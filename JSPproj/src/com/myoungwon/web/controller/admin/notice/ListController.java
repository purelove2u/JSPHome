package com.myoungwon.web.controller.admin.notice;

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
import com.myoungwon.web.entity.NoticeView;
import com.myoungwon.web.service.NoticeService;

@WebServlet("/admin/notice/list")
public class ListController extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] open_ids = req.getParameterValues("open-id");
		String[] del_ids = req.getParameterValues("del-id");
		String cmd = req.getParameter("cmd");
		
		switch(cmd) {
		case "일괄공개":
			for(String openId : open_ids) {
				System.out.printf("open_id : %s\n", openId);
			}
			break;
		case "일괄삭제":
			for(String delId : del_ids) {
				System.out.printf("del_id : %s\n", delId);
			}
			break;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// list?f=title&q=a 라는 형태로 전달될 때		
		String field_ = req.getParameter("f");
		String query_ = req.getParameter("q");
		String page_ = req.getParameter("p");
		
		String field = "title";
		if(field_ != null && !field_.equals("")) {
			field = field_;
		}
		
		String query = "";
		if(query_ != null && !query_.equals("")) {
			query = query_;
		}
		
		int page = 1;
		if(page_ != null && !page_.equals("")) {
			page = Integer.parseInt(page_);
		}
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page);
		int recordCount = service.getNoticeCount(field, query); 
		
		req.setAttribute("list", list);
		req.setAttribute("recordCount", recordCount);
		
		req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(req, resp);
		
	}
}
