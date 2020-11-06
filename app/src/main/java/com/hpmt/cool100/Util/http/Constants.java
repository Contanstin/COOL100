package com.hpmt.cool100.Util.http;

public class Constants {


	public static final class Macros {
		// 放置宏定义块

		public static final int MSG_SUCCESS = 0;
		public static final int MSG_FALLURE = 1;
		public static final int LOGIN_SUCCESSED = 0;
		public static final int LOGIN_FALLURE = 1;
		public static final int SEND_SUCCESSED = 2;
		public static final int VISICUSTOM_STAFF_CODE = 0x717;// 拜访客户请求码
		public static final int VISICUSTOM_CUSTOM_COOE = 0x716;
		public static final int SCHEDULE_COOE = 0x715;

		public static final int LOGIN_SHOW_CLEAR_USERNAME_LABLE = 11;
		public static final int LOGIN_SHOW_CLEAR_PASSWORD_LABLE = 12;
		public static final int LOGIN_SHOW_CLEAR_TESTNUMBER_LABLE = 13;
		public static final int LOGIN_HIDE_CLEAR_USERNAME_LABLE = 110;
		public static final int LOGIN_HIDE_CLEAR_PASSWORD_LABLE = 120;
		public static final int LOGIN_HIDE_CLEAR_TESTNUMBER_LABLE = 130;

		public static final int VERIFY_NUM_TEST_SUCCESSED = 200;// 不需要验证码
		public static final int VERIFY_NUM_TEST_FAILED = 210;// 需要验证码

		// 释放刷新
		public final static int RELEASE_To_REFRESH = 0;
		// 下拉刷新
		public final static int PULL_To_REFRESH = 1;
		// 正在刷新
		public final static int REFRESHING = 2;
		// 刷新完成
		public final static int DONE = 3;
		// 正在加载
		public final static int LOADING = 4;

		// 实际角度与界面偏移比例
		public final static int RATIO = 3;

		// 用户掉线的时候返回的消息
		public static final String LONGININDENTITY_FAILLURE_MESSAGE = "InvalidLoginIdentity";
		public static final String LOGIN_FALLURE_MSG = "对不起，您的网络状态不好，请检查后再登录";
		public static final String LOGIN_FALLURE_SERVER = "对不起，您的账号没有权限使用本应用，如有疑问，请与管理员联系，谢谢！";
		public static final String ISNETWORKCONNET = "您的网络未连接";
		public static final String KEY = "123qwe#$";
		public static final String IP = "0.0.0.0";
		public static final String USERNAME = "admin";
		public static final String PORT = "8888";
		public static final String KEY_SUCCEE = "succee";
		public static final String KEY_RESULT = "result";
		public static final int KEEP_UPDATE = 1;
		public static final int QUERY_DATA = 2;



	}
}
