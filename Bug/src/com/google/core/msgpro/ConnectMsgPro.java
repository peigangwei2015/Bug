package com.google.core.msgpro;

import com.alibaba.fastjson.JSONObject;
import com.google.core.domain.MsgType;
import com.google.core.utils.MsgUtils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class ConnectMsgPro {
	private Context context;
	private WifiManager wm;

	public ConnectMsgPro(Context context) {
		this.context = context;
	}

	public void doPro(int type, String data) {
		switch (type) {
		case MsgType.LOGIN_SUCCESS:
			Toast.makeText(context, "登陆成功！", 0).show();
			break;

		}
	}

}
