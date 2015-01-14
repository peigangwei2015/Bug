package com.google.core.test;

import java.util.List;
import java.util.Map;

import com.google.core.dao.SmsDao;
import com.google.core.domain.ConversInfo;
import com.google.core.domain.SmsInfo;

import android.content.ContentValues;
import android.test.AndroidTestCase;

public class TestSmsDao extends AndroidTestCase {
	/**
	 * 测试获取会话列表
	 */
	public void testGetConversationList(){
		SmsDao dao=new SmsDao(getContext());
		List<ConversInfo> list = dao.getConversationList();
		for (ConversInfo map :list) {
			System.out.println(map);
		}
	}
	/**
	 * 测试获取短信列表
	 */
	public void testFindSmsListByNumber(){
		SmsDao dao=new SmsDao(getContext());
		List<SmsInfo> list = dao.findSmsListByNumber("123");
	}
	
	/**
	 * 测试修改短信内容
	 */
	public void testUpdateById(){
		SmsDao dao=new SmsDao(getContext());
		ContentValues values=new ContentValues();
		values.put("body", "哈哈");
		boolean res = dao.updateById("3", values);
		assertTrue(res);
	}
	
	/**
	 * 测试删除短信
	 */
	public void testDelById(){
		SmsDao dao=new SmsDao(getContext());
		boolean res = dao.delById("3");
		assertTrue(res);
	}

}
