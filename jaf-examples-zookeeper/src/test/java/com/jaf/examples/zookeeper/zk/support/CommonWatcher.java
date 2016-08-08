package com.jaf.examples.zookeeper.zk.support;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * Watcher
 *
 * @author liaozhicheng.cn@163.com
 */
public class CommonWatcher implements Watcher {

    private final CountDownLatch latch;

    public CommonWatcher() {
        this.latch = new CountDownLatch(1);
    }

    public void await() throws InterruptedException {
        this.latch.await();
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

}
