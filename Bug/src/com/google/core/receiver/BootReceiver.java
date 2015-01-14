package com.google.core.receiver;

import com.google.core.service.WsService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 开机自启动
 * @author Administrator
 *
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, WsService.class));
	}

}
