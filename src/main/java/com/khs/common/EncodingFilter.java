package com.khs.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
   
	private String encoding = null;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.encoding = fConfig.getInitParameter("encoding");
		System.out.println("필터 시작:"+fConfig.getFilterName());
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(this.encoding != null) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}

}
