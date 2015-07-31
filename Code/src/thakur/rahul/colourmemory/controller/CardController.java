
package thakur.rahul.colourmemory.controller;

import java.util.Random;

import thakur.rahul.colourmemory.model.GameModeModel;
import thakur.rahul.colourmemory.model.TimerModel;
import thakur.rahul.colourmemory.view.CardView;
import android.app.Activity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.ImageView;

/**
 * Creates new game. Delegates each turn. Maintains game state.
 *
 * @author rahulthakur
 */
public class CardController {

	private Activity activity;
	private ImageView[][][] cardViewArray;
	private SparseArray<ImageView> cardViewMap;
	private SparseIntArray cardSwapMap;
	private ImageView[][] turnKeeper;
	private int currentScore = 0;
	private int matchCounter = 0;
	private int lastMatchCounter = 0; //used to check if all the cards are flipped.
	private int comboTracker = 0;
	private String card_prefix;
	private TimerModel timeLeft;
	private GameModeModel gameMode;
	private final int MAX_COMBO = 5;

	public CardController(Activity activity, String card_prefix) {

		this.activity = activity;
		this.card_prefix = card_prefix;
		this.gameMode = GameModeModel.getInstance();
		this.timeLeft = TimerModel.getInstance();
		CardView cardView = new CardView(activity);
		cardViewArray = cardView.getCardViewArray();
		cardViewMap = cardView.getCardViewMap();
		cardSwapMap = cardView.getCardSwapMap();
		turnKeeper = new ImageView[2][2];
		new GameBoardSetupHandler().makeNewGame();
	}

	/**
	 * Makes all cards unclickable. Used when game ends and user is typing name for the highscore table
	 */
	public void disableAllCards() {

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				for (int k = 0; k < 2; k++)
					cardViewArray[i][j][k].setClickable(false);
	}

	/**
	 * Gives user a new set of cards to open. Designed to be used for Time Trial Game Mode Input: ImageView v -- the
	 * last card to be opened. Needed for flipAllCards method
	 */
	public void setNewBoard(ImageView v) {

		turnKeeper = new ImageView[2][2];
		flipAllCards(v);
		new Thread(new AnimationController(activity, null, null, false, null).newGame(this).setPreAnimatinDelay(1000), "AnimationThread").start();
	}

