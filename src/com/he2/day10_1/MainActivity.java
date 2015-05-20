package com.he2.day10_1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "test";
	private EditText etUrl;
	private TextView textView1;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// tvHtml.setText((String)msg.obj);
				Toast.makeText(MainActivity.this, "下载成功", 1).show();
				break;
			case 2:
				Toast.makeText(MainActivity.this, "本地已有文件,无需下载", 1).show();
				break;
			case 0:
				Toast.makeText(MainActivity.this, "访问失败", 1).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etUrl = (EditText) findViewById(R.id.et_url);
		// tvHtml = (TextView) findViewById(R.id.tv_html);

	}

	public void login(View v) {
		Intent intent = new Intent(MainActivity.this, Main2.class);
		startActivityForResult(intent, 100);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == 101) {
			String userName = data.getStringExtra("userName");
			textView1 = (TextView) this.findViewById(R.id.tv1);
			textView1.setText("hello," + userName + "!欢迎回来");
		}
	}

	public void resiger(View v) {
		Intent intent = new Intent(MainActivity.this, Resiger.class);
		startActivity(intent);
	}

	public void downfile(View v) {
		// TODO Auto-generated method stub
		// final String url = etUrl.getText().toString();
		// http://android-wallpapers.25pp.com/20140404/1630/533e6da6023b320_900x675.jpg
		final String url = etUrl.getText().toString();
		int len = url.lastIndexOf("/");
		final String filename = url.substring(len + 1, url.length());
		File file = new File(getCacheDir(), filename);
		if (file.exists()) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				}
			}).start();
			// Bitmap bm =new
			// BitmapFactory().decodeFile(file.getAbsolutePath());
			// ImageView iv= (ImageView) findViewById(R.id.iv_pic);
			// iv.setImageBitmap(bm);
		} else {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String fileinfomation = getfileFromInternet(url, filename);

					if ("true".equals(fileinfomation)) {
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 0;
						handler.sendMessage(msg);
					}

				}
			}).start();
		}
	}

	private String getfileFromInternet(String url, String filename) {
		// TODO Auto-generated method stub
		try {
			URL mURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();

			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);

			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				File file = new File(getCacheDir(), filename);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = is.read(b)) != -1) {
					fos.write(b, 0, len);
				}
				is.close();
				fos.close();
				return "true";
			} else {
				Log.i(TAG, "访问失败: " + responseCode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * private String getStringFromInputStream(InputStream is) throws
	 * IOException { // TODO Auto-generated method stub
	 * 
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); byte[] buffer =
	 * new byte[1024]; int len = -1;
	 * 
	 * while((len = is.read(buffer))!= -1){ baos.write(buffer,0,len); }
	 * is.close();
	 * 
	 * String html = baos.toString(); String charset= "UTF-8";
	 * if(html.contains("gbk") || html.contains("gb2312") ||
	 * html.contains("GBK") || html.contains("GB2312")) { charset = "gbk"; }
	 * html = new String(baos.toByteArray(), charset); baos.close(); return
	 * html; }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
