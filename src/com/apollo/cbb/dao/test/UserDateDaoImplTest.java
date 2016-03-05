package com.apollo.cbb.dao.test;

import org.junit.Before;
import org.junit.Test;

import com.apollo.cbb.dao.UserDateDaoImpl;

public class UserDateDaoImplTest {
	
	private UserDateDaoImpl userDateDao = null;
	
	@Before
	public void init(){
		userDateDao = new UserDateDaoImpl();
	}
	
	@Test
	public void commentDate(){
		System.out.println(userDateDao.commentDate(3, 1, "其实吐啊吐啊你就习惯了"));
	}
	
	@Test
	public void queryCommentDataId(){
		System.out.println(userDateDao.queryCommentDateId(5, 1, "其实吐啊吐啊你就习惯了"));
	}
	
	@Test
	public void getCommentList(){
		System.out.println(userDateDao.getCommentList(3));
	}
}
