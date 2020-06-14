package com.myoungwon.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/hi")
public class Nana extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("utf-8");					// 내가 보내고자 하는 형식을 나타내는 것
		resp.setContentType("text/html;charset=utf-8");		// 브라우저가 해석해야 할 형식이 무엇인지 알려주는 것
		
		PrintWriter out = resp.getWriter();
		String cnt_ = req.getParameter("cnt");
		
		int cnt = 100;
		if(cnt_ != null && !cnt_.equals("")) {			
			cnt = Integer.parseInt(cnt_);
		}

		for(int i=0; i<cnt; i++) {
			out.println((i+1)+" : 안녕 Servlet!!<br>");			
		}
	}
}
