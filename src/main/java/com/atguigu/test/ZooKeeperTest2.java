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

public class ZooKeeperTest2 {

	private static final Logger logger = Logger.getLogger(ZooKeeperTest2.class);
	private static final String CONNECTSTRING = "192.168.184.128:2181";
	private static final int SESSION_TIMEOUT = 5 * 1000;
	private static final String PATH = "/atguigu";
	
	
	public ZooKeeper startZk() throws IOException{
		return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void endZk(ZooKeeper zk) throws InterruptedException{
		if (zk != null) {
			zk.close();
		}
	}
	
	public String createNode(ZooKeeper zk,String dataPath,String data) throws KeeperException, InterruptedException{
		String create = zk.create(dataPath, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		return create;
	}
	
	public String getNode(ZooKeeper zk,String dataPath) throws KeeperException, InterruptedException{
		byte[] data = zk.getData(dataPath, false, new Stat());
		String string = new String(data);
		return string;
	}
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		ZooKeeperTest2 zkt2 = new ZooKeeperTest2();
		
		ZooKeeper startZk = zkt2.startZk();
		
		if (startZk == null) {
			String createNode = zkt2.createNode(startZk, PATH, "test2");
			String node = zkt2.getNode(startZk, PATH);
			logger.info("在【"+createNode+"】路径下下创建一个节点： "+node);
		} else {
			String node = zkt2.getNode(startZk, PATH);
			logger.info("*****i have 【"+node+"】 node");
		}
		
	}

}





















