package com.google.core.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.core.domain.ConversInfo;
import com.google.core.domain.SmsInfo;

public class SmsDao {
	public static final String SMS_URI_ALL = "content://sms";

	public static final String SMS_URI_INBOX = "content://sms/inbox";

	private static final String TAG = "SmsDao";
	private ContactDao contactDao;

	private Context context;

	private ContentResolver cr;
	private Uri uri;

	public SmsDao(Context context) {
		this.context = context;
		contactDao = new ContactDao(context);
		cr = context.getContentResolver();
		uri = Uri.parse(SMS_URI_ALL);
	}

	/**
	 * 按照ID删除短信
	 * 
	 * @param id
	 * @return
	 */
	public boolean delById(String id) {
		return delByIds(new String[] { id });
	}

	/**
	 * 按照号码删除短信
	 * 
	 * @param number
	 * @return
	 */
	public boolean delByNumber(String number) {
		if (TextUtils.isEmpty(number))
			return false;

		int res = cr.delete(uri, "address=?", new String[] { number });
		return res > 0 ? true : false;
	}

	/**
	 * 按照ID修改短信内容
	 * 
	 * @param id
	 * @param values
	 * @return
	 */
	public boolean updateById(String id, ContentValues values) {
		int res = cr.update(uri, values, "_id=?", new String[] { id });
		return res > 0 ? true : false;
	}

	/**
	 * 获取会话列表
	 * 
	 * @return
	 */
	public List<ConversInfo> getConversationList() {
		List<ConversInfo> list = new ArrayList<ConversInfo>();
		uri = Uri.parse("content://sms/conversations");
		Cursor cur = cr.query(uri,new String[]{"sms.thread_id AS thread_id","sms.body AS body","groups.msg_count AS count","sms.address AS address","sms.date AS date"}, null,
				null, "date desc");
//		printCursor(cur);
		while (cur.moveToNext()) {
			ConversInfo conversInfo = new ConversInfo();
			conversInfo.setId(cur.getInt(0));
			conversInfo.setSnippet(cur.getString(1));
			conversInfo.setMessageCount(cur.getInt(2));
			conversInfo.setAddress(cur.getString(3));
			conversInfo.setDate(cur.getLong(4));

			// 查询发送者
//			Cursor cur2 = cr.query(uri, new String[] { "address" },
//					"thread_id=?",
//					new String[] { String.valueOf(recipient_ids) }, null);
//			if (cur2.moveToNext()) {
//				String address = cur2.getString(0);
//				conversInfo.setAddress(address);
//				conversInfo.setName(contactDao.findName(address).getName());
//			}
//			cur2.close();
			list.add(conversInfo);

			// 将Map添加到集合中

		}
		cur.close();
		return list;
	}

	/**
	 * 打印结果集
	 * 
	 * @param cur
	 */
	private void printCursor(Cursor cur) {
		if (cur != null && cur.getCount() > 0) {
			while (cur.moveToNext()) {
				for (int i = 0; i < cur.getColumnCount(); i++) {
					String name = cur.getColumnName(i);
					String value = cur.getString(i);
					Log.v(TAG, "第"+cur.getPosition()+"行                  "+name+"===="+value);
				}
			}
		}
	}

	/**
	 * 按照电话号码读取短信列表
	 * 
	 * @param number
	 * @return
	 */
	public List<SmsInfo> findSmsListByNumber(String number) {
		List<SmsInfo> list = new ArrayList<SmsInfo>();
		if (TextUtils.isEmpty(number)) {
			return list;
		}
		// 判断号码前是否有+86如果有则去掉+86
		number = number.startsWith("+86") ? number.substring(3) : number;
		String formartNumber = contactDao.getFormatNumber(number);
		Cursor cur = cr.query(uri, null, "address=? or address=?  ",
				new String[] { number, formartNumber }, "date asc");
		while (cur.moveToNext()) {
			int _id = cur.getInt(cur.getColumnIndex("_id"));
			String address = cur.getString(cur.getColumnIndex("address"));
			String body = cur.getString(cur.getColumnIndex("body"));
			long date = cur.getLong(cur.getColumnIndex("date"));
			int type = cur.getInt(cur.getColumnIndex("type"));
			SmsInfo sms = new SmsInfo();
			sms.setId(_id);
			sms.setAddress(address);
			sms.setBody(body);
			sms.setDate(date);
			sms.setType(type);
			list.add(sms);

		}
		cur.close();
		return list;
	}

	/**
	 * 插入短信
	 * 
	 * @param values
	 * @return
	 */
	public boolean insert(ContentValues values) {
		if (values == null)
			return false;
		uri = Uri.parse(SMS_URI_INBOX);
		cr.insert(uri, values);
		return true;

	}

	/**
	 * 删除短信
	 * 
	 * @param ids
	 * @return
	 */
	public boolean delByIds(String[] ids) {
		if (ids != null && ids.length > 0) {
			int res = cr.delete(uri, "_id=?", ids);
			return res > 1 ? true : false;
		}
		return false;

	}

}
