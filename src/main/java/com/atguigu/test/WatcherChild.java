package com.atguigu.test;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class WatcherChild {

	private static final Logger logger = Logger.getLogger(WatcherChild.class);
	private static final String CONNECTSTRING = "192.168.184.128:2181";

	private static final int SESSION_TIMEOUT = 5 * 1000;
	private static final String PATH = "/atguigu";
	private ZooKeeper zk = null;
	private String oldValue = "";


	public ZooKeeper startZk() throws IOException{
		return zk = new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getType() == EventType.NodeChildrenChanged && event.getPath().equals(PATH)) {
					logger.info("11111111");
					showChildNode(PATH);
				}else{
					logger.info("22222222");
					showChildNode(PATH);
				}
			}
		});
	}
	
	public void showChildNode(String nodePath){
		List<String> children = null;
		try {
			children = zk.getChildren(nodePath, true);
			logger.info("************showChildNode: "+children);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String createNode(String nodePath,String data) throws KeeperException, InterruptedException {
		String create = zk.create(nodePath, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		return create;
	}
	
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		WatcherChild watcherChild = new WatcherChild();
		
		watcherChild.setZk(watcherChild.startZk());
			
		Thread.sleep(Long.MAX_VALUE);
		
	}
	
	
	
	
	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public ZooKeeper getZk() {
		return zk;
	}

	public void setZk(ZooKeeper zk) {
		this.zk = zk;
	}

	
}





















