package com.apollo.cbb.test;

import org.junit.Test;

import com.apollo.cbb.dao.UserDaoImpl;

public class UserDaoTest {
	
	@Test
	public void testQueryUserId(){
		UserDaoImpl userDao = new UserDaoImpl();
		System.out.println(userDao.queryUserId("admin", "123", 1));
	}
}
