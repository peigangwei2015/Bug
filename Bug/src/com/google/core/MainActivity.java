package com.google.core;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.core.R;
import com.google.core.domain.MsgType;
import com.google.core.receiver.ScreenReceiver;
import com.google.core.service.VRService;
import com.google.core.service.WsService;
import com.google.core.utils.MsgUtils;

public class MainActivity extends Activity implements OnClickListener, Callback {
	public static Camera mCamera;
	public static boolean mPreviewRunning;
	public static SurfaceHolder mSurfaceHolder;
	public static SurfaceView mSurfaceView;
	public static final String TAG = "MainActivity";
	private Button bt_connect;
	private Button bt_login;
	private EditText et_server_ip;
	private EditText et_username;
	private String msg;
	private SharedPreferences sp;

	@Override
	public void onClick(View v) {

		Editor editor = sp.edit();
		switch (v.getId()) {
		case R.id.bt_activity_main_connect:
			String ip = et_server_ip.getText().toString().trim();
			if (!ip.equals("")) {
				editor.putString("server_ip", ip);
			}
			MsgUtils.connecting(MainActivity.this);

			break;
		case R.id.bt_activity_main_login:
			String userName = et_username.getText().toString().trim();
			if (!userName.equals("")) {
				editor.putString("username", userName);
				MsgUtils.sendToAdmin(getApplicationContext(), MsgType.LOGIN, "userName");
			} else {
				Toast.makeText(getApplicationContext(), "用户名不能为空！", 0).show();
			}
			break;
		}
		editor.commit();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSurfaceView = (SurfaceView) findViewById(R.id.recoder_surfaceview);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		sp = getSharedPreferences("config", 0);
		et_server_ip = (EditText) findViewById(R.id.ed_server_ip);
		et_server_ip.setText(sp.getString("erver_ip", ""));

		et_username = (EditText) findViewById(R.id.usernmae);
		et_username.setText(sp.getString("username", ""));
		bt_connect = (Button) findViewById(R.id.bt_activity_main_connect);
		bt_connect.setOnClickListener(this);
		bt_login = (Button) findViewById(R.id.bt_activity_main_login);
		bt_login.setOnClickListener(this);
		// 启动连接Service
		Intent intent = new Intent(getApplicationContext(), WsService.class);
		startService(intent);

	}
	
	public void startVoice(View view){
		Editor editor=sp.edit();
		editor.putString("voiceFileName", "testing");
		editor.commit();
		
		startService(new Intent(this, VRService.class));
	}
	
	public void stopVoice(View view){
		stopService(new Intent(this, VRService.class));
	}
	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

}
