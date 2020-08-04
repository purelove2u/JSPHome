package com.myoungwon.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] open_ids = req.getParameterValues("open-id");
		String[] del_ids = req.getParameterValues("del-id");
		String cmd = req.getParameter("cmd");
		String ids_ = req.getParameter("ids");
		String[] ids = ids_.trim().split(" ");
		
		NoticeService service = new NoticeService();
		
		switch(cmd) {
		case "일괄공개":
			for(String openId : open_ids) {
				System.out.printf("open_id : %s\n", openId);
			}
			List<String> oids = Arrays.asList(open_ids);
			List<String> cids = new ArrayList(Arrays.asList(ids));
			cids.removeAll(oids);
			System.out.println(cids);
//			for(int i=0; i<ids.length; i++) {
//				//1. ���� id�� open�� ���³�
//				if(oids.contains(ids[i])) {
//					pub = 1;
//				}else {
//					pub = 0;
//				}
//			}
			
			service.pubNoticeAll(oids, cids);
			//service.closeNoticeList(cids);
			
			break;
			
		case "일괄삭제":
			int[] ids1 = new int[del_ids.length];
			for(int i=0; i<del_ids.length; i++) {
				ids1[i] = Integer.parseInt(del_ids[i]);
			}
			int result = service.deleteNoticeAll(ids1);
			break;
		}
		
		resp.sendRedirect("list");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// list?f=title&q=a ��� ���·� ���޵� ��		
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
