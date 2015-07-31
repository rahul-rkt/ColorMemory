
package thakur.rahul.colourmemory.controller;

import thakur.rahul.colourmemory.R;
import thakur.rahul.colourmemory.model.ScoreModel;
import thakur.rahul.colourmemory.model.ScorePojo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom ArrayAdapter to iterate through list of scores and populate listview.
 *
 * @author rahulthakur
 */
public class HighScoreListController extends ArrayAdapter<ScorePojo> {

	private HighScoreListController(Context context) {

		super(context, 0, ScoreModel.getScoresList());
	}

	public static HighScoreListController getAdapter(Context context) {

		return new HighScoreListController(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ScorePojo score = getItem(position);
		if (convertView == null)
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
		TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
		TextView scoreTextView = (TextView) convertView.findViewById(R.id.scoreTextView);
		nameTextView.setText(score.getName());
		scoreTextView.setText(String.valueOf(score.getScore()));
		return convertView;
	}
}
