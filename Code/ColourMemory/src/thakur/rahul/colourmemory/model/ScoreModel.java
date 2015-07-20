
package thakur.rahul.colourmemory.model;

import java.util.ArrayList;
import java.util.List;

import thakur.rahul.colourmemory.MainActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handles all transaction to and from High Score Database.
 *
 * @author rahulthakur
 */
public final class ScoreModel {

	private static volatile Integer currentMinimumScoreInDB;

	private ScoreModel() {

	}

	/**
	 * Returns List of all rows present in the database.
	 */
	public static List<ScorePojo> getScoresList() {

		boolean closeOnExit = true;
		if (DatabaseHandler.getHandler().getWritableDatabase().isOpen())
			closeOnExit = false;
		SQLiteDatabase db = DatabaseHandler.getHandler().getWritableDatabase();
		List<ScorePojo> scoresList = new ArrayList<ScorePojo>();
		Cursor cursor = db.rawQuery(DatabaseHandler.QUERY_SELECT_ALL, null);
		if (cursor.moveToFirst())
			do {
				ScorePojo scoreObject = new ScorePojo();
				scoreObject.setName(cursor.getString(1));
				int score = cursor.getInt(2);
				if (currentMinimumScoreInDB == null || score < currentMinimumScoreInDB.intValue())
					currentMinimumScoreInDB = score;
				scoreObject.setScore(score);
				scoresList.add(scoreObject);
			} while (cursor.moveToNext());
		if (closeOnExit)
			db.close();
		return scoresList;
	}

	/**
	 * Returns current lowest high score present in the database.
	 */
	public static int getCurrentMinimumScoreInDB() {

		if (currentMinimumScoreInDB == null)
			getScoresList();
		return currentMinimumScoreInDB.intValue();
	}

	
	/**
	 * Adds new high score to the database.
	 *
	 * @return <b>true</b> when successful, <b>false</b> when unsuccessful
	 */
	public static boolean addScore(String name, int score) {

		SQLiteDatabase db = DatabaseHandler.getHandler().getWritableDatabase();
		boolean isSuccessful = false;
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_NAME, name);
		values.put(DatabaseHandler.KEY_SCORE, score);
		if (db.insert(DatabaseHandler.TABLE_NAME, null, values) != -1)
			if (getScoresList().size() > 10) {
				if (db.rawQuery(DatabaseHandler.QUERY_UPDATE_TABLE, null).moveToFirst())
					isSuccessful = true;
			} else
				isSuccessful = true;
		db.close();
		return isSuccessful;
	}

	
}
