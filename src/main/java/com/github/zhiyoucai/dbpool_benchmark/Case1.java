package com.github.zhiyoucai.dbpool_benchmark;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.dbcp2.BasicDataSource;
//import org.apache.commons.dbcp.BasicDataSource;
import org.apache.tomcat.jdbc.pool.DataSource;

import com.jolbox.bonecp.BoneCPDataSource;

public class Case1 {
	
	
	private int minPoolSize = 10;
	private int maxPoolSize = 50;
	private String username = "root";
	private String password = "root_sinosun";
	private String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";
	private int threadCount = 200;
	private final String sql = "select 1";
	private int loopCount = 1000000;
	
	
	private void test_bonecp() throws InterruptedException {
		BoneCPDataSource source = new BoneCPDataSource();
		source.setMinConnectionsPerPartition(10);
		source.setMaxConnectionsPerPartition(50);
		source.setPartitionCount(1);
		source.setDriverClass("com.mysql.jdbc.Driver");
		source.setUsername(username);
		source.setPassword(password);
		source.setJdbcUrl(jdbcUrl);
		source.setAcquireIncrement(5);
		CountDownLatch latch = new CountDownLatch(threadCount);
		ExecutorService threadsPool = Executors.newFixedThreadPool(threadCount);
		long startTime = System.currentTimeMillis();
		System.out.println(threadCount + " threads, every threads run " + loopCount + " times.");
		System.out.println("process start at: " + startTime);
		for (int i = 0; i < threadCount; i++) {
			threadsPool.execute(new Execute(source, sql, loopCount, latch));
			
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		long timeCost = endTime - startTime;
		System.out.println("process stop at: " + endTime);
		System.out.println("time cost : " + timeCost + " ms");
		double rate = 1000 * loopCount * threadCount / timeCost;
		System.out.println("process rate is : " + rate + " request/s");
	}
	
	private void test_tomcat() throws InterruptedException {
		DataSource source = new DataSource();
		source.setMaxIdle(maxPoolSize);
		source.setMinIdle(minPoolSize);
		source.setMaxActive(maxPoolSize);
		source.setUrl(jdbcUrl);
		source.setUsername(username);
		source.setPassword(password);
		source.setTestOnBorrow(true);
		source.setDriverClassName("com.mysql.jdbc.Driver");
		
		CountDownLatch latch = new CountDownLatch(threadCount);
		ExecutorService threadsPool = Executors.newFixedThreadPool(threadCount);
		long startTime = System.currentTimeMillis();
		System.out.println(threadCount + " threads, every threads run " + loopCount + " times.");
		System.out.println("process start at: " + startTime);
		for (int i = 0; i < threadCount; i++) {
			threadsPool.execute(new Execute(source, sql, loopCount, latch));
			
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		long timeCost = endTime - startTime;
		System.out.println("process stop at: " + endTime);
		System.out.println("time cost : " + timeCost + " ms");
		double rate = 1000 * loopCount * threadCount / timeCost;
		System.out.println("process rate is : " + rate + " request/s");
	}
	
	private void test_dbcp1() throws InterruptedException {
		org.apache.commons.dbcp.BasicDataSource source = new org.apache.commons.dbcp.BasicDataSource();
		source.setUsername(username);
		source.setPassword(password);
		//source.setMaxActive(maxPoolSize);
		source.setMaxIdle(maxPoolSize);
		source.setMinIdle(minPoolSize);
		source.setInitialSize(minPoolSize);
		//source.setPoolPreparedStatements(true);
		//source.setDriverClassName(driver);
		source.setUrl(jdbcUrl);
		source.setTestOnBorrow(false);
		source.setTestWhileIdle(true);
		
		CountDownLatch latch = new CountDownLatch(threadCount);
		ExecutorService threadsPool = Executors.newFixedThreadPool(threadCount);
		long startTime = System.currentTimeMillis();
		System.out.println(threadCount + " threads, every threads run " + loopCount + " times.");
		System.out.println("process start at: " + startTime);
		for (int i = 0; i < threadCount; i++) {
			threadsPool.execute(new Execute(source, sql, loopCount, latch));
			
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		long timeCost = endTime - startTime;
		System.out.println("process stop at: " + endTime);
		System.out.println("time cost : " + timeCost + " ms");
		double rate = 1000 * loopCount * threadCount / timeCost;
		System.out.println("process rate is : " + rate + " request/s");
	}
	
	private void test_dbcp() throws InterruptedException {
		BasicDataSource source = new BasicDataSource();
		source.setUsername(username);
		source.setPassword(password);
		source.setMaxIdle(maxPoolSize);
		source.setMinIdle(minPoolSize);
		source.setInitialSize(minPoolSize);
		source.setUrl(jdbcUrl);
		source.setTestOnBorrow(false);
		source.setTestWhileIdle(true);
		
		CountDownLatch latch = new CountDownLatch(threadCount);
		ExecutorService threadsPool = Executors.newFixedThreadPool(threadCount);
		long startTime = System.currentTimeMillis();
		System.out.println(threadCount + " threads, every threads run " + loopCount + " times.");
		System.out.println("process start at: " + startTime);
		for (int i = 0; i < threadCount; i++) {
			threadsPool.execute(new Execute(source, sql, loopCount, latch));
			
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		long timeCost = endTime - startTime;
		System.out.println("process stop at: " + endTime);
		System.out.println("time cost : " + timeCost + " ms");
		double rate = 1000 * loopCount * threadCount / timeCost;
		System.out.println("process rate is : " + rate + " request/s");
		
	}
	
	public static void main(String[] args) throws SQLException, InterruptedException {
		Case1 client = new Case1();
		//client.test_bonecp();
		//client.test_dbcp();
		client.test_tomcat();
		//client.test_dbcp1();
	}
	
}
