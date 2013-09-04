package gecko.mybaby.provider;

import gecko.mybaby.controller.SQLiteHelper;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class BabyProvider extends ContentProvider {

	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final String AUTH = "gecko.mybaby.provider";
	private static final int ALL_CODE = 0;
	private static final int SINGLE_CODE = 1;
	
	static {
		
		MATCHER.addURI(AUTH, "bebe", ALL_CODE);
		MATCHER.addURI(AUTH, "bebe/#", SINGLE_CODE);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int ret = 0;
		SQLiteHelper helper = null;
		
		switch (MATCHER.match(uri)) {
		
			case SINGLE_CODE:
				
				helper = new SQLiteHelper(this.getContext());
				if (helper.open()) {
				
					if (helper.executeSQL("DELETE FROM Baby where id = " + uri.getLastPathSegment())) {
						
						ret = 1;
					}
				}
				break;
		}
		
		return ret;
	}

	@Override
	public String getType(Uri uri) {

		switch (MATCHER.match(uri)) {
		
			case ALL_CODE:
				
				return ContentResolver.CURSOR_DIR_BASE_TYPE + "/gecko.mybaby.baby";

				
			case SINGLE_CODE:
				
				return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/gecko.mybaby.baby";
				
		}
		
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteHelper helper = null;
		
		switch (MATCHER.match(uri)) {
		
			case ALL_CODE:
				
				helper = new SQLiteHelper(this.getContext());
				if (helper.open()) {
				
					long new_id = helper.insertContent(values, "Baby");
					
					return Uri.parse(AUTH + "/bebe/" + new_id);
				}
				break;
		}
		
		return null;
	}

	@Override
	public boolean onCreate() {

		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		Cursor ret = null;
		SQLiteHelper helper = null;
		
		switch (MATCHER.match(uri)) {
		
			case ALL_CODE:
				
				helper = new SQLiteHelper(this.getContext());
				if (helper.open()) {
				
					ret = helper.executeQuery("SELECT *  FROM Baby;");
				}
				break;
				
			case SINGLE_CODE:
				
				helper = new SQLiteHelper(this.getContext());
				if (helper.open()) {
				
					ret = helper.executeQuery("SELECT *  FROM Baby where id = " + uri.getLastPathSegment());
				}
				break;
		}
		
		return ret;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int ret = 0;
		SQLiteHelper helper = null;
		
		switch (MATCHER.match(uri)) {
		
			case ALL_CODE:
				
				helper = new SQLiteHelper(this.getContext());
				if (helper.open()) {
				
					ret = helper.updateContent(values, "Baby", "");
				}
				break;
				
			case SINGLE_CODE:
				
				helper = new SQLiteHelper(this.getContext());
				if (helper.open()) {
				
					ret = helper.updateContent(values, "Baby", "id = " + uri.getLastPathSegment());
				}
				break;
		}
		
		return ret;
	}

}
