package thakur.rahul.colourmemory.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import thakur.rahul.colourmemory.MainActivity;
import thakur.rahul.colourmemory.controller.CardController;

public class ThemeModel {
	private static ThemeModel singleton;
	
	private ThemeModel() {
		theme = DEFAULT_THEME;
		
		unlockedThemes = new HashSet<String>();
		loadThemes();
		
		//if nothing was loaded
		if( unlockedThemes.size() == 0 ) {
			unlockedThemes.add(DEFAULT_THEME);
			unlockedThemes.add(RED_THEME);
			
			saveTheme(DEFAULT_THEME, 1);
			saveTheme(RED_THEME, 0);
		}
	}
	
	public static ThemeModel getInstance() {
		if(singleton == null) {
			singleton = new ThemeModel();
		}
		return singleton;
	}
	
	private HashSet<String> unlockedThemes;
	public static final String DEFAULT_THEME = "colour_";
	public static final String RED_THEME = "red_";
	public static final String DIGIMON_THEME = "s_digimon_";
	public static final String GREEN_THEME = "green_";
	private String theme;

	public String[] getAllThemes() {
		return new String[] {DEFAULT_THEME, RED_THEME, DIGIMON_THEME, GREEN_THEME  };
	}
	
	public HashMap<String,String> getUnlockRequirements() {
		HashMap<String,String> toReturn = new HashMap<String,String>();
		toReturn.put( DEFAULT_THEME, "already unlocked at the beginning" );
		toReturn.put( DEFAULT_THEME, "already unlocked at the beginning" );
		toReturn.put( GREEN_THEME, "finish a classic game at least once" );
		toReturn.put( DIGIMON_THEME, "score at least 10 points in time trial." );
		
		return toReturn;
	}
	
	public String getThemeUnlockRequirement(String theme) {
		return getUnlockRequirements().get(theme);
	}
	
	public void setTheme(String theme) {
		String currentTheme = theme;
		String prevTheme = this.theme;
		updateSelectedTheme(currentTheme, prevTheme);
		
		this.theme = currentTheme;
	}
	
	public String getTheme() {
		return this.theme;
	}
	
	public boolean isUnlocked(String theme) {
		return unlockedThemes.contains(theme);
	}
	
	public void unlock(String theme) {
		if(!unlockedThemes.contains(theme)) {
			unlockedThemes.add(theme);
			saveTheme(theme, 0);
		}
	}
	
	
	private void setUnlockedThemes(List<String> themes) {
		unlockedThemes = new HashSet<String>();
		
		for( String theme : themes) {
			unlockedThemes.add(theme);
		}
	}
	
	/**
	 * Checks if new themes are unlocked.
	 */
	public void checkForUnlockedThemes(CardController cardController) {
		if(GameModeModel.getInstance().isNormalGameMode())
			unlock(GREEN_THEME);
		if(GameModeModel.getInstance().isTimeTrialGameMode() && cardController.getFinalScore() >= 10 )
			unlock(DIGIMON_THEME);
	}
	
	/**
	 * Updates record when theme is selected
	 */
	public void updateSelectedTheme(String currentTheme, String prevTheme) {
	    SQLiteDatabase db = DatabaseHandler.getHandler().getWritableDatabase();
	 
	    ContentValues values1 = new ContentValues();
	    values1.put(DatabaseHandler.THEMES_KEY_NAME, currentTheme);
	    values1.put(DatabaseHandler.THEMES_KEY_SELECTED, 1);
	    
	    ContentValues values2 = new ContentValues();
	    values2.put(DatabaseHandler.THEMES_KEY_NAME, prevTheme);
	    values2.put(DatabaseHandler.THEMES_KEY_SELECTED, 0);
	    
	    // updating row
	    db.update(DatabaseHandler.THEMES_TABLE_NAME, values1, DatabaseHandler.THEMES_KEY_NAME + " = ?",
	            new String[] { currentTheme });
	    db.update(DatabaseHandler.THEMES_TABLE_NAME, values2, DatabaseHandler.THEMES_KEY_NAME + " = ?",
	            new String[] { prevTheme });
	    
	}
	
	
	/**
	 * Adds new unlocked theme to the database.
	 *
	 * @return <b>true</b> when successful, <b>false</b> when unsuccessful
	 */
	
	private boolean saveTheme(String themeName, int isSelected) {

		SQLiteDatabase db = DatabaseHandler.getHandler().getWritableDatabase();
		boolean isSuccessful = false;
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.THEMES_KEY_NAME, themeName);
		values.put(DatabaseHandler.THEMES_KEY_SELECTED, isSelected);
		if (db.insert(DatabaseHandler.THEMES_TABLE_NAME, null, values) != -1)
			isSuccessful = true;
		db.close();
		return isSuccessful;
	}
	
	/**
	 * Returns List of all rows present in the database.
	 */
	private void loadThemes() {

		boolean closeOnExit = true;
		if (DatabaseHandler.getHandler().getWritableDatabase().isOpen())
			closeOnExit = false;
		SQLiteDatabase db = DatabaseHandler.getHandler().getWritableDatabase();
		List<String> themeList = new ArrayList<String>();
		Cursor cursor = db.rawQuery(DatabaseHandler.THEMES_QUERY_SELECT_ALL, null);
		if (cursor.moveToFirst())
			do {
				ScorePojo scoreObject = new ScorePojo();
				String themeName = cursor.getString(1);
				int isSelected = cursor.getInt(2);
				themeList.add(themeName);
				
				if( isSelected == 1 ) {
					this.theme = themeName;
				}
			} while (cursor.moveToNext());
		
		setUnlockedThemes(themeList);
		
		if (closeOnExit)
			db.close();
	}
}
