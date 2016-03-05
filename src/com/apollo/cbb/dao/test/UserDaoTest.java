package com.apollo.cbb.dao.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.apollo.cbb.constant.UserConst;
import com.apollo.cbb.dao.UserDaoImpl;

public class UserDaoTest {
	
	private UserDaoImpl userDao;
	
	@Before
	public void init(){
		userDao = new UserDaoImpl();
	}
	
	@Test
	public void testQueryUserId(){
		System.out.println(userDao.queryUserId("admin", "123", 1));
	}
	
	@Test
	public void testRegisterExists(){
		//case 1 : exist
		Map<String, Object> register = userDao.register("admin", "123", UserConst.USER_TYPE_ADMIN);
		System.out.println(register != null);
	}
	
	@Test
	public void testRegisterNotExists(){
		Map<String, Object> register = userDao.register("testUser", "123", UserConst.USER_TYPE_NORMAL);
		System.out.println(register != null);
	}
	
	@Test
	public void checkUserIdExist(){
		Assert.assertEquals(true, userDao.checkUserIdExist(1));
		Assert.assertEquals(true, userDao.checkUserIdExist(5));
	}
	
	@Test
	public void testLogout(){
		Assert.assertEquals(true, userDao.logout(1));
	}
	
	@Test
	public void testLogin(){
		System.out.println(userDao.login("大叔", 123+""));
		System.out.println(userDao.login("大叔", 124+""));
	}
	
	@Test
	public void testListAllUsers(){
		System.out.println(userDao.listAllUsers().toString());
	}
	
	@Test
	public void testUpdateUserInfo(){
		System.out.println(userDao.updateUserInfo(5, "小甜甜"));
	}
}
