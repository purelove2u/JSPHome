package com.myoungwon.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/calc")
public class Calc extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("utf-8");					// ������ �������� �ϴ� ������ ��Ÿ���� ��
		resp.setContentType("text/html;charset=utf-8");		// �������� �ؼ��ؾ� �� ������ �������� �˷��ִ� ��
		//req.setCharacterEncoding("utf-8");					// ��Ĺ���� �о���� ������ �����ϴ� ��
		
		String x_ = req.getParameter("x");
		String y_ = req.getParameter("y");
		String op = req.getParameter("operator");
		
		int x = 0;
		int y = 0;
		
		if(!x_.equals("")) x = Integer.parseInt(x_);
		if(!y_.equals("")) y = Integer.parseInt(y_);
		
		int result = 0;
		
		if(op.equals("����"))
			result = x + y;
		else
			result = x - y;
		
		PrintWriter out = resp.getWriter();
		
		out.println("������� : " + result);
		
	}
}
