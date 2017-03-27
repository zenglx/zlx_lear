package com.zlx.im.util;

import android.os.Handler;

public class ThreadUtils {

	/**
	 * 运行在子线程(发送数据)
	 * 
	 * @param r
	 */
	public static void runInSubThread(Runnable r) {
		new Thread(r).start();
	}

	private static Handler handler = new Handler();

	/**
	 * 运行在主线程(UI 线程 更新界面)
	 * 
	 * @param r
	 */
	public static void runInUiThread(Runnable r) {
		handler.post(r);// Message-->handler.sendMessage-->handleMessage()
						// 主线程-->r.run();
	}
}
