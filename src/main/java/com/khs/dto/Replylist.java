package com.khs.dto;

import java.util.List;

public class Replylist {
	private List<ReplyDB> rdb;
	private int total;
	
	
	
	public Replylist(List<ReplyDB> rdb, int total) {
		super();
		this.rdb = rdb;
		this.total = total;
	}
	public List<ReplyDB> getRdb() {
		return rdb;
	}
	public void setRdb(List<ReplyDB> rdb) {
		this.rdb = rdb;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
