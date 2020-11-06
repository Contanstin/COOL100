package com.hpmt.cool100.Util.tools;

public class MoveSpecificChar {

	/**
	 * @func 去掉空格和换行符
	 * @param str
	 * @return
	 */
//	public static String replaceBlankAndBreakLine(String str) {
//		String dest = "";
//		if (str != null) {
//			Pattern p = Pattern.compile("\\s|\r|\n");
//			Matcher m = p.matcher(str);
//			dest = m.replaceAll("");
//		}
//		return dest;
//	}

	/**
	 * @func 只得到字符串中的数字,但是注意不能改变传递进来的值
	 * @param str
	 * @return
	 */
	public static String getOnlyNumbers(String str) {
		String tmpStr = "";
		if (str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				String tmp = "" + str.charAt(i);
				if ((tmp).matches("[0-9]")) {
					tmpStr += tmp;
				}
			}
		}
		return tmpStr;
	}
}
