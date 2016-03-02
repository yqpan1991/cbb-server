package com.apollo.cbb.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingFilter implements Filter {
	private String encode = "";
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encode = filterConfig.getServletContext().getInitParameter("encode");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//一行代码解决响应乱码
		response.setContentType("text/html;charset="+encode);
		
		//通过修改request对象和获取请求参数相关的方法,解决请求参数乱码,但是通过这些方法会解决,乱码会解决
		
		//放行
		chain.doFilter(new MyHttpServletRequestWrapper((HttpServletRequest) request), response);
	}
	
	class  MyHttpServletRequestWrapper extends HttpServletRequestWrapper{
		private boolean isEncode = false;
		private HttpServletRequest request = null;
		public MyHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		@Override
		public Map<String,String[]> getParameterMap(){
			String method = request.getMethod();
			//首先获取到提交方式,如果提交方式为post,直接设置utf-8编码即可
			try {
				if("POST".equals(method)){
					request.setCharacterEncoding(EncodingFilter.this.encode);
					return request.getParameterMap();
				}else if(!"GET".equals(method)){//其他提交方式,不处理
					return request.getParameterMap();
				}else{
					//如果是get方式,那么进行处理,如果是其他提交方式,不处理,直接使用原来的方式即可
					//获取原来request中的map,将此map的数据进行遍历,分别取出来进行编码更改掉原来的参数即可
					Map<String, String[]> map = request.getParameterMap();
					if(!this.isEncode){
						for(String key : map.keySet()){
							String[] arr = map.get(key);
							for(int i=0;i<arr.length;i++){
								arr[i] = new String(arr[i].getBytes("ISO8859-1"),EncodingFilter.this.encode);
							}
						}
						isEncode = true;
					}
					return map;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public String[] getParameterValues(String name){
			return this.getParameterMap().get(name);
		}
		
		@Override
		public String getParameter(String name){
			String result = null;
			String[] arr = getParameterValues(name);
			if(arr!=null){
				result = arr[0];
			}
			return result;
		}
	} 

	@Override
	public void destroy() {

	}

}
