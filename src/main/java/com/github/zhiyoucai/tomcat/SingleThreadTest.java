package com.github.zhiyoucai.tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;

public class SingleThreadTest {
	private DataSource datasource = null;
	
	private DataSourceFactory factory = new DataSourceFactory();

	
	Properties properties = new Properties();
	
	private void init() throws Exception {
		File file = new File("src\\main\\resources\\mysql.properties");
		properties.load(new FileInputStream(file));

		datasource = (DataSource) factory.createDataSource(properties);
	}
	
	private void doSomething() {
		Connection conn = null;
		PreparedStatement statement = null;
		final String sql = "select 1";
		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.executeQuery();
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
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		SingleThreadTest client = new SingleThreadTest();
		client.init();
		client.doSomething();
	}
}
