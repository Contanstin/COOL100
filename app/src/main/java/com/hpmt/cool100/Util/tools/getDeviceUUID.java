package com.hpmt.cool100.Util.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class getDeviceUUID {
	protected static final String PREFS_FILE = "gank_device_id.xml";
	protected static final String PREFS_DEVICE_ID = "gank_device_id";
	protected static String uuid;

	static public String getUDID(Context context) {
		if (uuid == null) {
			synchronized (context) {
				if (uuid == null) {
					final SharedPreferences prefs = context
							.getSharedPreferences(PREFS_FILE, 0);
					final String id = prefs.getString(PREFS_DEVICE_ID, null);

					if (id != null) {
						// Use the ids previously computed and stored in the
						// prefs file
						uuid = id;
					} else {

						final String androidId = Secure
								.getString(context.getContentResolver(),
										Secure.ANDROID_ID);

						// Use the Android ID unless it's broken, in which case
						// fallback on deviceId,
						// unless it's not available, then fallback on a random
						// number which we store
						// to a prefs file
						try {
							if (!"9774d56d682e549c".equals(androidId)) {
								uuid = UUID.nameUUIDFromBytes(
										androidId.getBytes("utf8")).toString();
							} else {
								final String deviceId = ((TelephonyManager) context
										.getSystemService(Context.TELEPHONY_SERVICE))
										.getDeviceId();
								uuid = deviceId != null ? UUID
										.nameUUIDFromBytes(
												deviceId.getBytes("utf8"))
										.toString() : UUID.randomUUID()
										.toString();
							}
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}

						// Write the value out to the prefs file
						prefs.edit().putString(PREFS_DEVICE_ID, uuid).commit();
					}
				}
			}
		}
		return uuid;
	}

}
