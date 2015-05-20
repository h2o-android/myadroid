package com.he2.day10_1;


import com.he2.utils.NetUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Resiger extends Activity {
	private EditText myusername;
	private EditText mypassword;
	private CheckBox myremeber;
	private Button mylogin;

	public Resiger() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		// int demo = intent.getIntExtra("demo", 0);

		myusername = (EditText) findViewById(R.id.myusername);
		mypassword = (EditText) findViewById(R.id.mypassword);
		mypassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		myremeber = (CheckBox) findViewById(R.id.myremeber);
		mylogin = (Button) findViewById(R.id.mylogin);
		mylogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (myremeber.isChecked()) {
					final String username = myusername.getText().toString()
							.trim();
					final String password = mypassword.getText().toString()
							.trim();
					new Thread(new Runnable() {
						@Override
						public void run() {
							final String state = NetUtils.resigerofPost(username, password);
							// 执行任务在主线程中
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// 就是在主线程中操作
									Toast.makeText(Resiger.this, state, 0)
											.show();
									if(state.equals("注册成功")){
										Toast.makeText(Resiger.this, "即将跳转登入界面", 0)
										.show();
										Intent intent = new Intent(Resiger.this, Main2.class);
										startActivity(intent);
									}
								}
							});
						}
					}).start();
				}
			}

		});
	}

}
