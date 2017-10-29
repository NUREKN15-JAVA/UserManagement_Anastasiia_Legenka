package ua.nure.kn156.legenka.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

	private String driver;
	private String url;
	private String password;
	private String user;

	public ConnectionFactoryImpl(String driver, String url, String user, String password) {
		this.driver=driver;
		this.url=url;
		this.password=password;
		this.user=user;
	}

	@Override
	public Connection createConnection() throws DatabaseException {
		try{
			Class.forName(driver);
		}
	catch(ClassNotFoundException e){
		throw new RuntimeException(e);
		
	}
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
