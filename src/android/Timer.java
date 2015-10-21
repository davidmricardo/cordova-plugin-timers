package de.schchr.cordova.plugin.timers;

public class Timer {
	
	protected static int countTimers = 0;
	
	protected int id;
	
	public Timer() {
		id = countTimers++;
	}
	
	public int getId(){
		return id;
	}
	
	public void delete(){
		
	};

}