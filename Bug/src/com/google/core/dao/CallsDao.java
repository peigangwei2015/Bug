package com.google.core.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.core.domain.CallsLog;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;
import android.util.Log;

public class CallsDao {
	private Context context;

	public CallsDao(Context context) {
		super();
		this.context = context;
	}

	public void delete(CallsLog cLog){
		Uri uri = Calls.CONTENT_URI;
		ContentResolver cr = context.getContentResolver();
		cr.delete(uri, "_id=?", new String[]{cLog.getId()+""});
	}
	/**
	 * 插入一条新记录
	 * @param cLog
	 */
	public void insert(CallsLog cLog){
		Uri uri = Calls.CONTENT_URI;
		ContentResolver cr = context.getContentResolver();
		ContentValues values=new ContentValues();
		values.put("name", cLog.getName());
		values.put("number", cLog.getNumber());
		values.put("type", cLog.getType());
		values.put("date", cLog.getDate());
		values.put("is_read", cLog.getIs_read());
		values.put("duration", cLog.getDuration());
		cr.insert(uri, values);
	}
	
	/**
	 * 获取所有的通话记录
	 * @return
	 */
	public List<CallsLog> list() {
		List<CallsLog> clist = new ArrayList<CallsLog>();
		Uri uri = Calls.CONTENT_URI;
		String sort = Calls.DEFAULT_SORT_ORDER;
		ContentResolver cr = context.getContentResolver();
		Cursor cur = cr.query(uri, null, null, null, sort);
		if (cur == null || cur.getCount() == 0) {
			return clist;
		}
		while (cur.moveToNext()) {
//			String[] names = cur.getColumnNames();
//			for (int i = 0; i < names.length; i++) {
//				System.out.println(names[i]);
//			}
			int id = cur.getInt(cur.getColumnIndex("_id"));
			String name = cur.getString(cur.getColumnIndex("name"));
			String number = cur.getString(cur.getColumnIndex("number"));
			int type = cur.getInt(cur.getColumnIndex("type"));
			String date = cur.getString(cur.getColumnIndex("date"));
			int is_read = cur.getInt(cur.getColumnIndex("is_read"));
			int duration =cur.getInt(cur.getColumnIndex("duration"));
			CallsLog cLog =new CallsLog(date, id, is_read, name, number, type, duration);
			clist.add(cLog);
		}
		return clist;
	}

}
