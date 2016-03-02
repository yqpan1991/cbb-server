package com.apollo.cbb.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class ErrorUtils {
	/**
	 * 发送错误信息
	 * 
	 * @param errorMsg 错误信息
	 * @param response
	 */
	public static void sendError(String errorMsg, HttpServletResponse response) {
		Map<String, Object> mapError = new HashMap<String, Object>();
		mapError.put("response", "error");
		mapError.put("error", errorMsg);
		CommonUtil.renderJson(response, mapError);
	}
}
