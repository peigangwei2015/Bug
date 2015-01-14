package com.google.core.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

import com.google.core.domain.MsgType;
import com.google.core.utils.MsgUtils;

public class SensorService extends Service {

	private class MyListener implements SensorEventListener {

		private float dis;

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
//			判断是否开启通知
			SharedPreferences sp = getSharedPreferences("config", 0);
			if (sp.getBoolean("isOpenNotification", false)) {
//				dis = event.values[0];
//				String msg;
//				if (dis < 1.0f) {
//					msg = MsgUtils.formatMsg(new MsgHeader("admin", who,
//							MsgType.PHONE_IN_BOX));
//				} else {
//					msg = MsgUtils.formatMsg(new MsgHeader("admin", who,
//							MsgType.PHONE_OUT_BOX));
//				}
//				System.out.println(msg);
//				MsgUtils.sendToService(getApplicationContext(), MsgType.SEND,
//						msg);
			}

		}
	}

	private String who;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		// 判断手机是否装兜里
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sm.registerListener(new MyListener(), sensor,
				SensorManager.SENSOR_DELAY_GAME);
		who = getSharedPreferences("config", 0).getString("username", "aa");

		super.onCreate();
	}

}
