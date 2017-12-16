package com.renhuikeji.wanlb.wanlibao.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.renhuikeji.wanlb.wanlibao.R;

/**
 * 网络开启判断
 * @author songdy
 *
 */
public class NetworkManageUtil {
	private static final String TAG = "NetworkManageUtil";

	/**
	 * 判断wifi网络是否连接
	 * @param inContext
	 * @return
	 */
	public static boolean isWiFiActive(Context inContext) {
		if(inContext == null){
			return false;
		}
		WifiManager mWifiManager = (WifiManager) inContext
				.getSystemService(Context.WIFI_SERVICE);
		if (mWifiManager == null || mWifiManager.getConnectionInfo() == null) {
			LogUtil.i(NetworkManageUtil.class, "**** newwork is off");
			return false;
		}
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		return mWifiManager.isWifiEnabled() && ipAddress != 0;
	}

	/**
	 * 判断3G网络是否连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);			
		if (connectivity == null) {
			LogUtil.i(NetworkManageUtil.class, "**** newwork is off");
			return false;
		} else {					
			NetworkInfo info = connectivity.getActiveNetworkInfo();								
			if (info == null) {
				LogUtil.i(NetworkManageUtil.class, "**** newwork is off");
				return false;
			} else {
				NetworkInfo mobNetInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (mobNetInfo != null) {
					return info.isAvailable() && mobNetInfo.isAvailable();								
				}				
			}
		}
		LogUtil.i(NetworkManageUtil.class, "**** newwork is off");
		return false;
	}
	
	/**
	 * 检查网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetworkAvailable(Context context) {
		boolean isNetworkAvailable = false;
		try{
			ConnectivityManager connManager = getConnectivityManager(context);
			if (connManager.getActiveNetworkInfo() != null) {
				isNetworkAvailable = connManager.getActiveNetworkInfo()
						.isAvailable();
			}
		}catch(Exception e){

		}
		if(!isNetworkAvailable){
            ToastUtils.toastForShort(context,context.getString(R.string.bad_net));
        }
		return isNetworkAvailable;
	}

	/**
	 * 获取网络连接对象
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unused")
	public static ConnectivityManager getConnectivityManager(Context context) throws Exception{
		ConnectivityManager connManager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		return connManager;

	}

	
	
}
