package jdbc;


import java.io.IOException;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


public class DBCPInit extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		loadJDBCDriver();
		initConnectionPool();
	}

	private void loadJDBCDriver() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Oracle JDBC Driver successfully loaded!(2)");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Fail to load JDBC Driver", e);
		}
		
	}
	
	private void initConnectionPool() {
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "0418";
			
			ConnectionFactory connFactory = new DriverManagerConnectionFactory(url, user, password);
			PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, null);
			poolableConnFactory.setValidationQuery("select 1");
			
			GenericObjectPoolConfig poolConfig =  new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setTestWhileIdle(true);
			poolConfig.setMinIdle(4);
			poolConfig.setMaxTotal(50);
			
			GenericObjectPool<PoolableConnection> connectionPool =
					new GenericObjectPool<PoolableConnection>(poolableConnFactory, poolConfig);
			poolableConnFactory.setPool(connectionPool);
		
		
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			driver.registerPool("guestbook", connectionPool);
			System.out.println("PoolingDriver Successfully loaded!(2)");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
