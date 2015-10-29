package de.schchr.cordova.plugin.timers;

import java.util.Timer;
import java.util.TimerTask;

public class Timeout extends de.schchr.cordova.plugin.timers.Timer {
	
	private int msTimeout;
	private int currentTime = 0;
	
	private Timer timer;
	
	public Timeout(int msTimeout){
		
		this.msTimeout = msTimeout;
		currentTime = msTimeout / 1000;
		
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    TimerPlugin.triggerTimer(id);
			  }
		}, msTimeout);
		
	}
	
	public int getTimeout(){
		return msTimeout;
	}
	
	@Override
	public void check(){
		
		currentTime--;
		
		if(currentTime <= 0)
			TimerManager.deleteTimer(id);
			
	}
	
	@Override
	public void delete(){
		
	}
	

}
