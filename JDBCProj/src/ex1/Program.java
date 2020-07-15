package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		String url = "jdbc:oracle:thin:@192.168.0.219:1521/xepdb1";
		String sql = "SELECT * FROM NOTICE";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "sys as sysdba", "12345");
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			String name = rs.getString("TITLE");
			System.out.printf("NAME:%s\n", name);			
		}
		
		rs.close();
		pstmt.close();
		con.close();
	}

}
