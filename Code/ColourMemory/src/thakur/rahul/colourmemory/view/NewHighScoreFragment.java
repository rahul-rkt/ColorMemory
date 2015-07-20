
package thakur.rahul.colourmemory.view;

import thakur.rahul.colourmemory.R;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Creates the New High Score fragment.
 *
 * @author rahulthakur
 */
public class NewHighScoreFragment extends Fragment {

	private EditText playerNameEditText;
	private Button saveScoreButton;
	private NewHighScoreFragmentToActivityCommunicator communicator;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		communicator = (NewHighScoreFragmentToActivityCommunicator) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_new_high_score, container, false);
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
		playerNameEditText = (EditText) getView().findViewById(R.id.playerNameEditText);
		saveScoreButton = (Button) getView().findViewById(R.id.saveScoreButton);
		final Fragment currentFragment = this;
		saveScoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String playerName = playerNameEditText.getText().toString().trim();
				if (TextUtils.isEmpty(playerName)) {
					playerNameEditText.setError("Enter your name first!");
					return;
				}
				communicator.getPlayerName(playerName);
				View view = getActivity().getCurrentFocus();
				if (view == playerNameEditText) {
					InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(playerNameEditText.getWindowToken(), 0);
				}
				getActivity().getFragmentManager().beginTransaction().remove(currentFragment).commit();
			}
		});
	}

	/**
	 * Handles NewHighScoreFragment to parent activity communication.
	 *
	 * @author rahulthakur
	 */
	public interface NewHighScoreFragmentToActivityCommunicator {

		/**
		 * Sends player name from the fragment to parent activity.
		 */
		public void getPlayerName(String playerName);
	}
}
