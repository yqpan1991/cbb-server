package com.apollo.cbb.servlet.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.apollo.cbb.bean.UserInfo;
import com.apollo.cbb.dao.UserDaoImpl;
import com.apollo.cbb.utils.CommonUtil;
import com.apollo.cbb.utils.ErrorUtils;

public class UserLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 7686666506415816749L;

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
		
		/**
		 * userName	用户名	xiaowen@redbaby.com.cn
			password	密码	123456
			type	类型	1 患者 2 医生
		 * */
		
		UserInfo userInfo = new UserInfo();
		try {
			BeanUtils.populate(userInfo, request.getParameterMap());
			if(userInfo.checkValidate()){
				UserDaoImpl userDao = new UserDaoImpl();
				Map<String, Object> map = userDao.login(userInfo.getUserName(), userInfo.getPassword());
				if(map != null){
					Map<String, Object> data = new HashMap<String,Object>();
					data.put("response", "suc");
					data.put("userInfo", map);
					CommonUtil.renderJson(response, data);
					return;
				}
			}
			ErrorUtils.sendError("数据格式有误", response);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorUtils.sendError("数据有误", response);
			return;
		}
	}

}
