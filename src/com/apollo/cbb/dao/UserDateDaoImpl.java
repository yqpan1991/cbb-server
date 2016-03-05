package com.apollo.cbb.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.apollo.cbb.bean.RecommendInfo;
import com.apollo.cbb.bean.StoreInfo;
import com.apollo.cbb.utils.DataSourceManager;

public class UserDateDaoImpl {

	private QueryRunner runner = new QueryRunner(
			DataSourceManager.getDataSource());

	public boolean addDate(RecommendInfo recommendInfo, StoreInfo storeInfo) {
		UserStoreDaoImpl userStoreDao = new UserStoreDaoImpl();
		return userStoreDao.addUserStore(recommendInfo, storeInfo);
	}

	public boolean commentDate(int userStoreId, int userId, String commentInfo) {
		UserStoreDaoImpl userStoreDao = new UserStoreDaoImpl();
		if (userStoreDao.checkUserStoreExist(userStoreId)) {
			if (queryCommentDateId(userStoreId, userId, commentInfo) > 0) {
				return true;
			}
			return insertIntoCommentDateImpl(userStoreId, userId, commentInfo,
					System.currentTimeMillis()) > 0;
		}
		return false;
	}

	private int insertIntoCommentDateImpl(int userStoreId, int userId,
			String commentInfo, long currentTimeMillis) {
		String sql = "insert into comment(userStoreId,description,commentUserId,date) values(?,?,?,?) ";
		try {
			int effectRows = runner.update(sql, userStoreId, commentInfo,
					userId, currentTimeMillis);
			if (effectRows != 0) {
				return queryCommentDateId(userStoreId, userId, commentInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int queryCommentDateId(int userStoreId, int userId,
			String commentInfo) {
		String sql = "select commendId  from comment where userStoreId=? and description = ? and commentUserId = ?";
		try {
			Object result = runner.query(sql, new ScalarHandler(1),
					userStoreId, commentInfo, userId);
			if (result == null) {
				return -1;
			} else {
				return (Integer) result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<Map<String, Object>> getCommentList(int userStoreId) {
		String sql = "select comment.commendId as commentId, comment.description as comment, comment.commentUserId as commentUserId, comment.date as commentDate, user_store.storeId as storeId , user_store.userId as userId ,user_store.userStoreId as userStoreId,store.storename as storeName,user_store.type as type, store.shortString as shortString, store.latitude as latitude, user_store.recommendDate as date, user_store.description as description"
				+ " from user_store,store,comment where user_store.storeId = store.storeId and user_store.userStoreId = comment.userStoreId and user_store.userStoreId = ? order by commentId asc";
		try {
			return runner.query(sql, new MapListHandler(), userStoreId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
