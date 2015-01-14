package com.google.core.test;

import com.google.core.domain.ContactInfo;
import com.google.core.utils.MsgUtils;

import android.test.AndroidTestCase;

public class TestMsgUtils  extends AndroidTestCase{
	public void testForamt(){
		String str= MsgUtils.format("aa", 1 ,new ContactInfo("张珊", "123455676"));
		System.out.println(str);
	}
}
