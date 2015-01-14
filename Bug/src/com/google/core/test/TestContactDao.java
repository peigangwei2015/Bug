package com.google.core.test;

import java.util.List;
import java.util.Map;

import com.google.core.dao.ContactDao;
import com.google.core.dao.SmsDao;
import com.google.core.domain.ContactInfo;

import android.content.ContentValues;
import android.test.AndroidTestCase;

public class TestContactDao extends AndroidTestCase {
	/**
	 * 测试获取全部联系人
	 */
	public void testGetList(){
		ContactDao dao=new ContactDao(getContext());
		List<ContactInfo> list = dao.getList();
		for (ContactInfo map :list) {
			System.out.println(map);
		}
	}
	/**
	 * 测试按照号码获取联系人
	 */
	public void testFindName(){
		ContactDao dao=new ContactDao(getContext());
		ContactInfo number= dao.findName("5556");
		System.out.println(number);
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
