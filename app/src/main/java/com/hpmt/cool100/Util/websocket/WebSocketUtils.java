package com.hpmt.cool100.Util.websocket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hpmt.cool100.config.MacrosConfig;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

public class WebSocketUtils {
    private static final String TAG = "res";
    public static WebSocket web;
    public static String result;
    private static Handler sHandler = new Handler();

    public static void setHandler(Handler handler) {
        sHandler = handler;
    }

    public static void requestWebSocket(String url, String port, final String content, final int requestCode) {
        AsyncHttpClient.getDefaultInstance().websocket(url, port, new AsyncHttpClient.WebSocketConnectCallback() {

            @Override
            public void onCompleted(Exception e, WebSocket webSocket) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }

                //接收到消息的监听
                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {


                        if (s != null && !s.trim().equals("")) {
                            Message msg = new Message();
                            msg.what = requestCode;
                            msg.obj = s;
                            sHandler.sendMessage(msg);
                        }

                    }
                });
                //发送内容到服务端
                Log.e("fasong", content);
                webSocket.send(content);
                //关闭链接的监听
                webSocket.setClosedCallback(new CompletedCallback() {

                    @Override
                    public void onCompleted(Exception e) {
                        if (e != null) {
                            Log.e(TAG, e.getMessage());
                        }
                        Log.e(TAG, "close success");
                    }
                });

                //发送内容到服务端
                // webSocket.send(content);
            }
        });
    }

    //建立websocket
    public static void startWebSocket() {
        AsyncHttpClient.getDefaultInstance().websocket(MacrosConfig.wsUrl, null, new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception e,  WebSocket webSocket) {
                if (e != null) {
                    e.printStackTrace();
                }
                //获取webSocket
                web = webSocket;
                //接收到消息的监听
                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
                        Log.e("a", s);
                        result=s;
                    }
                });


                //关闭链接的监听
                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception e) {
                        if (e != null) {
                            Log.e(TAG, e.getMessage());
                        }

                    }

                });

            }

        });
    }

}
