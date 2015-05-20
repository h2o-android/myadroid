package com.he2.day10_1;

import com.he2.utils.NetUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2 extends Activity {

	private EditText etUserName;
	private EditText etPassWord;

	public Main2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		etUserName = (EditText) findViewById(R.id.et_username);
		etPassWord = (EditText) findViewById(R.id.et_password);
		etPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());

	}

	public void doGet(View v) {
		final String userName = etUserName.getText().toString().trim();
		final String passWord = etPassWord.getText().toString().trim();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final String state = NetUtils.loginofGet(userName, passWord);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(Main2.this, state, 0).show();
					}
				});
			}
		}).start();

	}

	public void doPost(View V) {
		final String userName = etUserName.getText().toString();
		final String password = etPassWord.getText().toString();

		new Thread(new Runnable() {
			@Override
			public void run() {
				final String state = NetUtils.loginofPost(userName, password);
				// 执行任务在主线程中
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// 就是在主线程中操作
						Toast.makeText(Main2.this, state, 0).show();
						if(state.equals("登入成功")){
							Intent intent = new Intent();
							intent.putExtra("userName", userName);
							setResult(101, intent);
							finish();
						}
					}
				});
			}
		}).start();
	}
}
