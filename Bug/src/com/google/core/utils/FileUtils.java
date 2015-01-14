package com.google.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
	/**
	 * 将输入流写入文件
	 * @param is
	 * @param path
	 * @return
	 */
	private static File inputStreamToFile(InputStream is, String path) {
		File file = new File(path);
		FileOutputStream fos = null;
		int len = 0;
		byte[] temp = new byte[4 * 1024];
		try {
			fos = new FileOutputStream(file);
			while ((len = is.read(temp)) != -1) {
				fos.write(temp, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 将InputStream 流转换为字符串
	 * 
	 * @param is
	 * @param encode
	 *            字符编码
	 * @return
	 */
	private static String inputstreamToString(InputStream is, String encode) {
		StringBuffer sb = new StringBuffer();
		String line = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, encode));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}
	/**
	 * 读取文本文件
	 * @param path 文件路径
	 * @return
	 */
	public static String readText(String path){
		BufferedReader br=null;
		StringBuffer sb=new StringBuffer();
		String line="";
		try {
			br=new BufferedReader(new FileReader(new File(path)));
			while ((line=br.readLine())!=null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 写入文本文件
	 * @param path 路径
	 * @param content 文件内容
	 * @return
	 */
	public static boolean writeText(String path,String content){
		BufferedWriter bw=null;
		try {
			bw=new BufferedWriter(new FileWriter(new File(path)));
			bw.write(content);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			if (bw!=null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
