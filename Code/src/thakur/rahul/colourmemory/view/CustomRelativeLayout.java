
package thakur.rahul.colourmemory.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.RelativeLayout;

/**
 * Custom RelativeLayout for slide animation w.r.t. to the height of the view as percentages corresponding to 1.0
 * through 0.0
 *
 * @author rahulthakur
 */
public class CustomRelativeLayout extends RelativeLayout {

	private float yFraction = 0;
	private OnPreDrawListener preDrawListener = null;

	public CustomRelativeLayout(Context context) {

		super(context);
	}

	public CustomRelativeLayout(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
	}

	/**
	 * Calculates and sets Y Translation for this custom view.
	 */
	public void setYFraction(float fraction) {

		this.yFraction = fraction;
		if (getHeight() == 0) {
			if (preDrawListener == null) {
				preDrawListener = new OnPreDrawListener() {

					@Override
					public boolean onPreDraw() {

						getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
						setYFraction(yFraction);
						return true;
					}
				};
				getViewTreeObserver().addOnPreDrawListener(preDrawListener);
			}
			return;
		}
		float translationY = getHeight() * fraction;
		setTranslationY(translationY);
	}

	public float getYFraction() {

		return this.yFraction;
	}
}