package com.apollo.cbb.servlet.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.apollo.cbb.bean.UserInfo;
import com.apollo.cbb.dao.UserDaoImpl;
import com.apollo.cbb.utils.CommonUtil;
import com.apollo.cbb.utils.ErrorUtils;

class UserRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 8940698363241098392L;

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
//		this.dealData(request, response);
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
		//1. 解析出用户的参数
		//2.校验参数合法性
		//3.执行对应的操作
		//4.返回结果
		this.dealData(request, response);
	
	}

	private void dealData(HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = new UserInfo();
		try {
			BeanUtils.populate(userInfo, request.getParameterMap());
			if(userInfo.checkValidate()){
				UserDaoImpl userDao = new UserDaoImpl();
				Map<String,Object> map = userDao.register(userInfo.getUserName(), userInfo.getPassword(), userInfo.getType());
				if(map != null){
					Map<String,Object> results = new HashMap<String,Object>();
					results.put("response", "suc");
					results.put("user_info", map);
					CommonUtil.renderJson(response, results);
					return;
				}
			}
			ErrorUtils.sendError("数据格式有误", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ErrorUtils.sendError("数据有误", response);
	}

}
