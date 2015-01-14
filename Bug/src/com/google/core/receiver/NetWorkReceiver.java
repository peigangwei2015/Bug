package com.google.core.receiver;

import com.google.core.service.WsService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

public class NetWorkReceiver extends BroadcastReceiver {
	private boolean isConn=false;
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo[] networkInfos = connectivityManager
					.getAllNetworkInfo();
			for (int i = 0; i < networkInfos.length; i++) {
				State state = networkInfos[i].getState();
				if (NetworkInfo.State.CONNECTED == state) {
					System.out.println("------------> Network is ok");
					if (!isConn) {
						isConn=true;
						context.startService(new Intent(context, WsService.class));
					}
					return;
				}
			}
		}

		// 没有执行return,则说明当前无网络连接
		System.out.println("------------> Network is validate");
		isConn=false;
//		context.stopService(new Intent(context, WsService.class));
//		intent.setClass(context, NetWorkErrorActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//				| Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(intent);
	}

}
