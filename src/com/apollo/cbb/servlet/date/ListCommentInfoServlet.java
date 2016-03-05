package com.apollo.cbb.servlet.date;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.apollo.cbb.dao.UserDateDaoImpl;
import com.apollo.cbb.utils.CommonUtil;
import com.apollo.cbb.utils.ErrorUtils;

public class ListCommentInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 4222175564772276136L;

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
		this.dealData(request, response);

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
		ErrorUtils.sendError("not supported post", response);
	}
	
	private void dealData(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String userStoreId = request.getParameter("userStoreId");
		if(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(userStoreId)){
			UserDateDaoImpl userDateDao = new UserDateDaoImpl();
			List<Map<String,Object>> commentList = userDateDao.getCommentList(Integer.parseInt(userStoreId));
			if(commentList != null){
				Map<String, Object> data = new HashMap<String,Object>();
				data.put("response", "suc");
				data.put("userinfo", commentList);
				CommonUtil.renderJson(response, data);
			}else{
				ErrorUtils.sendError("不存在此数据", response);
			}
			return;
		}
		ErrorUtils.sendError("数据格式有误", response);
	}

}
