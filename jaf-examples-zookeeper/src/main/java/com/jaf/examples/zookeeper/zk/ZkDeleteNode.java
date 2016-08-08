package com.jaf.examples.zookeeper.zk;

import com.jaf.examples.zookeeper.Config;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 使用 zk 客户端删除节点
 * 1、使用 version 参数可以实现 CAS 删除操作
 * 2、创建节点和修改节点的值都会导致节点的 version 自增，默认是 0 开始，删除节点时 version = -1 表示不关心节点 version
 * 3、zk 只能删除子节点（任何存在子节点的节点都不能删除），而且不支持递归删除
 * 4、zk 支持同步和异步两种方式删除节点
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkDeleteNode implements Watcher {

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final Watcher watcher = new ZkDeleteNode();

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        initZookeeper();

        String path = "/zk_delete_node";
        createNode(path);

        // 使用 version 进行删除，可以实现 CAS 操作，只有当 version 值正确的时候才会真正删除
        Stat stat = zooKeeper.exists(path, watcher);
        System.out.println(stat);
        deleteNodeSync(path, stat.getVersion());

        // 使用错误的 version 值删除
        createNode(path);
        try {
            deleteNodeSync(path, 100);  // 抛出异常：KeeperErrorCode = BadVersion for /zk_delete_node
        } catch (KeeperException exception) {
            // ignore
            System.out.println("catch exception : " + exception.getMessage());
        }

        // zookeeper 的 version 默认是从 0 开始增加，-1 表示不关心 version 值，任意版本节点都会被删除
        zooKeeper.exists(path, watcher);
        deleteNodeSync(path, -1);

        // zookeeper 只能删除叶子节点，如果节点下还有子节点，则删除失败
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  // 创建永久节点
        zooKeeper.exists(path, watcher);

        String childrenPath = path + "/children";
        createNode(childrenPath);
        try {
            deleteNodeSync(path, -1);  // 抛出异常: KeeperErrorCode = Directory not empty for /zk_delete_node
        } catch (Exception exception) {
            // ignore
            System.out.println("catch exception : " + exception.getMessage());
        }

        // 异步方式删除节点
        deleteNodeAsync(childrenPath, -1);
        deleteNodeAsync(path, -1);

        Thread.sleep(1000);
        zooKeeper.close();
    }


    private static void initZookeeper() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(Config.ZK_SERVER_CLUSTER, Config.DEFUALT_TIME_OUT, watcher);
        latch.await();
    }

    private static void createNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    private static void deleteNodeSync(String path, int version) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, version);
    }

    private static void deleteNodeAsync(String path, int version) {
        zooKeeper.delete(path, version, new MyVoidCallback(), "i am context");
    }


    @Override
    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected == event.getState()) {
            if(Event.EventType.None == event.getType() && event.getPath() == null) {
                latch.countDown();
                System.out.println("connection init");
            } else if(Event.EventType.NodeDeleted == event.getType()) {
                System.out.println("node delete : " + event);
            }
        }
    }

    static class MyVoidCallback implements AsyncCallback.VoidCallback {

        @Override
        public void processResult(int rc, String path, Object ctx) {
            System.out.println("delete node, result code : " + rc + ", path : " + path + ", context : " + ctx);
        }
    }

}
