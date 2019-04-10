package projectatm;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection {
	public static Connection connection() {
		Connection c = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:users.db");
		} catch (Exception e) {
			System.out.println(e);
		}
		return c;
	}
}
