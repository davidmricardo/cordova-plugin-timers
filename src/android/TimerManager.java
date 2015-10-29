package de.schchr.cordova.plugin.timers;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;

public class TimerManager {
	
	private static TimerManager instance = null;
	
	static {

		if(instance == null)
			instance = new TimerManager();

    }
	
	private Hashtable<Integer, Timer> timers;
	
	public TimerManager() {
		timers = new Hashtable<Integer, Timer>();
	};
	
	public Hashtable<Integer, Timer> getTimers(){
		return timers;
	}
	
	public static void checkTimers(){
		
		for(Entry<Integer, Timer> entry : instance.getTimers().entrySet()) {
		    Timer timer = entry.getValue();
		    timer.check();
		}
		
	}
	
	public static int addInterval(int msInterval) {
		
		Interval newInterval = new Interval(msInterval);
		instance.getTimers().put(newInterval.getId(), newInterval);
		
		return newInterval.getId();
		
	}
	
	public static int addTimeout(int msTimeout) {
		
		Timeout newTimeout = new Timeout(msTimeout);
		instance.getTimers().put(newTimeout.getId(), newTimeout);
		
		return newTimeout.getId();
		
	}
	
	public static boolean deleteTimer(int timerId){
		
		Timer selectedTimer = instance.getTimers().get(timerId);
		
		if(selectedTimer == null)
			return false;
		
		selectedTimer.delete();
		instance.getTimers().remove(timerId);
		
		selectedTimer = null;
		
		return true;
		
	}

}
