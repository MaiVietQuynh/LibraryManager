package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDatabase {
	
	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException
	{
		String dbURL = "jdbc:mysql://localhost/qltv";

		String username = "root";
		String password = "";

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = (Connection) DriverManager.getConnection(dbURL, username, password);
		if (conn != null) {
			System.out.println("Kết nối thành công");
			return conn;
		}
		return null;
	}
}
//		String dbURL = "jdbc:mysql://node238128-lemanhltm.j.layershift.co.uk/manage_library?characterEncoding=UTF-8";
//		String password = "KROmim21616";