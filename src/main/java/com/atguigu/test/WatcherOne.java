package com.atguigu.test;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class WatcherOne {

	private static final Logger logger = Logger.getLogger(WatcherOne.class);
	private static final String CONNECTSTRING = "192.168.184.128:2181";

	private static final int SESSION_TIMEOUT = 5 * 1000;
	private static final String PATH = "/atguigu";
	private ZooKeeper zk = null;
	
	
	public ZooKeeper startZk() throws IOException{
		return zk = new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				
			}
		});
	}
	
	public String createNode(String nodePath,String data) throws KeeperException, InterruptedException{
		String create = zk.create(nodePath, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		return create;
	}
	
	public String getNode(final String nodePath) throws KeeperException, InterruptedException{
		String string = null;
		byte[] data = zk.getData(nodePath, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				try {
					triggerNode(nodePath);
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, new Stat());
		string = new String(data);
		return string;
	}
	
	public String triggerNode(String nodePath) throws KeeperException, InterruptedException{
		String retValue = null;
		byte[] data = zk.getData(nodePath, false, new Stat());
		retValue = new String(data);
		logger.info("***********triggerValue retValue: "+retValue);
		
		return retValue;
	}
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		WatcherOne watchOne = new WatcherOne();
		
		watchOne.setZk(watchOne.startZk());
		
		if(watchOne.getZk().exists(PATH, false) == null)
		{
			watchOne.createNode(PATH, "AAA");
			
			String retValue = watchOne.getNode(PATH);
			
			logger.info("******main getZNode retValue: "+retValue);
			//调用过了getZNode方法，之后在/atguigu节点上设置了water
			
			Thread.sleep(Long.MAX_VALUE);
		}

		
	}

	public ZooKeeper getZk() {
		return zk;
	}

	public void setZk(ZooKeeper zk) {
		this.zk = zk;
	}

	
}





















