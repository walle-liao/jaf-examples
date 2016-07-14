package com.jaf.examples.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年7月14日
 * @since 1.0
 */
public class WatcherTest {
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper(Config.ZK_SERVER, 5000, new SimpleWatcher());
//		zk.create("/zk-examples/test1", "456".getBytes(), null, CreateMode.PERSISTENT);
		zk.getChildren("/zk-examples", true);
		Thread.sleep(2000);
	}

	
	private static class SimpleWatcher implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			System.out.println("watch class : " + this.getClass().getName());
			System.out.println("path : " + event.getPath());
			System.out.println("eventType : " + event.getType().name());
		}
		
	}
	
}

