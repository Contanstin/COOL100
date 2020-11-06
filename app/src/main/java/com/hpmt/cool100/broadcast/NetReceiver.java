package com.hpmt.cool100.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hpmt.cool100.Util.tools.NetworkUtil;


/**
 * Created by xiaoqin on 2020/1/15.
 */

public class NetReceiver extends BroadcastReceiver {
    public static int Network_judgment;

//
//
//        public static boolean Death_Boolean;
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //Death_Boolean = ping();
//            final Context context1 = context;
//            new Thread(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            Death_Boolean = ping();
//                            // 如果当前没有网络接入
//                            if( Death_Boolean == false)
//                              //  Toast.makeText( context1 , "当前无网络接入" , Toast.LENGTH_LONG ).show();
//                            Log.e("网络","当前无网络接入");
//                            // 相关网络接入之后操作 ...
//                        }
//                    }
//            ).start();
//
//        }
//
//        public boolean ping() {
//            try {
//                String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
//                Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ip);// ping网址1次
//                if (p.waitFor() == 0)  return true;
//            }catch (Exception e) {
//            }
//            return false;
//        }


    @Override
    public void onReceive(Context context, Intent intent) {
        int netWorkStates = NetworkUtil.getNetWorkStates(context);

        switch (netWorkStates) {
            case NetworkUtil.TYPE_NONE:
                //断网了
                Network_judgment=-1;
                break;
            case NetworkUtil.TYPE_MOBILE:
                //打开了移动网络
                Network_judgment=0;
                break;
            case NetworkUtil.TYPE_WIFI:
                //打开了WIFI
                Network_judgment=1;
                break;

            default:
                break;
        }
    }

}
