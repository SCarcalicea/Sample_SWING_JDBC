package gestiune.detalii;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBUserDetails {
	
	private UserData user = null;
	
	public UserData getUserDetails(Connection con, String user) {
		
		try {
			Statement stmt = (Statement) con.createStatement();
			String select = "SELECT * FROM `users` WHERE `UserName` = \"" + user + "\"";
			ResultSet result = stmt.executeQuery(select);
			if (result.next()) {
				this.user = new UserData(result.getString(1), result.getString(2), result.getDate(3).toLocalDate(), result.getString(4));
				return this.user;
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Error from data base");
			return null;
		}
	}

}
