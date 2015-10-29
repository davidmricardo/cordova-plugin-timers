package de.schchr.cordova.plugin.timers;

import java.util.Timer;
import java.util.TimerTask;

public class Interval extends de.schchr.cordova.plugin.timers.Timer {
	
	private int msInterval;
	private int currentTime = 0;
	
	private Timer timer;
	
	public Interval(int msInterval){
		
		this.msInterval = msInterval;
		
		timer = new Timer();
		currentTime = msInterval / 1000;
		
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
	public void check(){
		
		currentTime--;
		
		if(currentTime <= 0){
			TimerPlugin.triggerTimer(id);
			currentTime = msInterval / 1000;
		}
			
	}
	
	@Override
	public void delete(){
		
	}

}
