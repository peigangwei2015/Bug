package com.google.core.msgpro;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.google.core.domain.MsgType;
import com.google.core.service.BDLocationService;
import com.google.core.utils.MsgUtils;

public class LocationMsgPro {

	private Context context;
	private String smsg;

	public LocationMsgPro(Context context) {
		this.context=context;
	}

	public void doPro(int type, String data) {
		Intent intent = new Intent(context, BDLocationService.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		switch (type) {
		case MsgType.START_LOCATION:
			// 开始定位
			context.startService(intent);
			MsgUtils.sendToAdmin(context, MsgType.START_LOCATION_SUCCESS);
			break;
		case MsgType.STOP_LOCATION:
			// 停止定位
			context.stopService(intent);
			MsgUtils.sendToAdmin(context, MsgType.STOP_LOCATION_SUCCESS);
			break;
		}
	}

}
