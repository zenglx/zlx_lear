package com.zlx.im.activity;

import com.example.videonetty.R;
import com.zlx.im.util.ThreadUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		/**
		 * �����߳�����һЩ������֮����ת����¼����
		 */
		ThreadUtils.runInSubThread(new Runnable() {

			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(new Intent(getApplicationContext(),
						LoginActivity.class));

			}
		});
}
}
