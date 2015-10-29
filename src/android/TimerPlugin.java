package de.schchr.cordova.plugin.timers;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class TimerPlugin extends CordovaPlugin {

	protected static final String TAG = "timers";

	protected static CordovaInterface cordovaInstance = null;
	private static CordovaWebView webView = null;
	
	AlarmManager alarmManager;
    PendingIntent pendingIntent;
    BroadcastReceiver mReceiver;

	// adb logcat -s timers

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		TimerPlugin.webView = super.webView;
		TimerPlugin.cordovaInstance = super.cordova;
		Log.v(TAG, "init");
		RegisterAlarmBroadcast();
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 , pendingIntent); 
	}

	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

		Log.v(TAG, "execute(" + action + ")");

		if (action.equals("addTimeout"))
			callbackContext.success(addTimeout(data.optInt(0)));
		else if (action.equals("addInterval"))
			callbackContext.success(addInterval(data.optInt(0)));
		else if (action.equals("deleteTimer"))
			callbackContext.success(deleteTimer(data.optInt(0)));
		else 
			return false;

		return true;

	}
	
 	private void RegisterAlarmBroadcast() {
 		
		mReceiver = new BroadcastReceiver() {
			    
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.v(TAG, "check timers");
				TimerManager.checkTimers();
		    }
	
		};
		
		Activity a = cordovaInstance.getActivity();
		a.registerReceiver(mReceiver, new IntentFilter("CORDOVA_PLUGIN_TIMER"));
		
		pendingIntent = PendingIntent.getBroadcast(a, 0, new Intent("CORDOVA_PLUGIN_TIMER"), 0);
	    alarmManager = (AlarmManager)(a.getSystemService(Context.ALARM_SERVICE));
		    
	}

	private int addInterval(int msInterval) {
		Log.v(TAG, "addInterval(" + msInterval + ")");
		return TimerManager.addInterval(msInterval);
	}

	private int addTimeout(int msTimeout) {
		Log.v(TAG, "addTimeout(" + msTimeout + ")");
		return TimerManager.addTimeout(msTimeout);
	}

	private String deleteTimer(int timerId) {

		Log.v(TAG, "deleteTimer(" + timerId + ")");

		boolean result = TimerManager.deleteTimer(timerId);
		return result ? "true" : "false";

	}

	public static void triggerTimer(int timerId) {

		final int finalTimerId = timerId;

		Activity a = cordovaInstance.getActivity();

		a.runOnUiThread(new Runnable() {

			public void run() {
				String js = "cordova.plugins.TimerPlugin.triggerTimer(" + finalTimerId + ")";
				webView.loadUrl("javascript:" + js);
			}

		});

	}

}