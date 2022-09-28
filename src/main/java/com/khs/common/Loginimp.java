package com.khs.common;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

@WebListener
public class Loginimp implements HttpSessionBindingListener {
	
	private String id;
	private String name;
	private static int user = 0;
	
	
    public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static int getUser() {
		return user;
	}

	public Loginimp(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Loginimp() {
    	
    }

    public void valueBound(HttpSessionBindingEvent event)  { 
    	//세션저장시 호출
    	user += 1;
    	System.out.println("현재 로그인 인원: " + user);
    }
    public void valueUnbound(HttpSessionBindingEvent event)  { 
    	//세션소멸시 호출
    	user -= 1;
    	System.out.println("현재 로그인 인원: " +user);
    }
	
}
