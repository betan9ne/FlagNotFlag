package apps.betan9ne.flagnotflag.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class HighScoreHandler extends SQLiteOpenHelper {

	private static final String TAG = HighScoreHandler.class.getSimpleName();
// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
// Database Name
	private static final String DATABASE_NAME = "highscore";
	// Login table name
	private static final String TABLE_LOGIN = "highscore";
// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_11 = "a";
	private static final String KEY_16 = "b";
	private static final String KEY_21 = "c";
	private static final String KEY_26 = "d";
	private static final String KEY_31 = "e";
	private static final String KEY_36 = "f";


	public HighScoreHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_EMAIL + " TEXT,"
				+ KEY_11 + " TEXT,"
				+ KEY_16 + " TEXT,"
				+ KEY_21 + " TEXT,"
				+ KEY_26+ " TEXT,"
				+ KEY_31 + " TEXT,"
				+ KEY_36 + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
	//	db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addScore( String email, String timer, String score) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email);
		values.put(timer, score);
		// Inserting Row
		long id = db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	 	Log.d("get_user", "score created: " + id + " " + email + " " + score);
	}


	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserScore(String tag, String email, int timer) {
		int index = 0;
		if(timer  ==11)
		{
			index = 2;
		}else if(timer  ==16){index = 3;}
		else if(timer  ==21){index = 4;}
		else if(timer  ==26){index = 5;}
		else if(timer  ==31){index = 6;}
		else if(timer  ==36){index = 7;}

		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE email = " + "'"+email+"'" ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("email", cursor.getString(1));
			user.put("score", cursor.getString(index));
		}
		cursor.close();
		db.close();
		// return user
		Log.d("get_user", tag+" Fetching user data: " + user.toString());

		return user;
	}
}
