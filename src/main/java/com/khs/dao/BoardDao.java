package com.khs.dao;

import java.util.List;
import java.util.Map;

import com.khs.dto.Board;
import com.khs.dto.BoardList;
import com.khs.dto.Cre;
import com.khs.dto.Reply;
import com.khs.dto.Upload;

public interface BoardDao {
	public List<Board> boardlist(Cre c);
	
	public Board boardDetail(String no);
	
	public void replyinsert(Reply r);
	
	public String boardinsert(Board bo, Upload u);
	
	public Map<String,String> boarddelete(String no);
	
	public String modify(Board b, Upload u);
	
	public String fileinsert(Upload u,String no);
	
	public void thumbinsert(Upload u);
	
	public int getT(Cre c);
	
}
