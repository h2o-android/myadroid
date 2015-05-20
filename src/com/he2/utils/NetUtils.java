package com.he2.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;

public class NetUtils {
	private static final String TAG = "test";

	public NetUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static String resigerofPost(String name,String password){
		
		HttpURLConnection conn = null;

		try {
			URL url = new URL("http://192.168.3.6:8080/Day10/servlet/Register");

			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);

			String data = "login=" + name + "&password=" + password;

			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i(TAG, "´íÎó´úÂë: " + responseCode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String loginofPost(String name, String password) {
		HttpURLConnection conn = null;

		try {
			URL url = new URL("http://192.168.3.6:8080/Day10/servlet/Login");

			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);

			String data = "login=" + name + "&password=" + password;

			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i(TAG, "´íÎó´úÂë: " + responseCode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String loginofGet(String username, String password) {
		HttpURLConnection conn = null;
		String data = null;
		try {
			data = "login=" + URLEncoder.encode(username,"utf-8") + "&password="
					+ URLEncoder.encode(password,"utf-8");
			Log.i("test", URLEncoder.encode(username,"utf-8"));
			Log.i("test", URLEncoder.encode(password,"utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
/*			String urlpath = "http://192.168.3.6:8080/Day10/servlet/Login?" + data;
			urlpath = URLEncoder.encode(urlpath);
			urlpath = URLEncoder.encode(urlpath);*/
			URL url = new URL("http://192.168.3.6:8080/Day10/servlet/Login?"
					+ data);
//			URL url = new URL(urlpath);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000); 
			conn.setReadTimeout(5000); 

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i(TAG, "´íÎó´úÂë: " + responseCode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect(); // ¹Ø±ÕÁ¬½Ó
			}
		}
		return null;
	}

	private static String getStringFromInputStream(InputStream is) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;

		try {
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String html = baos.toString();
		try {
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
	}

}
