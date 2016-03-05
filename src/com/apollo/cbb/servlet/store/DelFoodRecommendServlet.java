package com.apollo.cbb.servlet.store;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.apollo.cbb.dao.UserStoreDaoImpl;
import com.apollo.cbb.utils.CommonUtil;
import com.apollo.cbb.utils.ErrorUtils;

public class DelFoodRecommendServlet extends HttpServlet {

	private static final long serialVersionUID = -4996236220303137844L;

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
		//1. 获取到参数
		//2. 解析参数合法性
		//3. 返回结果
		String recommendId = request.getParameter("recommendId");
		if(!StringUtils.isEmpty(recommendId)){
			try {
				UserStoreDaoImpl userStoreDao = new UserStoreDaoImpl();
				boolean result = userStoreDao.deleteUserStore(Integer.parseInt(recommendId));
				if(result){
					Map<String, Object> data = new HashMap<String,Object>();
					data.put("response", "suc");
					CommonUtil.renderJson(response, data);				
				}else{
					ErrorUtils.sendError("更新失败", response);
				}
				return;
			} catch (Exception e) {
			}
		}
		ErrorUtils.sendError("数据格式有误", response);
	}

}
