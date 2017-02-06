package com.github.zhiyoucai.dbpool_benchmark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

public class Execute extends Thread {
	
	private DataSource dataSource ;
	
	private String sql;
	
	private int executeCount;
	
	private CountDownLatch latch;
	
	public Execute(DataSource dataSource, String sql, int executeCount, CountDownLatch latch) {
		this.dataSource = dataSource;
		this.sql = sql;
		this.executeCount = executeCount;
		this.latch = latch;
	}
	
	@Override 
	public void run() {
		for (int i = 0; i < executeCount; i++) {
			executeSQL(sql);
		}
		latch.countDown();
	}
	
	private void executeSQL(String sql) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			//statement = conn.prepareStatement(sql);
			//rs = statement.executeQuery();
			/*if (rs.next()) {
				System.out.println(rs.getInt(1));
			}*/
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
