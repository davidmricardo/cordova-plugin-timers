package de.schchr.cordova.plugin.timers;

import java.util.Timer;
import java.util.TimerTask;

public class Interval extends de.schchr.cordova.plugin.timers.Timer {
	
	private int msInterval;
	
	private Timer timer;
	
	public Interval(int msInterval){
		
		this.msInterval = msInterval;
		
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  TimerPlugin.triggerTimer(id);
			  }
		}, msInterval, msInterval);
		
	}
	
	public int getIntervall(){
		return msInterval;
	}
	
	@Override
	public void delete(){
		
	}

}
