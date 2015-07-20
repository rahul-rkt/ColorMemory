package thakur.rahul.colourmemory.model;

public class GameModeModel {
	public static final int NORMAL_GAME_MODE = 0;
	public static final int TIME_TRIAL_GAME_MODE = 1;
	
	private static GameModeModel singleton = new GameModeModel();
	private int gameMode;
	
	private GameModeModel() {
		gameMode = GameModeModel.NORMAL_GAME_MODE;
	}
	
	public static GameModeModel getInstance() {
		if( singleton == null ) {
			singleton = new GameModeModel();
		}
		return singleton;
	}
	
	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}
	
	/*
	 * Used to check for a specific game mode
	 */
	public boolean isGameMode(int gameMode) {
		if (this.gameMode == gameMode ) return true;
		return false;
	}
	
	/*
	 * Check if Time Trial Game Mode
	 */
	public boolean isTimeTrialGameMode() {
		return this.gameMode == GameModeModel.TIME_TRIAL_GAME_MODE;
	}
	
	/*
	 * Check if Normal Game Mode
	 */
	public boolean isNormalGameMode() {
		return this.gameMode == GameModeModel.NORMAL_GAME_MODE;
	}
}
