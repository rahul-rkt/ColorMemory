
package thakur.rahul.colourmemory.controller;

import thakur.rahul.colourmemory.R;
import thakur.rahul.colourmemory.view.animation.DisplayNextView;
import thakur.rahul.colourmemory.view.animation.FlipAnimation;
import android.app.Activity;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Creates, maintains and runs every Flip Animation in order. Updates current score when turn animation is complete.
 *
 * @author rahulthakur
 */
public class AnimationController implements Runnable {

	private Activity activity;
	private ImageView image1;
	private ImageView image2;
	private boolean isFirstImage = true;
	private boolean putToSleep;
	private ScoreHandler scorer;
	private int currentThread;
	private volatile static int totalThreads = 1;
	private volatile static int threadAllowedToRun = 1;
	private volatile static Object lock = new Object();
	private int preAnimationDelay = 0;
	private CardController cardController = null;
	
	public static Object getLock() {
		return lock;
	}
	
	public AnimationController(Activity activity, ImageView image1, ImageView image2, boolean putToSleep, Integer scoreToUpdate) {

		this.activity = activity;
		this.image1 = image1;
		this.image2 = image2;
		this.putToSleep = putToSleep;
		if (scoreToUpdate != null)
			scorer = new ScoreHandler(scoreToUpdate);
		this.currentThread = totalThreads++;
	}
	
	/*
	 * Sets delay before doing animation. 
	 * Used in Time Trial when users just opened all cards and we need to give a new set of cards.
	 */
	public AnimationController setPreAnimatinDelay(int delay) {
		this.preAnimationDelay = delay;
		return this;
	}
	
	/*
	 * Sets up a new game after all the animation is finished
	 */
	public AnimationController newGame(CardController cardController) {
		this.cardController = cardController;
		return this;
	}
	
	@Override
	public void run() {
		if( preAnimationDelay > 0 ) {
			try {
				Thread.sleep(preAnimationDelay);
			} catch (InterruptedException e) {
			}
		}
		synchronized (lock) {
			while (currentThread != threadAllowedToRun)
				try {
					lock.wait();
				} catch (InterruptedException e) {
				} catch (Exception e) {
				}
			if( cardController != null ) {
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						cardController.makeNewGame();
					}
					
				});
				
			}
			else {
				animate();
				if (putToSleep)
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				if (scorer != null)
					scorer.updateCurrentScore();
				
			}
			lock.notifyAll();
			threadAllowedToRun++;
		}
	}

	/**
	 * Starts first half of the Flip Animation.
	 */
	public void animate() {

		if (isFirstImage) {
			applyRotation(0, 90);
			isFirstImage = !isFirstImage;
		} else {
			applyRotation(0, -90);
			isFirstImage = !isFirstImage;
		}
	}

	/**
	 * Runs first half of the animation sequence.
	 */
	private void applyRotation(float start, float end) {

		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;
		final FlipAnimation rotation = new FlipAnimation(start, end, centerX, centerY);
		rotation.setDuration(300);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(isFirstImage, image1, image2));
		if (isFirstImage)
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					image1.startAnimation(rotation);
				}
			});
		else
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					image2.startAnimation(rotation);
				}
			});
	}
	
	
	/**
	 * Updates current score from UI Thread.
	 *
	 * @author rahulthakur
	 */
	private class ScoreHandler {

		private Button currentScoreButton;
		private int scoreToUpdate;

		private ScoreHandler(int score) {

			currentScoreButton = (Button) activity.findViewById(R.id.currentScoreButton);
			scoreToUpdate = score;
		}

		/**
		 * Updates current score from UI Thread.
		 */
		public void updateCurrentScore() {

			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					currentScoreButton.setText(String.valueOf(scoreToUpdate));
				}
			});
		}
	}
}
