package com.apollo.cbb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现缓存的过滤器
 * @author PandaPan
 *
 */
public class CacheFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//设置失效的时间
		HttpServletResponse res = (HttpServletResponse) response;
		res.setDateHeader("Expires", System.currentTimeMillis()+1000l*3600*24*30);
		//然后滤器链继续调用
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
