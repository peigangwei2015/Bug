package com.google.core.msgpro;

import com.alibaba.fastjson.JSONObject;
import com.google.core.domain.MsgType;
import com.google.core.utils.PhoneUtils;

import android.content.Context;

public class CallMsgPro {

	private Context context;

	public CallMsgPro(Context context) {
		this.context=context;
	}

	public void doPro(int type, String data) {
		switch (type) {
		case MsgType.CALL_OTHER_PHONE:
			//拨打别人电话
			break;
		case MsgType.END_CALL:
//			挂断电话
			PhoneUtils.endCall(context);
			break;

		default:
			break;
		}
	}

}
