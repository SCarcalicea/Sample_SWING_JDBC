package gestiune.login;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DataBaseConnectivity {

	// Data base connection details
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://localhost/pubdb";
	private static final String DB_USER_NAME = "root";
	private static final String DB_PASSWORD = "";
	private Connection con = null;

	public boolean connect() {
		if (con == null) {
			try {
				Class.forName(DRIVER).newInstance();
				con = (Connection) DriverManager.getConnection(JDBC_URL, DB_USER_NAME, DB_PASSWORD);
				return con != null;
			} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException err) {
				return false;
			}
		} else {
			return true;
		}
	}

	public boolean disconnect() {
		if (con != null) {
			try {
				con.close();
				con = null;
				return true;
			} catch (SQLException ex) {
				return false;
			}
		}
		return false;
	}

	public boolean checkPassword(String user, String password) throws SQLException {
		Statement stmt = (Statement) con.createStatement();
		String select = "SELECT `Password` FROM `users` WHERE `UserName` = \"" + user + "\"";
		ResultSet result = stmt.executeQuery(select);
		
		boolean res = false;
		if (result.next()) {
			res = result.getString(1).equals(password);
			result.close();
			stmt.close();
			return res;
		} else {
			return false;
		}
	}
	
	public Connection getConnection() {
		return this.con;
	}
}

