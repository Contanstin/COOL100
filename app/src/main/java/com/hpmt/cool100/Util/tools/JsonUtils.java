package com.hpmt.cool100.Util.tools;

import android.text.InputType;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {
	public static String getJSONString(JSONObject o, String key) {
		try {
			return o.getString(key);
		} catch (Exception e) {		
			LogUtils.e("JsonUtils", e.toString());
		}
		return "";
	}
	
	public static JSONObject getJSONObject(JSONObject jo, String key){			
		try {
			return jo.getJSONObject(key);
		} catch (Exception e) {		
			LogUtils.e("JsonUtils", e.toString());
		}
		return null;
	}


	public static JSONArray getJsonArray(JSONObject obj, String key) {
		try {
			return obj.getJSONArray(key);
		} catch (Exception e) {
			LogUtils.e("JsonUtils", e.toString());
		}
		return null;
	}

	
	public static boolean getJSONBoolean(JSONObject jo, String key, boolean defalut){			
		try {
			return jo.getBoolean(key);
		} catch (Exception e) {		
			LogUtils.e("JsonUtils", e.toString());
		}
		return defalut;
	}

	public static int getJSONInt(JSONObject o, String key) {
		try {
			return o.getInt(key);
		} catch (Exception e) {
			LogUtils.e("JsonUtils", e.toString());
		}
		return -1;
	}
	
	public static boolean isJson(String text) {
		try {
			new JSONObject(text);
			return true;
		} catch (Exception e) {
			LogUtils.e("JsonUtils", e.toString());
		}
		return false;
	}
	
	public static JSONObject getJSONObject(String text) {
		try {
			return new JSONObject(text);
		} catch (Exception e) {
			LogUtils.e("JsonUtils", e.toString());
		}
		return null;
	}

	public static List<String> getList(JSONArray arr) {
		List<String> list=new ArrayList<>();
		for (int i=0;i<arr.length();i++)
		{
			try {
				String a=arr.toString(i);
				list.add(a);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
//设置是否可写
	public static void EditTextEnable(boolean enable, EditText editText){
		editText.setFocusable(enable);
		editText.setFocusableInTouchMode(enable);
		editText.setLongClickable(enable);
		//editText.setSelection(arr.length());
		editText.setInputType(enable? InputType.TYPE_CLASS_NUMBER:InputType.TYPE_NULL);
	}

}
