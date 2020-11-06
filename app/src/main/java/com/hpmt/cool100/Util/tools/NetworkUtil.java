package com.hpmt.cool100.Util.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by xiaoqin on 2020/3/3.
 */

public class NetworkUtil {

    public static final int TYPE_NONE = -1;
    public static final int TYPE_NONEWiFi = -2;
    public static final int TYPE_MOBILE = 0;
    public static final int TYPE_WIFI = 1;

    private NetworkUtil() {
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return one of TYPE_NONE, TYPE_MOBILE, TYPE_WIFI
     * @permission android.permission.ACCESS_NETWORK_STATE
     */
    public static final int getNetWorkStates(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mWiFiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return TYPE_NONE;//没网
        }

//        if (mWiFiNetworkInfo != null) {
//            return TYPE_NONEWiFi;//wifi不可用
//        }


        int type = activeNetworkInfo.getType();
        switch (type) {
            case ConnectivityManager.TYPE_MOBILE:
                return TYPE_MOBILE;//移动数据
            case ConnectivityManager.TYPE_WIFI:
                return TYPE_WIFI;//WIFI
            default:
                break;
        }
        return TYPE_NONE;
    }


}
