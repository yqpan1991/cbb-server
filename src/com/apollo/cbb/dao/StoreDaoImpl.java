package com.apollo.cbb.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.apollo.cbb.utils.DataSourceManager;

public class StoreDaoImpl {
	
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	
	
	public Map<String,Object> addStore(String storeName, double latitude, double longtitude, String shortString){
		if(!checkStoreExist(storeName, latitude, longtitude)){
			int storeId = insertIntoDb(storeName, latitude, longtitude, shortString); 
			if(storeId > 0){
				Map<String,Object> result = new HashMap<String,Object>();
				result.put("storeId", storeId);
				return result;
			}
		}
		return null;
	}
	
	private int insertIntoDb(String storeName, double latitude, double longtitude, String shortString) {
		String sql = "insert into store(storeName,latitude,longtitude,shortString) values(?,?,?,?) ";
		try {
			int effectRows = runner.update(sql, storeName , latitude , longtitude , shortString);
			if(effectRows != 0){
				return queryStoreId(storeName, latitude, longtitude);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int queryStoreId(String storeName,double latitude, double longtitude){
		String sql = "select storeId from store where storeName = ? and latitude = ? and longtitude = ?";
		try {
			Object result = runner.query(sql, new ScalarHandler(1), storeName , latitude , longtitude);
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
	
	
	public boolean checkStoreExist(String storeName,double latitude, double longtitude){
		String sql = "select * from store where storeName = ? and latitude = ? and longtitude = ?";
		try {
			Object result = runner.query(sql, new ScalarHandler(1), storeName , latitude , longtitude);
			if(result == null){
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkStoreIdExist(int storeId){
		String sql = "select * from store where storeId=?";
		try {
			Map<String, Object> query1 = runner.query(sql, new MapHandler(),storeId);
			if (query1 != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteStoreInfo(int storeId){
		String sql = "delete from store where storeId = ? ";
		try {
			int effectRows = runner.update(sql, storeId);
			if(effectRows != 0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
}
