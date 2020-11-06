package com.hpmt.cool100.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.hpmt.cool100.Util.Jpush.TagAliasOperatorHelper;


import java.util.List;

import cn.jpush.android.api.JPushInterface;

//import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends AppCompatActivity {

    public boolean isActive;
    protected MyApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        application = (MyApplication) getApplication();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (!isAppOnForeground()) {
            // app 进入后台

            // 全局变量isActive = false 记录当前已经进入后台
            isActive = true;
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
       // MobclickAgent.onResume(this);

        if (isActive) {
            // app 从后台唤醒，进入前台
            isActive = false;
           // OperationTimeOut();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //MobclickAgent.onPause(this);
    }
    protected void exit() {
        // TODO Auto-generated method stub
        JPushInterface.deleteAlias(BaseActivity.this, TagAliasOperatorHelper.sequence);
        //MobclickAgent.onPause(this);
    }
    /**
     * 禁止返回按钮
     * @param keyCode
     * @param event
     * @return
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//           // return true;
//        }
//        return false;
//
//    }

    //}
}
