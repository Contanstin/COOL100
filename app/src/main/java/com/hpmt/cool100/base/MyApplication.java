package com.hpmt.cool100.base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.multidex.MultiDex;

import com.hpmt.cool100.Activity.MainActivity;
import com.hpmt.cool100.Util.Jpush.TagAliasOperatorHelper;
import com.hpmt.cool100.dbhelp.DaoMaster;
import com.hpmt.cool100.dbhelp.DaoSession;
import com.pgyersdk.crash.PgyCrashManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

//import android.support.multidex.MultiDex;

public class MyApplication extends Application {

	private MutableLiveData<String> mBroadcastData;
	private Map<String, Object> mCacheMap;
	private List<Activity> activityList = new LinkedList<Activity>();
	private static DaoMaster.DevOpenHelper helper;
	private SQLiteDatabase db;
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	public boolean isConnect;
	public long TimeOutTime = 0;
	public ArrayList<String> mcuCodeArr = new ArrayList<String>();
	//public int selectLimit = 0;// 用户的使用权限

	private static MyApplication instance;

	public static MyApplication getInstance() {
		return instance;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(base);

	}

	//@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initApplication();

		instance = this;


		mCacheMap = new HashMap<>();

		mBroadcastData = new MutableLiveData<>();
		IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		if (Build.VERSION.SDK_INT >= 28) {
			filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
		}
		registerReceiver(mReceiver, filter);
		//BluetoothContext.set(this);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	/**
	 * 取得DaoMaster
	 * 

	 * @return
	 */
	public static DaoMaster getBleDaoMaster() {
		return daoMaster;
	}


	/**
	 * 取得DaoSession
	 *
	 * @return
	 */
	public static DaoSession getBleDaoSession() {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getBleDaoMaster();
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}

	// 添加activity
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 退出activity
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}


	private void initApplication() {
		// TODO Auto-generated method stub
		helper = new DaoMaster.DevOpenHelper(this, "Param_info-db", null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		PgyCrashManager.register(this);

	}
	//创建guangbo
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action == null) {
				return;
			}

			switch (action) {
				case WifiManager.NETWORK_STATE_CHANGED_ACTION:
				case LocationManager.PROVIDERS_CHANGED_ACTION:
					mBroadcastData.setValue(action);
					break;
			}
		}
	};

	public DaoMaster.DevOpenHelper getHelper() {
		return helper;
	}

	public void setHelper(DaoMaster.DevOpenHelper helper) {
		this.helper = helper;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	public DaoMaster getDaoMaster() {
		return daoMaster;
	}

	public void setDaoMaster(DaoMaster daoMaster) {
		this.daoMaster = daoMaster;
	}

	public DaoSession getDaoSession() {
		return daoSession;
	}

	public void setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
	}

	/*public BluetoothLeService getBleServer() {
		// TODO Auto-generated method stub
		return this.mBluetoothLeService;
	}*/
	
	@Override

	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		//exit();
	}

	public void destory() {
	}
	public void observeBroadcast(LifecycleOwner owner, androidx.lifecycle.Observer<String> observer) {
		mBroadcastData.observe(owner, observer);
	}

	public void observeBroadcastForever(androidx.lifecycle.Observer<String> observer) {
		mBroadcastData.observeForever(observer);
	}

	public void removeBroadcastObserver(Observer<String> observer) {
		mBroadcastData.removeObserver(observer);
	}

	public void putCache(String key, Object value) {
		mCacheMap.put(key, value);
	}

	public Object takeCache(String key) {
		return mCacheMap.remove(key);
	}


}
