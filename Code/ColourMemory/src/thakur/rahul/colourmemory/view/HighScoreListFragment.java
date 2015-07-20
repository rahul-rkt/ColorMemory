
package thakur.rahul.colourmemory.view;

import thakur.rahul.colourmemory.R;
import thakur.rahul.colourmemory.controller.HighScoreListController;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Creates the High Score List fragment.
 *
 * @author rahulthakur
 */
public class HighScoreListFragment extends ListFragment {

	public static final String NOTIFICATION_KEY = "NotificationKey";
	private HighScoreListFragmentToActivityCommunicator communicator;

	/**
	 * Sets HighScoreListAdapter and returns instance of the fragment.
	 */
	public static HighScoreListFragment instantiate(boolean notifyOnDetach) {

		Log.d("DD", "HighScoreListFragment instantiate");
		HighScoreListFragment instance = new HighScoreListFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean(NOTIFICATION_KEY, notifyOnDetach);
		instance.setArguments(bundle);
		return instance;
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		if (getArguments().getBoolean(NOTIFICATION_KEY))
			communicator = (HighScoreListFragmentToActivityCommunicator) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_high_score_list, container, false);
	}

	@Override
	public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {

		if (enter)
			return AnimatorInflater.loadAnimator(getActivity(), R.anim.slide_up);
		else
			return AnimatorInflater.loadAnimator(getActivity(), R.anim.slide_down);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);
		setListAdapter(HighScoreListController.getAdapter(getActivity()));
	}

	@Override
	public void onDetach() {

		if (getArguments().getBoolean(NOTIFICATION_KEY))
			communicator.hasDetached();
		super.onDetach();
	}

	/**
	 * Handles HighScoreListFragment to parent activity communication.
	 *
	 * @author rahulthakur
	 */
	public interface HighScoreListFragmentToActivityCommunicator {

		/**
		 * Notifies parent activity if fragment has detached.
		 */
		public void hasDetached();
	}
}