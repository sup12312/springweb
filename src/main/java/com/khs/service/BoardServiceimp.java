package com.khs.service;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.khs.dao.BoardDao;
import com.khs.dto.Board;
import com.khs.dto.Cre;
import com.khs.dto.Reply;
import com.khs.dto.Upload;

@Service
public class BoardServiceimp implements BoardService {
	
	@Autowired
	private BoardDao dao;
	private static final String CHARSET = "utf-8"; 

	@Override
	public List<Board> boardList(Cre c) {
		return dao.boardlist(c);
	}

	@Override
	public Board boardDetail(String no) {
		return dao.boardDetail(no);
	}


	@Override
	public String boardinsert(HttpServletRequest req, HttpServletResponse res) {
		DiskFileItemFactory f = new DiskFileItemFactory();
		f.setDefaultCharset(CHARSET);
		ServletFileUpload u =new ServletFileUpload(f);
		Board b = new Board();
		FileServiceimp file = new FileServiceimp();
		Upload up = null;
		try {
			List<FileItem> i = u.parseRequest(req);
			for(FileItem item: i) {
				if(item.isFormField()) {
					b = boardvalue(item,b);
				}else {
					up = file.fileupload(item);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao.boardinsert(b,up);
	}
	
	public String boardinsert(Board b, MultipartFile files) {
		FileServiceimp f = new FileServiceimp();
		return dao.boardinsert(b,f.fileupload(files));
	}

	public void boarddelete(HttpServletRequest req, HttpServletResponse res) {
		String no = req.getParameter("no");
		Map<String,String> m =dao.boarddelete(no);
		FileServiceimp f = new FileServiceimp();
		f.filedel(m);
	}
	public void boarddelete(String no) {
		Map<String,String> m =dao.boarddelete(no);
		FileServiceimp f = new FileServiceimp();
		f.filedel(m);
	}

	public String modify(HttpServletRequest req, HttpServletResponse res) {
		
		DiskFileItemFactory f = new DiskFileItemFactory();
		f.setDefaultCharset(CHARSET);
		ServletFileUpload u =new ServletFileUpload(f);
		Board b = new Board();
		FileServiceimp file = new FileServiceimp();
		Upload up = null;
		try {
			List<FileItem> i = u.parseRequest(req);
			for(FileItem item: i) {
				if(item.isFormField()) {
					b = boardvalue(item,b);
				}else {
					up = file.fileupload(item);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dao.modify(b,up);
	}
	Board boardvalue(FileItem item,Board b) {
		//<input> 태그값
		//System.out.printf("필드이름 : %s, 필드값 : %s\n",item.getFieldName(),item.getString());
		if(item.getFieldName().equals("title")) {
			b.setTitle(item.getString());
		}
		if(item.getFieldName().equals("content")) {
			b.setContent(item.getString());
		}
		if(item.getFieldName().equals("open")) {
			b.setOpen(item.getString());
		}
		if(item.getFieldName().equals("id")) {
			b.setId(item.getString());
		}
		if(item.getFieldName().equals("seqno")) {
			b.setSeqno(item.getString());
		}
		return b;
	}

	public int getT(Cre c) {
		return dao.getT(c);
	}

	public void replyinsert(Reply r) {
		dao.replyinsert(r);
	}

}
