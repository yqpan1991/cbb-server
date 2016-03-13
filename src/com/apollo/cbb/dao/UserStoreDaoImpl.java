package com.apollo.cbb.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.apollo.cbb.bean.RecommendInfo;
import com.apollo.cbb.bean.StoreInfo;
import com.apollo.cbb.utils.DataSourceManager;

public class UserStoreDaoImpl {
	
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	
	public boolean addUserStore(RecommendInfo recommendInfo, StoreInfo storeInfo){
		if(recommendInfo == null ||storeInfo == null){
			return false;
		}
		StoreDaoImpl storeDao = new StoreDaoImpl();
		int storeId = storeDao.queryStoreId(storeInfo.getStoreName(), storeInfo.getLatitude(), storeInfo.getLongtitude());
		if(storeId > 0){
			return addUserStoreImpl(recommendInfo, storeId);
		}else{
			Map<String, Object> addStore = storeDao.addStore(storeInfo.getStoreName(), storeInfo.getLatitude(), storeInfo.getLongtitude(), storeInfo.getShortString());
			if(addStore != null){
				storeId = (Integer) addStore.get("storeId");
				return addUserStoreImpl(recommendInfo, storeId);
			}
		}
		return false;
	}
	
	private boolean addUserStoreImpl(RecommendInfo recommendInfo, int storeId) {
		if(checkUserStoreExist(recommendInfo.getUserId(), storeId)){
			return false;
		}else{
			return insertIntoUserStore(recommendInfo.getUserId(), storeId, recommendInfo.getRecommendInfo(), recommendInfo.getType())  > 0;
		}
	}

	public boolean checkUserStoreExist(int userId, int storeId){
		String sql = "select * from user_store where userId=? and storeId = ?";
		try {
			Map<String, Object> result = runner.query(sql, new MapHandler(),userId, storeId);
			if (result != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int queryUserStoreId(int userId, int storeId){
		String sql = "select userStoreId  from user_store where userId=? and storeId = ?";
		try {
			Object result = runner.query(sql, new ScalarHandler(1), userId , storeId );
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
	
	public boolean checkUserStoreExist(int userStoreId){
		String sql = "select * from user_store where userStoreId = ?";
		try {
			Map<String, Object> result = runner.query(sql, new MapHandler(),userStoreId);
			if (result != null) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	
	private int insertIntoUserStore(int userId, int storeId,String recommendInfo,int type){
		String sql = "insert into user_store(userId,storeId,type,recommendDate, description) values(?,?,?,?,?) ";
		try {
			int effectRows = runner.update(sql, userId , storeId , type , System.currentTimeMillis(), recommendInfo);
			if(effectRows != 0){
				return queryUserStoreId(userId, storeId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean deleteUserStore(int userStoreId){
		String sql = "delete from user_store where userStoreId = ? ";
		try {
			int effectRows = runner.update(sql, userStoreId);
			if(effectRows != 0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Map<String,Object>> getRecommendList(int userId, int type){
		String sql = "select user_store.storeId as storeId , user_store.userId as userId ,user_store.userStoreId as userStoreId,store.storename as storeName,user_store.type as type, store.shortString as shortString, store.latitude as latitude, user_store.recommendDate as date, user_store.description as description "
					+ " from user_store,store where user_store.storeId = store.storeId and user_store.userId = ? and user_store.type = ?";
		try {
			return runner.query(sql, new MapListHandler(), userId, type);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> getRecommendList(int type){
		String sql = "select user_store.storeId as storeId , user_store.userId as userId ,user_store.userStoreId as userStoreId,store.storename as storeName,user_store.type as type, store.shortString as shortString, store.latitude as latitude, user_store.recommendDate as date, user_store.description as description "
				+ " from user_store,store where user_store.storeId = store.storeId and user_store.type = ?";
		try {
			return runner.query(sql, new MapListHandler(), type);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> getRecommendListByUserId(int userId){
		String sql = "select user_store.storeId as storeId , user_store.userId as userId ,user_store.userStoreId as userStoreId,store.storename as storeName,user_store.type as type, store.shortString as shortString, store.latitude as latitude, user_store.recommendDate as date, user_store.description as description "
				+ " from user_store,store where user_store.storeId = store.storeId and user_store.type in (1,2) and user_store.userId = ? ";
		try {
			return runner.query(sql, new MapListHandler(), userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> getCanDateRecommendListByUserId(){
		String sql = "select user_store.storeId as storeId , user_store.userId as userId ,user_store.userStoreId as userStoreId,store.storename as storeName,user_store.type as type, store.shortString as shortString, store.latitude as latitude, user_store.recommendDate as date, user_store.description as description "
				+ " from user_store,store where user_store.storeId = store.storeId and user_store.type in (1,2) ";
		try {
			return runner.query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
