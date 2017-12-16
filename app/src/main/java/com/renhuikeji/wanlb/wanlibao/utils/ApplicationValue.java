package com.renhuikeji.wanlb.wanlibao.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class ApplicationValue {

    public boolean visaHandleFlag = false;//签证是否带返回首页按钮

    public int nAppSelectIndex;
    public boolean flag = false;
    public static boolean cancel_order_flag = false;
    public String weiboValidateUrl;

    public String requestJsonStr;
    public String receiveJsonStr;

    /**
     * 定位状态
     */
    public int locateState = 0;
    //	public List<TravelJourney> travelJourneys;
//	public Map<String, List<PhoneAlbumTopReceive>> mapTopicsList = new HashMap<String, List<PhoneAlbumTopReceive>>();
//    public List<TravelLine> travelLines;
//    public TravelLine travelLine;
//
//    public List<OrderSonDTO> orderSonList;
    public File cacheRoot;
    private static String TAG = "ApplicationValue";
    public List<Activity> activityList = new LinkedList<Activity>();
    private static ApplicationValue instance;

    public ApplicationValue() {
        super();
        Thread.setDefaultUncaughtExceptionHandler(onBlooey);
    }

    private Thread.UncaughtExceptionHandler onBlooey = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            LogUtil.e(TAG, "Uncaught exception   " + ex);
        }
    };

    public String USERNAME = "Rember_UserName";
    public String PASSWORD = "Rember_Pawwrod";

    public void invokeGc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    finalize();
                } catch (Throwable e) {
                }
                // 提醒系统及时回收
                System.gc();
            }
        }).start();
    }

    // 单例模式中获取唯一的ApplicationUtil实例
    public static ApplicationValue getInstance() {
        if (null == instance) {
            instance = new ApplicationValue();
        }
        return instance;

    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }

    }

    /**
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }

    }

    /**
     * 判断是否包含某个视图
     *
     * @param activity
     * @return
     */
    public boolean containsActivity(Activity activity) {
        if (activityList.contains(activity)) {
            return true;
        }
        return false;
    }

    // 遍历所有Activity并finish

    public void exit() {
        boolean flag = false;
        // ApplicationValue.mapTopicsList.clear();
        for (Activity activity : activityList) {
            if (!flag) {
                flag = true;
                // 加载check的状态
                SharedPreferences settingc = activity.getSharedPreferences("checkst_stauts",
                        Context.MODE_PRIVATE);
                boolean userBoolean = settingc.getBoolean("username_ischeck", false);
                if (!userBoolean) {
                    clearUserInfo(activity);
                }
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

        System.exit(0);
    }

    /**
     * 清除用户信息
     */
    private void clearUserInfo(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(
                IKeySourceUtil.LOGIN_STATUS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();

        SharedPreferences userName = activity.getSharedPreferences(USERNAME,
                Context.MODE_PRIVATE);
        String username = userName.getString("username", "");
        SharedPreferences.Editor editorUser = userName.edit();
        editorUser.putString("username", "");
        editorUser.apply();

        SharedPreferences passwork = activity.getSharedPreferences(PASSWORD,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorPass = passwork.edit();
        editorPass.putString(username, "");
        editorPass.apply();
    }

    /**
     * 回到主界面清除打开的activity
     */
    public void removeActivitys() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                String className = LogUtil.getClassName(activity);
                if (!"HomeActivityNew".equals(className)) {
                    activity.finish();
                }
            }
        }
    }

    public void fishActivity(Class<?> cls) {
        int size = activityList.size();
        for (int i = size - 1; i > 0; i--) {
            Activity activity = activityList.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
            if (cls.getName().equals(activity.getLocalClassName())) {
                break;
            }
        }
    }

}
