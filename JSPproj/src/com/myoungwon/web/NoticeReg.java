package com.myoungwon.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/notice-reg")
public class NoticeReg extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("utf-8");					// 서버가 보내고자 하는 형식을 나타내는 것
		resp.setContentType("text/html;charset=utf-8");		// 브라우저가 해석해야 할 형식이 무엇인지 알려주는 것
		//req.setCharacterEncoding("utf-8");					// 톰캣에서 읽어오는 형식을 설정하는 것
		
		PrintWriter out = resp.getWriter();
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		out.println(title);
		out.println(content);
		
		
	}
}
