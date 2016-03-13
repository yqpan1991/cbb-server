package com.apollo.cbb.dao.test;

import org.junit.Before;
import org.junit.Test;

import com.apollo.cbb.bean.RecommendInfo;
import com.apollo.cbb.bean.StoreInfo;
import com.apollo.cbb.constant.RecommendConst;
import com.apollo.cbb.dao.UserStoreDaoImpl;

public class UserStoreDaoTest {
	
	private UserStoreDaoImpl userStoreDao;
	
	@Before
	public void init(){
		userStoreDao = new UserStoreDaoImpl();
	}
	
	@Test
	public void addStoreInfo(){
		RecommendInfo info = new RecommendInfo();
/*		info.recommendInfo="测试数据";
		info.type = RecommendConst.RECOMMEND_TYPE_NORMAL;
		info.userId = 2;*/
		
		
		StoreInfo storeInfo = new StoreInfo();
/*		storeInfo.latitude = 50;
		storeInfo.longtitude = 80;
		storeInfo.shortString="去你妹的";
		storeInfo.storeName="测试饭店";*/
		
		System.out.println(userStoreDao.addUserStore(info, storeInfo));
	}
	
	@Test
	public void checkUserStoreExist(){
		 System.out.println(userStoreDao.checkUserStoreExist(1, 1));
		 System.out.println(userStoreDao.checkUserStoreExist(1, 2));
		 System.out.println(userStoreDao.checkUserStoreExist(1, 3));
	}
	
	@Test
	public void queryUserStoreId(){
		System.out.println(userStoreDao.queryUserStoreId(1, 1));
		System.out.println(userStoreDao.queryUserStoreId(1, 2));
		System.out.println(userStoreDao.queryUserStoreId(1, 3));
	}
	
	
	@Test
	public void deleteUserStore(){
		//TODO 未测试。。。。。
		System.out.println(userStoreDao.deleteUserStore(5));
	}
	
	@Test
	public void getRecommendList(){
		System.out.println(userStoreDao.getRecommendList(1, 3));
	}
	
	@Test
	public void testGetRecommendList(){
		System.out.println(userStoreDao.getRecommendList(1));
	}
	
	
	
	
}
