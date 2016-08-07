package com.jaf.examples.zookeeper.base;

import com.jaf.examples.zookeeper.Config;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper 可以使用之前会话的 sessionId 和 password 来恢复会话
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkConnectUsageSIDPasswd implements Watcher {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(Config.ZK_SERVER, 5000, new ZkConnectUsageSIDPasswd());
        latch.await();

        long sessionId = zooKeeper.getSessionId();
        byte[] passwd = zooKeeper.getSessionPasswd();

        // 使用错误的 sessionId 和 password 连接，客户端将收到 Expired 事件通知
        zooKeeper = new ZooKeeper(Config.ZK_SERVER, 5000, new ZkConnectUsageSIDPasswd(), 1l, "test".getBytes());
        zooKeeper.getState();

        // 使用之前正确的 sessionId 和 password 来创建连接
        zooKeeper = new ZooKeeper(Config.ZK_SERVER, 5000, new ZkConnectUsageSIDPasswd(), sessionId, passwd);
        zooKeeper.getState();

        Thread.sleep(1000);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Received watched event: " + event);
        if(Event.KeeperState.SyncConnected == event.getState()) {
            latch.countDown();
        }
    }
}
