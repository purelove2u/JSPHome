package com.myoungwon.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.myoungwon.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList(){
		return getNoticeList("title", "", 1);
	}
	public List<Notice> getNoticeList(int page){
		return getNoticeList("title", "", page);
	}
	public List<Notice> getNoticeList(String field/*title, writer_id*/, String query/*A*/, int page){
		List<Notice> list = new ArrayList<>();

		String sql = "select * from (" + 
				"    select rownum num, n.* " + 
				"    from (select * from notice where " + field + " like ? order by regdate desc) n " + 
				") " + 
				"where num between ? and ?";
		// 1, 11, 21, 31,,,,, -> an = 1 + (page - 1) * 10
		// 10, 20, 30, 40,,,,,-> page * 10
		
		String url = "jdbc:oracle:thin:@192.168.0.5:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "NEWLEC", "12345");
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, "%" + query + "%");
			pstmt.setInt(2, 1 + (page - 1) * 10);
			pstmt.setInt(3, page * 10);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){				
				int id = rs.getInt("id");	
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerId = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				
				// 인자 순서 주의 
				Notice notice = new Notice(id, title, regdate, writerId, hit, files, content);
				list.add(notice);
			}
				rs.close();
				pstmt.close();
				con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}
	public int getNoticeCount(String field, String query) {
		
		int count = 0;
		
		String sql = "select count(id) count from ("
					+ "	select rownum num, n.* "
					+ " from (select * from notice where "+field+" like ? order by regdate desc) n "  
					+ ") ";
		
		String url = "jdbc:oracle:thin:@192.168.0.5:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "NEWLEC", "12345");
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, "%" + query + "%");
			
			ResultSet rs = pstmt.executeQuery();
			
			count = rs.getInt("count");
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Notice getNotice(int id) {
		Notice notice = null;
		
		String sql = "select * from notice where id=?";
		
		String url = "jdbc:oracle:thin:@192.168.0.5:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "NEWLEC", "12345");
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){				
				int nid = rs.getInt("id");	
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerId = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				
				// 인자 순서 주의 
				notice = new Notice(nid, title, regdate, writerId, hit, files, content);
				
			}
				rs.close();
				pstmt.close();
				con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	public Notice getNextNotice(int id) {
		
		Notice notice = null;
		
		String sql = "select * from notice " + 
				"where id = (" + 
				"    select id from notice " + 
				"    where regdate > (select regdate from notice where id = ?) " + 
				"    and rownum = 1" + 
				") ";
		
		String url = "jdbc:oracle:thin:@192.168.0.5:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "NEWLEC", "12345");
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){				
				int nid = rs.getInt("id");	
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerId = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				
				// 인자 순서 주의 
				notice = new Notice(nid, title, regdate, writerId, hit, files, content);
				
			}
				rs.close();
				pstmt.close();
				con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	public Notice getPrevNotice(int id) {
		
		Notice notice = null;
		
		String sql = "select * from notice " + 
				"where id = (select id from (select * from notice order by regdate desc) " + 
				"            where regdate < (select regdate from notice where id = ?) " + 
				"            and rownum = 1 " + 
				") ";
		
		String url = "jdbc:oracle:thin:@192.168.0.5:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "NEWLEC", "12345");
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){				
				int nid = rs.getInt("id");	
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerId = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				
				// 인자 순서 주의 
				notice = new Notice(nid, title, regdate, writerId, hit, files, content);
				
			}
				rs.close();
				pstmt.close();
				con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
}
