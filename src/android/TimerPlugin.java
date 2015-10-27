package de.schchr.cordova.plugin.timers;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class TimerPlugin extends CordovaPlugin {
	

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("greet")) {

            String name = data.getString(0);
            String message = "Hello, " + name;
            callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }
/*
    private static CordovaWebView webView = null;

    protected static final String TAG = "timers";
    //adb logcat -s timers
    //
    
    @Override
    public void initialize (CordovaInterface cordova, CordovaWebView webView) {
        TimerPlugin.webView = super.webView;
        Log.v(TAG, "init");
    }
    
    @Override
    public boolean execute (String action, JSONArray args,
                            CallbackContext command) throws JSONException {

        Log.v(TAG, "exec");
    	
        try{
        	
        	if (action.equals("addTimeout"))
                command.success(addTimeout(args.optInt(0)));
            else if (action.equals("addInterval"))
                command.success(addInterval(args.optInt(0)));
            else if (action.equals("deleteTimer"))
                command.success(deleteTimer(args.optInt(0)));
        	
        } catch (Exception e){
        	System.out.println(e);
        }

        return true;
        
    }
    
    private int addInterval(int msInterval){
    	Log.v(TAG, "addInterval");
    	return TimerManager.addInterval(msInterval);
    }
    
    private int addTimeout(int msTimeout){
    	Log.v(TAG, "addTimeout");
    	return TimerManager.addTimeout(msTimeout);
    }
    
    private String deleteTimer(int timerId){
    	Log.v(TAG, "deleteTimer");
    	boolean result = TimerManager.deleteTimer(timerId);
    	return result ? "true" : "false";
    }

    public void triggerTimer (int timerId) {

        String js = "cordova.plugins.TimerPlugin.triggerTimer(" + timerId + ")";

        webView.loadUrl("javascript:" + js);
        
    }
*/
}