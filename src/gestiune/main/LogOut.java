package gestiune.main;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class LogOut {
	
	public static void logOut (Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Error");
		}
	}

}
