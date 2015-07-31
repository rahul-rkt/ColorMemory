
package thakur.rahul.colourmemory.controller;

import android.app.Activity;
import android.widget.ImageView;

/**
 * Evaluates each turn. Handles Flip Animation.
 *
 * @author rahulthakur
 */
public class GamePlayController {

	private Activity activity;
	private ImageView[][] turnKeeper;
	private int moveType;
	private Integer scoreToUpdate;
	public static final int FIRST_MOVE = 1;
	public static final int SECOND_MOVE_MATCHED = 2;
	public static final int SECOND_MOVE_MISMATCHED = 3;
	private static final String ANIMATION_THREAD_TAG = "AnimationThread";

	public GamePlayController(Activity activity, ImageView[][] turnKeeper, Integer currentScore, int moveType) {

		this.activity = activity;
		this.turnKeeper = turnKeeper;
		this.moveType = moveType;
		if (currentScore != null)
			scoreToUpdate = currentScore;
	}

	/**
	 * Takes proper action in response to each turn.
	 */
	public void evaluate() {

		switch (moveType) {
			case FIRST_MOVE:
				turnKeeper[0][0].setClickable(false);
				new Thread(new AnimationController(activity, turnKeeper[0][0], turnKeeper[0][1], false, null), ANIMATION_THREAD_TAG).start();
				break;
			case SECOND_MOVE_MATCHED:
				turnKeeper[1][0].setClickable(false);
				new Thread(new AnimationController(activity, turnKeeper[1][0], turnKeeper[1][1], false, scoreToUpdate), ANIMATION_THREAD_TAG).start();
				break;
			case SECOND_MOVE_MISMATCHED:
				turnKeeper[1][0].setClickable(false);
				new Thread(new AnimationController(activity, turnKeeper[1][0], turnKeeper[1][1], true, null), ANIMATION_THREAD_TAG).start();
				new Thread(new AnimationController(activity, turnKeeper[0][1], turnKeeper[0][0], false, null), ANIMATION_THREAD_TAG).start();
				new Thread(new AnimationController(activity, turnKeeper[1][1], turnKeeper[1][0], false, scoreToUpdate), ANIMATION_THREAD_TAG).start();
				turnKeeper[0][0].setClickable(true);
				turnKeeper[1][0].setClickable(true);
				break;
		}
	}
}
