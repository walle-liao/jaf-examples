package com.jaf.examples.zookeeper;

import com.jaf.examples.zookeeper.zk.support.CommonWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

/**
 * zk 基础测试类
 *
 * @author liaozhicheng.cn@163.com
 */
public class BaseTests {

    protected ZooKeeper zooKeeper;

    @Before
    public void setUp() throws IOException, InterruptedException {
        CommonWatcher watcher = new CommonWatcher();
        zooKeeper = new ZooKeeper(Config.ZK_SERVER_CLUSTER, Config.DEFUALT_TIME_OUT, watcher);
        watcher.await();
    }

    @After
    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    protected ZooKeeper newZookeeper() throws IOException, InterruptedException {
        CommonWatcher watcher = new CommonWatcher();
        ZooKeeper zooKeeper = new ZooKeeper(Config.ZK_SERVER_CLUSTER, Config.DEFUALT_TIME_OUT, watcher);
        watcher.await();
        return zooKeeper;
    }

    protected void createEphemeralNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    protected void createEphemeralNode(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    protected void deleteNodeSync(String path, int version) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, version);
    }


}
