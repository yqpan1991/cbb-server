package com.apollo.cbb.dao.test;

import org.junit.Before;
import org.junit.Test;

import com.apollo.cbb.dao.StoreDaoImpl;

public class StoreDaoTest {
	
	private StoreDaoImpl storeDao;
	
	@Before
	public void init(){
		storeDao = new StoreDaoImpl();
	}
	
	@Test
	public void addStore(){
//		System.out.println(storeDao.addStore("小六汤包", 100, 200,"查单呢"));
		System.out.println(storeDao.addStore("新疆菜馆test5", 50.3, 80.7, "我是短串"));
	}
	
	@Test
	public void queryStoreId(){
		System.out.println(storeDao.queryStoreId("新疆菜馆test5", 50.3, 80.6));
	}
	
	@Test
	public void testCheckStoreExist(){
		System.out.println(storeDao.checkStoreExist("新疆菜馆test5", 50.3, 80.4));
	}
	
	@Test
	public void testCheckStoreExistById(){
		System.out.println(storeDao.checkStoreIdExist(15));
	}
	
	@Test
	public void testDeleteStore(){
		System.out.println(storeDao.deleteStoreInfo(11));
		System.out.println(storeDao.deleteStoreInfo(11));
	}
}
