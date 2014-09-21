package infiniteviewpager;

import android.util.Log;

public class Logger {
	
	private static final String TAG = "Test";
	
	public static final boolean DEBUG = false;

	public static void v(String tag, String msg) {
		if (DEBUG) {
			Log.v(TAG, tag + " " + msg);
		}
	}

	public static void d(String tag, String msg) {
		if (DEBUG) {
			Log.d(TAG, tag + " " + msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			Log.i(TAG, tag + " " + msg);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUG) {
			Log.v(TAG, tag + " " + msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (DEBUG) {
			Log.e(TAG, tag + " " + msg);
		}
	}
}
