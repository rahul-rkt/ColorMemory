package thakur.rahul.colourmemory.model;

public class TimerModel {
	
	private static TimerModel singleton = new TimerModel();
	
	public static final int TIME_ADDED = 2;
	public static final int STARTING_TIME = 20;
	
	private int time_left = 0;
	private TimerModel() {
		this.time_left = 0;
	}
	
	/**
	 * Used to get the only possible instance of TimerModel
	 */
	public static TimerModel getInstance() {
		if(singleton == null) {
			singleton = new TimerModel();
		}
		return singleton;
	}
	
	/**
	 * Sets the timer to STARTING_TIME
	 */
	public void initializeTimer() {
		time_left = STARTING_TIME;
	}
	
	public int getTime() {
		return time_left;
	}
	
	public void addTime(int delta) {
		time_left += delta;
	}
	
	public void decreaseTime(){
		time_left --;
	}
	
	public void decreaseTime(int delta) {
		time_left -= delta;
	}
	
	public boolean isFinished(){
		return time_left <= 0 ? true : false;
	}
	
}
