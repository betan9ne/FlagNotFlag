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
	private static final String DATABASE_NAME = "tellr";
	// Login table name
	private static final String TABLE_LOGIN = "login";
// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PHONE = "name";
	private static final String KEY_UID = "u_id";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_ISO = "iso";
	private static final String KEY_SCORE = "score";


	public HighScoreHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_EMAIL + " TEXT,"
				+ KEY_PHONE + " TEXT,"
				+ KEY_UID + " TEXT,"
				+ KEY_PHOTO + " TEXT,"
				+ KEY_ISO + " TEXT,"
				+ KEY_SCORE + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser( String email, String u_id, String phone, String photo, String iso, String score) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, email);
		values.put(KEY_PHONE, phone);
		values.put(KEY_UID, u_id);
		values.put(KEY_PHOTO, photo);
		values.put(KEY_ISO, iso);
		values.put(KEY_SCORE, score);
		// Inserting Row
		long id = db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	 	Log.d("get_user", "user created: " + id + " " + email + " " + photo + " " +u_id);
	}


	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails(String tag) {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("email", cursor.getString(1));
			user.put("name", cursor.getString(2));
			user.put("u_id", cursor.getString(3));
			user.put("photo", cursor.getString(4));
			user.put("iso", cursor.getString(5));
			user.put("score", cursor.getString(6));

		}
		cursor.close();
		db.close();
		// return user
		Log.d("get_user", tag+" Fetching user data: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();

		Log.d("get_user", "We are starting over");
	}


}
