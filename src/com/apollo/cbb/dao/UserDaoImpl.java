package com.apollo.cbb.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.apollo.cbb.utils.DataSourceManager;


public class UserDaoImpl {
	
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	
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
}
