
package thakur.rahul.colourmemory.view.animation;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Swaps views and runs the second half of the Flip Animation sequence.
 *
 * @author rahulthakur
 */
public final class SwapViews implements Runnable {

	private boolean isFirstView;
	private ImageView image1;
	private ImageView image2;

	public SwapViews(boolean isFirstView, ImageView image1, ImageView image2) {

		this.isFirstView = isFirstView;
		this.image1 = image1;
		this.image2 = image2;
	}

	/**
	 * Runs second half of the animation sequence.
	 */
	@Override
	public void run() {

		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;
		FlipAnimation rotation;
		if (isFirstView) {
			image1.setVisibility(View.INVISIBLE);
			image2.setVisibility(View.VISIBLE);
			image2.requestFocus();
			rotation = new FlipAnimation( -90, 0, centerX, centerY);
		} else {
			image2.setVisibility(View.INVISIBLE);
			image1.setVisibility(View.VISIBLE);
			image1.requestFocus();
			rotation = new FlipAnimation(90, 0, centerX, centerY);
		}
		rotation.setDuration(300);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new DecelerateInterpolator());
		if (isFirstView)
			image2.startAnimation(rotation);
		else
			image1.startAnimation(rotation);
	}
}