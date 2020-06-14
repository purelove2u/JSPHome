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
		
		resp.setCharacterEncoding("utf-8");					// ������ �������� �ϴ� ������ ��Ÿ���� ��
		resp.setContentType("text/html;charset=utf-8");		// �������� �ؼ��ؾ� �� ������ �������� �˷��ִ� ��
		//req.setCharacterEncoding("utf-8");					// ��Ĺ���� �о���� ������ �����ϴ� ��
		
		PrintWriter out = resp.getWriter();
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		out.println(title);
		out.println(content);
		
		
	}
}
