package com.myoungwon.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myoungwon.web.entity.Notice;
import com.myoungwon.web.service.NoticeService;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		NoticeService service = new NoticeService();
		
		List<Notice> list = service.getNoticeList();
		
		req.setAttribute("list", list);
		req.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(req, resp);;
		
	}
}
