package com.google.core.msgpro;

import com.alibaba.fastjson.JSONObject;
import com.google.core.CameraActivity;
import com.google.core.domain.MsgType;

import android.content.Context;
import android.content.Intent;

public class CameraMsgPro {

	private Context context;

	public CameraMsgPro(Context context) {
		this.context=context;
	}

	public void doPro(int type, String data) {
		Intent intent = new Intent(context, CameraActivity.class);
		switch (type) {
		case MsgType.CAMERA:
//			int camId = data.getInteger("body");
//			intent.putExtra("camera", camId);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(intent);
			break;
		}
	}

}
