package com.jaf.examples.zookeeper.zk;

import com.jaf.examples.zookeeper.Config;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * ZooKeeper 连接创建简单示例
 *
 * @author liaozhicheng.cn@163.com
 */
public class ZkConnectTests implements Watcher {

    private static CountDownLatch lantch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(Config.ZK_SERVER, 5000, new ZkConnectTests());
        // 因为 zookeeper 的连接是异步的，所以这里的连接还没有完成
        System.out.println(zooKeeper.getState());  // CONNECTING

        try {
            lantch.await();  // 这里阻塞等待，连接创建完成之后会收到通知事件
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 这里连接已经创建好了
        System.out.println(zooKeeper.getState());  // CONNECTED

        System.out.println("Zookeeper session established.");
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: " + event);
        if(Event.KeeperState.SyncConnected == event.getState()) {
            lantch.countDown();
        }
    }

}
