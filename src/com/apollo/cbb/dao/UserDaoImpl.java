package com.apollo.cbb.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.apollo.cbb.constant.UserConst;
import com.apollo.cbb.utils.DataSourceManager;


public class UserDaoImpl {
	
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	
	public Map<String,Object> register(String userName, String password, int type){
		Map<String, Object> map = null;
		if(checkValidate(userName)){
			map = new HashMap<String, Object>();
			//insert,并且获取到id即可
			int id = insertIntoDb(userName,password,type);
			map.put("userid", id);
		}
		return map;
	}
	
	private int insertIntoDb(String userName, String password, int type) {
		String sql = "insert into user_info(userName,password,type,status,loginStatus) values(?,?,?,?,?) ";
		try {
			//TODO 此处需要查询,返回的是影响的行数还是说返回的是id
			int effectColumn = runner.update(sql, userName , password , type , UserConst.USER_STATUS_VALID , UserConst.USER_LOGIN_STATUS_UNLOGIN);
			if(effectColumn != 0){
				return queryUserId(userName, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private boolean checkValidate(String userName){
		/**
		 * 查询用户名是否存在
		 */
		String sql = "select * from user_info where userName=?";
		try {
			Map<String, Object> query1 = runner.query(sql, new MapHandler(),userName);
			if (query1 == null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkUserIdExist(int userId){
		/**
		 * 查询用户名是否存在
		 */
		String sql = "select * from user_info where userId=?";
		try {
			Map<String, Object> query1 = runner.query(sql, new MapHandler(),userId);
			if (query1 != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean logout(int userId) {
		if(checkUserIdExist(userId)){
			setLoginStatus(userId, UserConst.USER_LOGIN_STATUS_UNLOGIN);
			return true;
		}
		return false;
	}

	private void setLoginStatus(int userId , int status){
		String sql = "update user_info  set loginStatus = ? where userId = ?";
		try {
			runner.update(sql, status , userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int queryUserId(String userName , String password){
		String sql = "select userId from user_info where userName = ? and password = ?";
		try {
			Object result = runner.query(sql, new ScalarHandler(1), userName , password);
			if(result == null){
				return -1;
			}else{
				return (Integer)result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int queryUserId(String userName , String password , int type){
		String sql = "select userId from user_info where userName = ? and password = ? and type = ?";
		try {
			Object result = runner.query(sql, new ScalarHandler(1), userName , password , type);
			if(result == null){
				return -1;
			}else{
				return (Integer)result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Map<String, Object> login(String userName, String password){
		/**
		 * 查询用户名是否存在
		 */
		String sql = "select userId as userId,nickName as nickName, type as userType,userName as userName, password as password from user_info where userName = ? and password = ? ";
		try {
			Map<String, Object> map = runner.query(sql, new MapHandler(), userName, password);
			if(map != null){
				int userId = (Integer) map.get("userId");
				setLoginStatus(userId, UserConst.USER_LOGIN_STATUS_LOGEDIN);
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String, Object>> listAllUsers(){
		String sql = "select userId , userName from user_info where status = "+UserConst.USER_STATUS_VALID;
		try {
			return runner.query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateUserInfo(int userId,String nickName){
		if(checkUserIdExist(userId)){
			String sql = "update user_info  set nickName = ? where userId = ?";
			try {
				int effectColumns = runner.update(sql, nickName , userId);
				return effectColumns > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
