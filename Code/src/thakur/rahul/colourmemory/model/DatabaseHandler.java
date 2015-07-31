package thakur.rahul.colourmemory.model;

import thakur.rahul.colourmemory.MainActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Creates and maintains High Score Database.
 *
 * @author rahulthakur
 */
public final class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "colour_memory";
	public static final String TABLE_NAME = "score_manager";
	private static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "score";
	private static final String QUERY_CREATE_TABLE = "CREATE TABLE "
	                                                 + TABLE_NAME
	                                                 + "("
	                                                 + KEY_ID
	                                                 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	                                                 + KEY_NAME
	                                                 + " VARCHAR(15),"
	                                                 + KEY_SCORE
	                                                 + " INTEGER)";
	private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	public static final String QUERY_UPDATE_TABLE = "DELETE FROM "
	                                                 + TABLE_NAME
	                                                 + " WHERE "
	                                                 + KEY_ID
	                                                 + " NOT IN(SELECT "
	                                                 + KEY_ID
	                                                 + " FROM "
	                                                 + TABLE_NAME
	                                                 + " ORDER BY "
	                                                 + KEY_SCORE
	                                                 + " DESC, "
	                                                 + KEY_ID
	                                                 + " DESC LIMIT 10)";
	public static final String QUERY_SELECT_ALL = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_SCORE + " DESC, " + KEY_ID + " DESC";
	private static volatile DatabaseHandler UNIQUE_INSTANCE;

	
	//Themes Database attributes
	public static final String THEMES_TABLE_NAME = "unlocked_themes";
	private static final String THEMES_KEY_ID = "_id";
	public static final String THEMES_KEY_NAME = "name";
	public static final String THEMES_KEY_SELECTED = "isSelected"; //1 = True; 0 = False
	private static final String THEMES_QUERY_CREATE_TABLE = "CREATE TABLE "
	                                                 + THEMES_TABLE_NAME
	                                                 + "("
	                                                 + THEMES_KEY_ID
	                                                 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	                                                 + THEMES_KEY_NAME
	                                                 + " VARCHAR(30),"
	                                                 + THEMES_KEY_SELECTED
	                                                 + " INTEGER)";
	private static final String THEMES_QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + THEMES_TABLE_NAME;

	public static final String THEMES_QUERY_SELECT_ALL = "SELECT  * FROM " + THEMES_TABLE_NAME + " ORDER BY " + 
															THEMES_KEY_SELECTED + " DESC, " + THEMES_KEY_ID + " DESC";
	/**
	 * Returns DatabaseHandler Singleton.
	 */
	public static DatabaseHandler getHandler() {

		if (UNIQUE_INSTANCE == null)
			synchronized (DatabaseHandler.class) {
				if (UNIQUE_INSTANCE == null)
					UNIQUE_INSTANCE = new DatabaseHandler();
			}
		return UNIQUE_INSTANCE;
	}

	private DatabaseHandler() {

		super(MainActivity.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(QUERY_CREATE_TABLE);
		db.execSQL(THEMES_QUERY_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL(QUERY_DROP_TABLE);
		db.execSQL(THEMES_QUERY_DROP_TABLE);
		onCreate(db);
	}
}