package com.khs.dto;

import java.util.List;

public class Board {
	private String no;
	private String title;
	private String content;
	private String count;
	private String wdate;
	private String name;
	private String id;
	private String seqno;
	private List<Reply> refly;
	private String open;
	private List<Upload> up;
	
	public List<Upload> getUp() {
		return up;
	}
	public void setUp(List<Upload> up) {
		this.up = up;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Reply> getRefly() {
		return refly;
	}
	public void setRefly(List<Reply> refly) {
		this.refly = refly;
	}
	
}
