package de.schchr.cordova.plugin.timers;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;

public class TimerPlugin extends CordovaPlugin {
	
	/*public static void main(String[] args){
		
		System.out.println("Start");
		
		System.out.println("init timeout: " + TimerManager.addTimeout(1000));
		System.out.println("init timeout: " + TimerManager.addInterval(1000));
		
		System.out.println("End");
		
	}*/
	
	// Reference to the web view for static access
    private static CordovaWebView webView = null;

    // Indicates if the device is ready (to receive events)
    private static Boolean deviceready = false;

    // To inform the user about the state of the app in callbacks
    protected static Boolean isInBackground = true;

    // Queues all events before deviceready
    private static ArrayList<String> eventQueue = new ArrayList<String>();
    
    @Override
    public boolean execute (final String action, final JSONArray args,
                            final CallbackContext command) throws JSONException {

        cordova.getThreadPool().execute(new Runnable() {
        	
            public void run() {
            	
                if (action.equals("addTimeout"))
                    command.success(addTimeout(args.optInt(0)));
                else if (action.equals("addInterval"))
                	command.success(addInterval(args.optInt(0)));
                else if (action.equals("deleteTimer"))
                	command.success(deleteTimer(args.optInt(0)));
                else if (action.equals("deviceready"))
                    deviceready();
                
            }
            
        });

        return true;
        
    }
    
    private int addInterval(int msInterval){
    	return TimerManager.addInterval(msInterval);
    }
    
    private int addTimeout(int msTimeout){
    	return TimerManager.addTimeout(msTimeout);
    }
    
    private String deleteTimer(int timerId){
    	boolean result = TimerManager.deleteTimer(timerId);
    	return result ? "true" : "false";
    }
    
    /**
     * Most of the following code to handle javascript callbacks is taken from
     * https://github.com/katzer/cordova-plugin-local-notifications/blob/master/src/android/LocalNotification.java
     */

    /**
     * Called when the system is about to start resuming a previous activity.
     *
     * @param multitasking
     *      Flag indicating if multitasking is turned on for app
     */
    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        isInBackground = true;
    }

    /**
     * Called when the activity will start interacting with the user.
     *
     * @param multitasking
     *      Flag indicating if multitasking is turned on for app
     */
    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        isInBackground = false;
        deviceready();
    }

    /**
     * The final call you receive before your activity is destroyed.
     */
    @Override
    public void onDestroy() {
        deviceready = false;
        isInBackground = true;
    }
    
    /**
     * Call all pending callbacks after the deviceready event has been fired.
     */
    private static synchronized void deviceready () {
        isInBackground = false;
        deviceready = true;

        for (String js : eventQueue) {
            sendJavascript(js);
        }

        eventQueue.clear();
    }

    /**
     * Fire given event on JS side. Does inform all event listeners.
     *
     * @param event
     *      The event name
     * @param notification
     *      Optional local notification to pass the id and properties.
     */
    static void triggerTimer (int timerId) {
    	
        String state = getApplicationState();
        String params = "\"" + state + "\"";

        String js = "cordova.plugins.timers.triggerTimer(" +
                "\"" + timerId + "\"," + params + ")";

        sendJavascript(js);
        
    }
 
    /**
     * Use this instead of deprecated sendJavascript
     *
     * @param js
     *       JS code snippet as string
     */
    private static synchronized void sendJavascript(final String js) {

        if (!deviceready) {
            eventQueue.add(js);
            return;
        }
        
        Runnable jsLoader = new Runnable() {
            public void run() {
                webView.loadUrl("javascript:" + js);
            }
        };
        
		try {
			
		    Method post = webView.getClass().getMethod("post",Runnable.class);
		        post.invoke(webView,jsLoader);
	    } catch(Exception e) {
	        ((Activity)(webView.getContext())).runOnUiThread(jsLoader);
	    }
		
	}
	 
	static String getApplicationState () {
	    return isInBackground ? "background" : "foreground";
	}
	
}
