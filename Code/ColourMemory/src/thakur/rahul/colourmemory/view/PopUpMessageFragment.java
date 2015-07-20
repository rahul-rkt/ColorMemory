

package thakur.rahul.colourmemory.view;

import thakur.rahul.colourmemory.R;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Creates the New High Score fragment.
 *
 * @author janssen
 */
public class PopUpMessageFragment extends Fragment {

	private Button okayButton;
	private String message;
	private TextView textView;
	
	public PopUpMessageFragment(String message) {
		this.message = message;
	}
	
	/**
	 * Sets Pop Up Message  and returns instance of the fragment.
	 */
	public static PopUpMessageFragment instantiate(String message) {

		PopUpMessageFragment instance = new PopUpMessageFragment(message);
		return instance;
	}
	
	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_popup_message, container, false);
	}

	@Override
	public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {

		if (enter)
			return AnimatorInflater.loadAnimator(getActivity(), R.anim.fade_in);
		else
			return AnimatorInflater.loadAnimator(getActivity(), R.anim.fade_out);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * Validates input and if not empty sends it back to parent activity.
	 */
	@Override
	public void onResume() {

		super.onResume();
		okayButton = (Button) getView().findViewById(R.id.okayButton);
		textView = (TextView) getView().findViewById(R.id.popupMessage);
		final Fragment currentFragment = this;
		textView.setText(message);
		okayButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewGroup vg = (ViewGroup)getActivity().findViewById(R.id.theme_shop_button_layout);
				for( int i = 0; i < vg.getChildCount(); i++ ) {
					vg.getChildAt(i).setEnabled(true);
				}
				getActivity().getFragmentManager().beginTransaction().remove(currentFragment).commit();
			}
		});
	}

}
