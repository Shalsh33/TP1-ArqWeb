package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.MotorDB;

public class ConnectionFactory {
	
	//Patron singleton
	private static ConnectionFactory instancia = new ConnectionFactory();
	
	private Connection connection;
	
	private ConnectionFactory() {
		
	}
	
	public static ConnectionFactory getInstance() {
		return instancia;
	}
	
	public Connection connect(MotorDB type) {
		
		if (this.connection != null) {
			this.disconnect();
		}
		
		if (type.equals(MotorDB.DERBY)) {
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				this.connection = DriverManager.getConnection("jdbc:derby:Dbtp;create=true");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (type.equals(MotorDB.MYSQL)) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ArqWeb", "root", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return this.connection;
	}

	public Connection connection() {
		return this.connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


}
