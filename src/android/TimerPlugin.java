package de.schchr.cordova.plugin.timers;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.util.Log;

public class TimerPlugin extends CordovaPlugin {

    private static CordovaWebView webView = null;

    protected static final String TAG = "timers";
    
    @Override
    public void initialize (CordovaInterface cordova, CordovaWebView webView) {
        TimerPlugin.webView = super.webView;
        Log.v(TAG, "init");
    }
    
    @Override
    public boolean execute (final String action, final JSONArray args,
                            final CallbackContext command) throws JSONException {

        Log.v(TAG, "exec");

        triggerTimer(1);

        /*if (action.equals("addTimeout"))
            command.success(addTimeout(args.optInt(0)));
        else if (action.equals("addInterval"))
            command.success(addInterval(args.optInt(0)));
        else if (action.equals("deleteTimer"))
            command.success(deleteTimer(args.optInt(0)));*/

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

}