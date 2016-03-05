package com.apollo.cbb.servlet.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.apollo.cbb.dao.UserDaoImpl;
import com.apollo.cbb.utils.CommonUtil;
import com.apollo.cbb.utils.ErrorUtils;

public class UserLogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 338560563630451767L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErrorUtils.sendError("not supported get", response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userIdStr = request.getParameter("userId");
		if(!StringUtils.isBlank(userIdStr)){
			UserDaoImpl userDao = new UserDaoImpl();
			boolean result = userDao.logout(Integer.parseInt(userIdStr));
			if(result){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("response", "suc");
				CommonUtil.renderJson(response, map);
			}else{
				ErrorUtils.sendError("未传输userId", response);
			}
			return;
		}
		ErrorUtils.sendError("未传输userId", response);
	}

}
