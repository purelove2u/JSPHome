package com.myoungwon.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myoungwon.web.entity.Notice;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("id"));
		
		String url = "jdbc:oracle:thin:@192.168.0.227:1521/xepdb1";
		String sql = "SELECT * FROM NOTICE where id = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "NEWLEC", "12345");
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();

			String title = rs.getString("title");
			Date regdate = rs.getDate("regdate");
			String writerId = rs.getString("writer_id");
			int hit = rs.getInt("hit");
			String files = rs.getString("files");
			String content = rs.getString("content");
			
			// 인자 순서 주의 
			Notice notice = new Notice(id, title, regdate, writerId, hit, files, content);
			
			req.setAttribute("n", notice);
			
			/*
			// redirect
			req.setAttribute("title", title);
			req.setAttribute("writeId", writerId );
			req.setAttribute("regdate", regdate);
			req.setAttribute("hit", hit);
			req.setAttribute("files", files);			
			req.setAttribute("content", content);
			*/
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// forward
		req.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(req, resp);;
		
	}
}








