package com.google.core.msgpro;


import com.alibaba.fastjson.JSONObject;
import com.google.core.domain.MsgType;
import com.google.core.service.VRService;
import com.google.core.utils.MsgUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class VoiceMsgPro {

	private Context context;

	public VoiceMsgPro(Context context) {
		this.context = context;
	}

	public void doPro(int type, String data) {
		switch (type) {
		case  MsgType.START_VOICE_RECODER:
				Editor editor=context.getSharedPreferences("config", 0).edit();
				editor.putString("voiceFileName",data);
				editor.commit();
				context.startService(new Intent(context, VRService.class));
				
			break;
		case  MsgType.STOP_VOICE_RECODER:
			context.stopService(new Intent(context, VRService.class));
			MsgUtils.sendToAdmin(context, MsgType.STOP_VOICE_RECODER_SUCCESS);
			break;
		}
		
	}


}
