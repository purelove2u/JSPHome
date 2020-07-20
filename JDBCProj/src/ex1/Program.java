package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		String url = "jdbc:oracle:thin:@192.168.0.215:1521/orcl";
		String sql = "SELECT * FROM NOTICE";

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "system", "12345");
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("title");
			String writerid = rs.getString(3);
			String content = rs.getString(4);
			Date regDate = rs.getDate(5);
			int hit = rs.getInt("hit");
			System.out.println("-------------------------------------------------------");
			System.out.printf("ID : %d, TITLE : %s, Writer : %s\nContent : %s\nregDate : %s, hit : %d\n", id, name,
					writerid, content, regDate, hit);
			System.out.println("-------------------------------------------------------");
		}

		rs.close();
		pstmt.close();
		con.close();
	}

}
