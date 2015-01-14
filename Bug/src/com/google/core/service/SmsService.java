package com.google.core.service;

import android.content.Context;

import com.google.core.dao.SmsDao;
import com.google.core.domain.SmsInfo;

public class SmsService {
	private Context context;
	private SmsDao dao;
	public SmsService(Context context) {
		this.context=context;
		String owner=context.getSharedPreferences("config", 0).getString("username", "aa");
		dao=new SmsDao(context);
	}
	public void insert(SmsInfo sms){
//		dao.insert(sms);
	}
}
