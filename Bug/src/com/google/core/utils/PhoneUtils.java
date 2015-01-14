package com.google.core.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.android.internal.telephony.ITelephony;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;


public class PhoneUtils {

	/**
	 * 获取手机位置信息
	 * @param context
	 * @return
	 */
	public static Map<String,Integer> getLocation(Context context) {
		Map<String,Integer> map=new HashMap<String, Integer>();
		TelephonyManager mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// 返回值MCC + MNC
		String operator = mTelephonyManager.getNetworkOperator();
		int mnc = Integer.parseInt(operator.substring(3));
		map.put("mnc", mnc);
		// 中国移动和中国联通获取LAC、CID的方式
		GsmCellLocation location = (GsmCellLocation) mTelephonyManager
				.getCellLocation();
		int lac = location.getLac();
		int cell = location.getCid();
		map.put("lac", lac);
		map.put("cell", cell);
		return map;
	}
public static String getMyDriverId(Context context){
		TelephonyManager phoneMgr=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return  phoneMgr.getDeviceId(); 
	}
	
	//	
	public static String getMyNumber(Context context){
		TelephonyManager phoneMgr=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String num=phoneMgr.getLine1Number(); //txtPhoneNumber是一个EditText 用于显示手机号
		if (num!=null && !num.trim().equals("")) {
			return num;
		}else{
			return getMyDriverId(context);
		}
	}
	
	public static NetworkInfo getNetworkInfo(Context context) {
		// 获取网络连接状况
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}
	/**
	 * 打开数据连接
	 * @param cxt
	 * @param state
	 */
	public static void setDataConnectionState(Context cxt, boolean state) {
		ConnectivityManager connectivityManager = null;
		Class connectivityManagerClz = null;
		try {
			connectivityManager = (ConnectivityManager) cxt
					.getSystemService(cxt.CONNECTIVITY_SERVICE);
			connectivityManagerClz = connectivityManager.getClass();
			Method method = connectivityManagerClz.getMethod(
					"setMobileDataEnabled", new Class[] { boolean.class });
			method.invoke(connectivityManager, state);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 拨打 别人电话
	 * @param context
	 * @param phoneNumber
	 */
	public static void callOtherPhone(Context context,String phoneNumber) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		context.startActivity(intent);
	}
	/**
	 * 挂断电话
	 * @param context
	 */
	public static void endCall(Context context) {
		try {
			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getITelephony", (Class[]) null);
			getITelephonyMethod.setAccessible(true);
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(context.TELEPHONY_SERVICE);
			ITelephony iTelephony = (ITelephony) getITelephonyMethod.invoke(tm,
					(Object[]) null);
			iTelephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
