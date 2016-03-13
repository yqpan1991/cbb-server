package com.apollo.cbb.servlet.store;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.apollo.cbb.dao.UserStoreDaoImpl;
import com.apollo.cbb.utils.CommonUtil;
import com.apollo.cbb.utils.ErrorUtils;

public class ListCanDateRecommendServlet extends HttpServlet {

	private static final long serialVersionUID = -4979084840722218353L;

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
	
	private void dealData(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		if(!StringUtils.isEmpty(userId)){
			UserStoreDaoImpl userStoreDao = new UserStoreDaoImpl();
			List<Map<String,Object>> recommendList = null;
			recommendList = userStoreDao.getCanDateRecommendListByUserId();
			if(recommendList != null){
				Map<String, Object> results = new HashMap<String, Object>();
				results.put("response", "suc");
				results.put("results", recommendList);
				CommonUtil.renderJson(response, results);
			}else{
				ErrorUtils.sendError("数据格式有误", response);
			}
			return;
		}
		ErrorUtils.sendError("数据格式有误", response);
	}

}
