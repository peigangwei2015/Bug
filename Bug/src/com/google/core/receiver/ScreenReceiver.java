package com.google.core.receiver;

import com.google.core.domain.MsgType;
import com.google.core.utils.MsgUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver{

	private String msg;
	@Override
	public void onReceive(Context context, Intent intent) {
//		String owner=context.getSharedPreferences("config", 0).getString("username", "aa");
//		String action=intent.getAction();
//		System.out.println(action);
//		if (action.equals(Intent.ACTION_SCREEN_OFF)) {
//			msg=MsgUtils.formatMsg(new MsgHeader("admin", owner, MsgType.SCREEN_OFF));
//		}else if(action.equals(Intent.ACTION_SCREEN_ON)){
//			msg=MsgUtils.formatMsg(new MsgHeader("admin", owner, MsgType.SCREEN_ON));
//		}else if(action.equals(Intent.ACTION_USER_PRESENT)){
//			msg=MsgUtils.formatMsg(new MsgHeader("admin", owner, MsgType.USER_PRESENT));
//		}
//		boolean isSend = context.getSharedPreferences("config", 0).getBoolean("isOpenNotification", false);
//		if (!"".equals(msg) && isSend) {
//			MsgUtils.sendToService(context,msg);
//		}
	}

}
