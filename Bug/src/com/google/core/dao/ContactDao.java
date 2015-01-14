package com.google.core.dao;

import java.text.Format;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.core.domain.ContactInfo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Log;

public class ContactDao {
	private static final String TAG = "ContactDao";
	private Context context;
	private String baseUri = "content://com.android.contacts";
	private ContentResolver cr;

	public ContactDao(Context context) {
		this.context = context;
		cr = context.getContentResolver();
	}

	/**
	 * 获取所有的联系人
	 * 
	 * @return
	 */
	public List<ContactInfo> getList() {
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		// 查询raw_contacts表
		String[] raw_contacts_cloumns = { "_id", "display_name" };
		Cursor cursor = cr.query(Uri.parse(baseUri + "/raw_contacts"),
				raw_contacts_cloumns, null, null, null); // 获取手机联系人
		while (cursor.moveToNext()) {
			String _id = cursor.getString(0);
			String display_name = cursor.getString(1);
			// 查询Data表
			String[] data_cloums = { "mimetype", "data1", "_id" };
			Cursor cur2 = cr.query(Uri.parse(baseUri + "/data"), data_cloums,
					"raw_contact_id=?", new String[] { _id }, null);
			while (cur2.moveToNext()) {
				String mimetype = cur2.getString(0);
				// 判断是否是号码
				if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
					String number = cur2.getString(1);
					number = number.replaceAll("\\s|-", "");
					ContactInfo contactInfo = new ContactInfo();
					contactInfo.setName(display_name);
					contactInfo.setNumber(number);
					contactInfo.setId(Integer.parseInt(_id));
					list.add(contactInfo);
				}

			}

		}
		cursor.close();
		return list;
	}
	
	/**
	 * 打印Cursor中的内容
	 * @param cursor
	 */
	
	public void printCursor(Cursor cursor){
		Log.v(TAG, cursor.getColumnCount()+"");
		while (cursor.moveToNext()) {
			Log.v(TAG, "aaaaaaa");
			int columnCount = cursor.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				String columnName=cursor.getColumnName(i);
				String columnValue=cursor.getString(i);
				Log.v(TAG, "第"+cursor.getPosition() +"行       列名："+columnName+" = "+columnValue);
			}
		}
	}
	
	public String getFormatNumber(String number){
		String res=number;
		Uri uri=Uri.parse(baseUri+"/phone_lookup/"+number);
		Cursor cursor=cr.query(uri, new String[]{"number"}	, null, null, null);
//		printCursor(cursor);
		if (cursor!=null && cursor.moveToFirst()) {
			res=cursor.getString(0);
		}
		cursor.close();
		return res;
		
	}
	

	/**
	 * 根据电话号码查询到名字
	 * 
	 * @param number
	 * @return
	 */
	public ContactInfo findName(String number) {
		number = number.startsWith("+86") ? number.substring(3) : number;
		number=getFormatNumber(number);
		
		Cursor cursor = cr.query(Uri.parse(baseUri+"/data" ), new String[] {"raw_contact_id" },
				"data1=?", new String[]{number}, null); // 获取手机联系人

		ContactInfo contactInfo = new ContactInfo();
		if (cursor!=null && cursor.moveToNext()) {
			String raw_contact_id = cursor.getString(0);
			contactInfo.setId(Integer.parseInt(raw_contact_id));
			contactInfo.setNumber(number);
			
			// 查询名字
			Cursor cursor2 = cr.query(Uri.parse(baseUri + "/raw_contacts"),
					new String[] { "display_name" }, "_id = ?",
					new String[] { raw_contact_id }, null);
			if (cursor2!=null && cursor2.moveToNext()) {
				String name=cursor2.getString(0);
				contactInfo.setName(name);
			}
			cursor2.close();
		}
		cursor.close();
		return contactInfo;
	}
}
