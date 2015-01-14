package com.google.core.msgpro;

import com.alibaba.fastjson.JSONObject;
import com.google.core.domain.MsgType;
import com.google.core.utils.MsgUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;

public class OptMsgPro {

	private Context context;
	private String smsg;

	public OptMsgPro(Context context) {
		this.context = context;
	}

	public void doPro(int type, String data) {
		SharedPreferences sp = context.getSharedPreferences("config", 0);
		Editor edit = sp.edit();
		WifiManager wm;
		switch (type) {
		case MsgType.OPEN_NOTIFICATION:
			// 开启通知提醒服务
			edit.putBoolean("isOpenNotification", true);
			MsgUtils.sendToAdmin(context, MsgType.OPEN_NOTIFICATION_SUCCESS);
			break;
		case MsgType.CLOSE_NOTIFICATION:
			// 关闭通知提醒服务
			edit.putBoolean("isOpenNotification", false);
			MsgUtils.sendToAdmin(context, MsgType.CLOSE_NOTIFICATION_SUCCESS);
			break;
		case MsgType.OPEN_WIFI:
			// 打开WIFI
			wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
			if (wm != null)
				wm.setWifiEnabled(true);
			MsgUtils.sendToAdmin(context, MsgType.OPEN_WIFI_SUCCESS);
			break;
		case MsgType.CLOSE_WIFI:
			// 关闭WIFI
			wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
			if (wm != null)
				wm.setWifiEnabled(false);
			MsgUtils.sendToAdmin(context, MsgType.CLOSE_WIFI_SUCCESS);
			break;
		}

		edit.commit();

	}

}
