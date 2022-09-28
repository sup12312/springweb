package com.khs.service;

import java.util.List;


import com.khs.dto.Cre;
import com.khs.dto.Reply;
import com.khs.dto.ReplyDB;
import com.khs.dto.Replylist;

public interface ReplyService {

	public int replyinsert(Reply r);

	public List<ReplyDB> list(String no, Cre c);
	
	public Replylist getlist(String no, Cre c);

	public ReplyDB detail(int no);

	public int update(ReplyDB re);

	public int delete(int no);
}
