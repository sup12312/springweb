package com.khs.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khs.service.FileServiceimp;


@WebServlet(urlPatterns={"/file/*"})
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FileServiceimp f = new FileServiceimp();
       
    public FileController() {
        super();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doaction(req,res);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doaction(req,res);
	}
	private void doaction(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		//res.setContentType("text/html; charset=utf-8");
		//req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		switch(cmd) {
		case "fileDown" :
			f.filedownload(req, res);
			break;
		case"filedel" :
			String no = req.getParameter("no");
			String sf = req.getParameter("sf");
			String fp = req.getParameter("fp");
			String fn = req.getParameter("fn");
			//int r = f.filedel(no,sf,fp,fn);
			//System.out.println("파일삭제결과: "+r);
			try {
				PrintWriter out = res.getWriter();
				//out.print(r);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
		}
	}

}
