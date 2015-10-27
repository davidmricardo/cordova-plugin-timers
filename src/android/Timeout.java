package de.schchr.cordova.plugin.timers;
import java.util.Timer;
import java.util.TimerTask;

public class Timeout extends de.schchr.cordova.plugin.timers.Timer {
	
	private int msTimeout;
	
	private Timer timer;
	
	public Timeout(int msTimeout){
		
		this.msTimeout = msTimeout;
		
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    //TimerPlugin.triggerTimer(id);
			  }
		}, msTimeout);
		
	}
	
	public int getTimeout(){
		return msTimeout;
	}
	
	@Override
	public void delete(){
		
	}
	

}
