package com.khs.common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class Logging extends HttpFilter implements Filter {
    PrintWriter w;
    
    
    public Logging() {
        super();
    }
	public void destroy() {
		w.close();
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		long s = System.currentTimeMillis();
		
		String path = ((HttpServletRequest)request).getContextPath();
		String uri = ((HttpServletRequest)request).getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		w.printf("Path:%s , uri:%s, cmd:%s %n", path,uri,cmd);
		
		GregorianCalendar n = new GregorianCalendar();
		w.printf("접근시간:%TF, %TT \n", n,n);
		//주소
		String addr = request.getRemoteAddr();
		
		//포트정보
		int port = request.getRemotePort();
		
		
		w.printf("접근주소:%s , 접근포트:%s\n",addr, port);
		
		chain.doFilter(request, response);
		
		long e = System.currentTimeMillis();
		w.printf("응답시간:%d ms \n", (e-s));
	}
	public void init(FilterConfig fConfig) throws ServletException {
		GregorianCalendar c = new GregorianCalendar();
		String fn = c.get(Calendar.YEAR) + "_" + (c.get(Calendar.MONTH)+1)+"_"
					+ c.get(Calendar.DATE);
		try {
			w = new PrintWriter(new FileWriter("d:\\JAVAkhs2\\log\\" + fn + ".log" , true),true);
		} catch (IOException e) {
			System.out.println("로그파일생성오류");
		}
	}

}
