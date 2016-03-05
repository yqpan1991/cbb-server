package com.apollo.cbb.servlet.store;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.apollo.cbb.bean.RecommendInfo;
import com.apollo.cbb.bean.StoreInfo;
import com.apollo.cbb.bean.UserInfo;
import com.apollo.cbb.dao.UserStoreDaoImpl;
import com.apollo.cbb.utils.CommonUtil;
import com.apollo.cbb.utils.ErrorUtils;

public class AddFoodRecommendServlet extends HttpServlet {

	private static final long serialVersionUID = -1665454980733720587L;

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
		StoreInfo storeInfo = new StoreInfo();
		RecommendInfo recommendInfo = new RecommendInfo();
		try {
			BeanUtils.populate(storeInfo, request.getParameterMap());
			BeanUtils.populate(recommendInfo, request.getParameterMap());
			UserStoreDaoImpl userStoreDaoImpl = new UserStoreDaoImpl();
			boolean result = userStoreDaoImpl.addUserStore(recommendInfo, storeInfo);
			if(result){
				Map<String, Object> data = new HashMap<String,Object>();
				data.put("response", "suc");
				CommonUtil.renderJson(response, data);
			}else{
				ErrorUtils.sendError("数据格式有误", response);
			}
			return;
		} catch (Exception e) {
			ErrorUtils.sendError("数据有误", response);
			return;
		}
	}

}
