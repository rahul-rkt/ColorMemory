
package thakur.rahul.colourmemory.view.animation;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

/**
 * animationListener for Flip Animation. Swaps between views when first half of the animation is complete.
 *
 * @author rahulthakur
 */
public final class DisplayNextView implements AnimationListener {

	private boolean currentView;
	private ImageView image1;
	private ImageView image2;

	public DisplayNextView(boolean currentView, ImageView image1, ImageView image2) {

		this.currentView = currentView;
		this.image1 = image1;
		this.image2 = image2;
	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		image1.post(new SwapViews(currentView, image1, image2));
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}
}
