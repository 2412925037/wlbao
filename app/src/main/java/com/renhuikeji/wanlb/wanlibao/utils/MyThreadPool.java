package com.renhuikeji.wanlb.wanlibao.utils;

import android.annotation.SuppressLint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint("NewApi")
public class MyThreadPool {

	private static ExecutorService mES = Executors.newCachedThreadPool();

	// 在线程池中执行线程
	public static void submit(Runnable command) {
		// mExecutor.execute(command);
		try {
			mES.execute(command);
		} catch (NullPointerException e) {

		}
	}
}