	/**
	 * Flips all cards face down Input: ImageView v -- the last card to be opened. We need to first finish the card
	 * flipping up animation before flipping this card down.
	 */
	public void flipAllCards(ImageView v) {

		ImageView[][] tk = new ImageView[2][2];
		tk[0][1] = v;
		tk[0][0] = cardViewMap.get(cardSwapMap.get(v.getId()));
		new Thread(new AnimationController(activity, tk[0][0], tk[0][1], false, null).setPreAnimatinDelay(700), "AnimationThread").start();
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				tk[0][1] = cardViewArray[i][j][0];
				tk[0][0] = cardViewMap.get(cardSwapMap.get(cardViewArray[i][j][0].getId()));
				tk[0][1].setClickable(true);
				if ((tk[0][1].getId() == v.getId() || tk[0][0].getId() == v.getId()))
					continue;
				else
					new Thread(new AnimationController(activity, tk[0][0], tk[0][1], false, null), "AnimationThread").start();
			}
		lastMatchCounter = matchCounter;
	}

	/**
	 * Sets up new game. This is called by the AnimationController when all flipping is done. Otherwise, users will have
	 * a short time frame where he can see the new card set before they're flipped down.
	 */
	public void makeNewGame() {

		new GameBoardSetupHandler().makeNewGame();
	}

	/**
	 * Delegates first selection of each turn.
	 */
	public void firstSelection(ImageView cardFaceDown) {

		ImageView cardFaceUp = cardViewMap.get(cardSwapMap.get(cardFaceDown.getId()));
		turnKeeper[0][0] = cardFaceDown;
		turnKeeper[0][1] = cardFaceUp;
		new GamePlayController(activity, turnKeeper, null, GamePlayController.FIRST_MOVE).evaluate();
	}

	/**
	 * Delegates second selection of each turn.
	 */
	public void secondSelection(ImageView cardFaceDown) {

		ImageView cardFaceUp = cardViewMap.get(cardSwapMap.get(cardFaceDown.getId()));
		turnKeeper[1][0] = cardFaceDown;
		turnKeeper[1][1] = cardFaceUp;
		if (TextUtils.equals(turnKeeper[0][1].getTag().toString(), turnKeeper[1][1].getTag().toString())) {
			comboTracker++;
			currentScore += (2 + comboTracker);
			new GamePlayController(activity, turnKeeper, currentScore, GamePlayController.SECOND_MOVE_MATCHED).evaluate();
			matchCounter++;
			if (gameMode.isTimeTrialGameMode()) {
				int timeToAdd = TimerModel.TIME_ADDED;
				timeToAdd += Math.min(comboTracker, MAX_COMBO);
				timeLeft.addTime(timeToAdd);
			}
		} else {
			currentScore--;
			comboTracker = 0;
			new GamePlayController(activity, turnKeeper, currentScore, GamePlayController.SECOND_MOVE_MISMATCHED).evaluate();
		}
	}

	/**
	 * Checks if game has ended.
	 */
	public boolean hasGameEnded() {

		if (this.gameMode.isTimeTrialGameMode())
			return timeLeft.isFinished();
		return matchCounter == 8 ? true : false;
	}

	/**
	 * Checks if all cards are opened
	 */
	public boolean allCardsFlipped() {

		/*
		 * matchCounter % 8 == 0 : used to check if all cards are flipped up
		 * matchCounter != lastMatchCounter : used to check if cards were already flipped down with this matchCounter
		 */
		return matchCounter % 8 == 0 && matchCounter != lastMatchCounter;
	}

	/**
	 * Returns final score of the game once it has ended.
	 */
	public int getFinalScore() {

		if (hasGameEnded())
			return currentScore;
		return -666; // The Number of the Beast passed instead of throwing Exception. \m/
	}

	/**
	 * Randomises and makes new game.
	 *
	 * @author rahulthakur
	 */
	private class GameBoardSetupHandler {

		private SparseIntArray cardCounter;
		private int randomCardID;
		private Random randomGenerator;

		private GameBoardSetupHandler() {

			cardCounter = new SparseIntArray();
			randomGenerator = new Random();
		}

		/**
		 * Lays out cards for the new game layout.
		 */
		private void makeNewGame() {

			int[] list = new int[8];
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++) {
					String extra = "";
					int rnd = randomiser();
					if (card_prefix.startsWith("s_"))
						if (list[rnd - 1] == 0) {
							int rnd2 = randomGenerator.nextInt(2) + 1;
							extra = "_" + rnd2;
							list[rnd - 1] = rnd2;
						} else
							extra = "_" + ((list[rnd - 1] % 2) + 1);
					int cardDrawableID = activity.getResources().getIdentifier(card_prefix + rnd + extra, "drawable", activity.getPackageName());
					cardViewArray[i][j][1].setImageDrawable(activity.getResources().getDrawable(cardDrawableID));
					// The drawable is set as the tag for easily retrieving the type
					if (card_prefix.startsWith("s"))
						cardViewArray[i][j][1].setTag(rnd);
					else
						cardViewArray[i][j][1].setTag(cardDrawableID);
				}
		}

		/**
		 * Randomises cards w.r.t. their IDs.
		 */
		private int randomiser() {

			int random = randomGenerator.nextInt(8) + 1;
			if (randomCardID != random) {
				randomCardID = random;
				if (cardCounter.indexOfKey(randomCardID) >= 0) {
					if (cardCounter.get(randomCardID) < 2) {
						cardCounter.put(randomCardID, cardCounter.get(randomCardID) + 1);
						return randomCardID;
					} else {
						randomCardID = randomiser();
						return randomCardID;
					}
				} else {
					cardCounter.put(randomCardID, 1);
					return randomCardID;
				}
			} else {
				randomCardID = randomiser();
				return randomCardID;
			}
		}
	}
}
