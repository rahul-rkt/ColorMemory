
package thakur.rahul.colourmemory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import thakur.rahul.colourmemory.controller.CardController;
import thakur.rahul.colourmemory.controller.FragmentController;
import thakur.rahul.colourmemory.model.GameModeModel;
import thakur.rahul.colourmemory.model.ScoreModel;
import thakur.rahul.colourmemory.model.ScorePojo;
import thakur.rahul.colourmemory.model.ThemeModel;
import thakur.rahul.colourmemory.model.TimerModel;
import thakur.rahul.colourmemory.view.HighScoreListFragment.HighScoreListFragmentToActivityCommunicator;
import thakur.rahul.colourmemory.view.NewHighScoreFragment.NewHighScoreFragmentToActivityCommunicator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Activity where the game runs. Handles onClick events for all turns. Shows high score list. Delegates new high score
 * if any.
 *
 * @author rahulthakur
 */
public class GameBoardActivity extends Activity implements NewHighScoreFragmentToActivityCommunicator, HighScoreListFragmentToActivityCommunicator {

	private CardController cardController;
	private boolean isFirstImage;
	private int finalScore;
	private int[] card_backs = { R.id.R0C0_0,
	                            R.id.R0C1_0,
	                            R.id.R0C2_0,
	                            R.id.R0C3_0,
	                            R.id.R1C0_0,
	                            R.id.R1C1_0,
	                            R.id.R1C2_0,
	                            R.id.R1C3_0,
	                            R.id.R2C0_0,
	                            R.id.R2C1_0,
	                            R.id.R2C2_0,
	                            R.id.R2C3_0,
	                            R.id.R3C0_0,
	                            R.id.R3C1_0,
	                            R.id.R3C2_0,
	                            R.id.R3C3_0 };
	private ThemeModel cardTheme;
	private TimerTask timerTask;
	private Timer timer;
	private TimerModel timeLeft;
	private GameModeModel gameMode;
	private boolean timerStarted = false;;
	final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
		gameMode = GameModeModel.getInstance();
		cardTheme = ThemeModel.getInstance();
		isFirstImage = true;
		setCardBack(cardTheme.getTheme());
		if (gameMode.isGameMode(GameModeModel.TIME_TRIAL_GAME_MODE)) {
			timerStarted = false;
			timeLeft = TimerModel.getInstance();
			timeLeft.initializeTimer();
			Button timerButton = (Button) findViewById(R.id.timerButton);
			timerButton.setText(String.valueOf(timeLeft.getTime()));
		} else if (gameMode.isNormalGameMode())
			findViewById(R.id.timerButton).setAlpha(0.25f);
		cardController = new CardController(this, cardTheme.getTheme());
	}

	private void setCardBack(String card_prefix) {

		int resId = getResources().getIdentifier(card_prefix + "back", "drawable", getPackageName());
		for (int temp : card_backs) {
			ImageView imageView = (ImageView) findViewById(temp);
			imageView.setImageResource(resId);
		}
	}

	/**
	 * onClickMethod which shows the high score list as a fragment.
	 */
	public void showScores(View v) {

		if (gameMode.isTimeTrialGameMode())
			stopTimer();
		new FragmentController(this).showHighScoreList(R.id.fragment_container_high_score_list_gameboard, true);
	}

	/**
	 * endGame helper method when ending game
	 */
	public void endGame() {

		if (gameMode.isNormalGameMode())
			cardController.disableAllCards();
		finalScore = cardController.getFinalScore();
		List<ScorePojo> scoresList = ScoreModel.getScoresList();
		if (scoresList.size() >= 10)
			if (finalScore < ScoreModel.getCurrentMinimumScoreInDB()) {
				new FragmentController(this).showHighScoreList(R.id.fragment_container_high_score_list_gameboard, true);
				return;
			}
		new FragmentController(this).showNewHighScoreInput(R.id.fragment_container_new_high_score);
		stopTimer();
		ThemeModel.getInstance().checkForUnlockedThemes(cardController);
	}

	/**
	 * onClickMethod associated with every ImageView in the <i>include_grid_0.xml</i> layout file
	 */
	public void imageClick(View v) {

		if (gameMode.isTimeTrialGameMode())
			if ( !timerStarted) {
				timerStarted = true;
				startTimer();
			}
		if (isFirstImage) {
			cardController.firstSelection((ImageView) v);
			isFirstImage = !isFirstImage;
		} else {
			cardController.secondSelection((ImageView) v);
			isFirstImage = !isFirstImage;
		}
		if (cardController.hasGameEnded()) {
			//See initializeTimerTask for Time Trial's end game condition.
			if ( !gameMode.isTimeTrialGameMode())
				endGame();
		} else if (gameMode.isTimeTrialGameMode() && cardController.allCardsFlipped())
			//Sets up a new set of cards for when user has opened all cards but still has time to play the game
			cardController.setNewBoard((ImageView) v);
	}

	@Override
	public void getPlayerName(String playerName) {

		ScoreModel.addScore(playerName, finalScore);
		new FragmentController(this).showHighScoreList(R.id.fragment_container_high_score_list_gameboard, true);
	}

	@Override
	public void hasDetached() {

		if (cardController.hasGameEnded()) {
			this.finish();
			stopTimer();
		} else if (gameMode.isTimeTrialGameMode())
			startTimer();
	}

	@Override
	public void onPause() {

		super.onPause();
		stopTimer();
	}

	@Override
	public void onStop() {

		super.onStop();
		stopTimer();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		stopTimer();
	}

	@Override
	public void onResume() {

		super.onResume();
		if (timerStarted)
			startTimer();
	}

	/*
	 * ends the timer
	 */
	public void stopTimer() {

		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/*
	 * starts the in-game timer for Time Trial Game Mode
	 */
	public void startTimer() {

		//set a new Timer
		timer = new Timer();
		//initialize the TimerTask's job
		initializeTimerTask();
		//schedule the timer, after the first 0ms the TimerTask will run every 1000ms
		timer.schedule(timerTask, 10, 1000); //
	}

	/*
	 * Initializes the Timer Task instance
	 */
	public void initializeTimerTask() {

		timerTask = new TimerTask() {

			@Override
			public void run() {

				handler.post(new Runnable() {

					@Override
					public void run() {

						if ( !timeLeft.isFinished()) {
							timeLeft.decreaseTime();
							Button timerButton = (Button) findViewById(R.id.timerButton);
							timerButton.setText(String.valueOf(timeLeft.getTime()));
							if (timeLeft.isFinished())
								endGame();
						}
					}
				});
			}
		};
	}
}
