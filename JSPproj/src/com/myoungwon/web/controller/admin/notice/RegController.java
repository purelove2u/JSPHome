package com.myoungwon.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.myoungwon.web.entity.Notice;
import com.myoungwon.web.service.NoticeService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB가 넘어가면 메모리말고 디스크를 사용
		maxFileSize = 1024 * 1024 * 50, // 개별 파일의 최대 용량은 50MB
		maxRequestSize = 1024 * 1024 * 50 * 5 // 전체 파일의 총합산 최대 용량은 250MB
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String isOpen = req.getParameter("open");

		Collection<Part> parts = req.getParts();
		StringBuilder builder = new StringBuilder();

		for (Part p : parts) {
			if(!p.getName().equals("file")) continue;
			if(p.getSize() == 0) continue;

			Part filePart = p;
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			InputStream fis = filePart.getInputStream();

			// "/upload/"
			String realPath = req.getServletContext().getRealPath("/upload");
			System.out.println(realPath);
			
			File path = new File(realPath);
			if(!path.exists()) {
				path.mkdirs();
			}

			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);

			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = fis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			fos.close();
			fis.close();
		}
		
		builder.delete(builder.length()-1, builder.length());

		boolean pub = false;
		if (isOpen != null) {
			pub = true;
		}

		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("newlec");
		notice.setFiles(builder.toString());

		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);

		resp.sendRedirect("list");
	}
}
