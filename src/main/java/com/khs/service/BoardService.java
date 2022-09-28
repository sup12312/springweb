package com.khs.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.khs.dto.Board;
import com.khs.dto.BoardList;
import com.khs.dto.Cre;
import com.khs.dto.Reply;


public interface BoardService {
	public List<Board> boardList(Cre c);
	public Board boardDetail(String no);
	public void replyinsert(Reply r);
	public String boardinsert(HttpServletRequest req, HttpServletResponse res);
	public String boardinsert(Board b, MultipartFile files);
	public String modify(HttpServletRequest req, HttpServletResponse res);
	public int getT(Cre c);
	public void boarddelete(HttpServletRequest req, HttpServletResponse res);
	public void boarddelete(String no);
}
