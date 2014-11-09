package com.zhimeng.loupanguanli.ui;

import com.zhimeng.loupanguanli.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		// 欢迎界面
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(WelcomeActivity.this, MainActivity.class);
				WelcomeActivity.this.startActivity(intent);
				WelcomeActivity.this.finish();
			}
		}, 2500);
	}

}
