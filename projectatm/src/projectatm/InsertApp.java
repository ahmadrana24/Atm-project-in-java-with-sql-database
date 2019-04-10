package projectatm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertApp {

	private Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:users.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void insert(String name, int id, String cash, String email, String creditc, String cnic, String number) {
		String sql = "INSERT INTO users(name,id,cash,email,creditcard,cnic,number) VALUES(?,?,?,?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.setString(3, cash);
			pstmt.setString(4, email);
			pstmt.setString(5, creditc);
			pstmt.setString(6, cnic);
			pstmt.setString(7, number);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
