package com.myoungwon.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/calc2")
public class Calc2 extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ServletContext application = req.getServletContext();
		//HttpSession session = req.getSession();
		Cookie cookies[] = req.getCookies();
		
		resp.setCharacterEncoding("utf-8");					// 서버가 보내고자 하는 형식을 나타내는 것
		resp.setContentType("text/html;charset=utf-8");		// 브라우저가 해석해야 할 형식이 무엇인지 알려주는 것
		//req.setCharacterEncoding("utf-8");					// 톰캣에서 읽어오는 형식을 설정하는 것
		
		String v_ = req.getParameter("v");
		String op = req.getParameter("operator");
		
		int v = 0;
		if(!v_.equals("")) v = Integer.parseInt(v_);
		int result = 0;
		
		// 계산
		if(op.equals("=")) {
			
			//int x = (Integer)application.getAttribute("value");
			//int x = (Integer)session.getAttribute("value");
			int x = 0;
			for(Cookie c : cookies) {
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}				
			}

			int y = v;
			
			//String operator = (String)application.getAttribute("op");
			//String operator = (String)session.getAttribute("op");
					
			String operator = "";
			for(Cookie c : cookies) {
				if(c.getName().equals("op")) {
					operator = c.getValue();
					break;
				}				
			}

			if(operator.equals("+")) {
				result = x + y;				
			}
			else {
				result = x - y;				
			}

			resp.getWriter().println("계산결과는 : " + result);
		}
		// 저장
		else {	
			//application.setAttribute("value", v);
			//application.setAttribute("op", op);

			//session.setAttribute("value", v);
			//session.setAttribute("op", op);
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v));	//url에 사용될 수 있는 문자열만 사용가능
			Cookie opCookie = new Cookie("op", op);
			valueCookie.setPath("/calc2");
			//valueCookie.setMaxAge(60*60*24); //초 단위로 24시간 표현 -> 60초 * 60분 * 24시간 
			opCookie.setPath("/calc2");
			resp.addCookie(valueCookie);
			resp.addCookie(opCookie);
		}
	}
}
