/*
 * @author GeckoApps Team
 */
package gecko.mybaby.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "mybaby.db";
    
    // SQL statements to create the tables.
    private String tableBaby = "create table Baby (name, birth, sex check(sex=0 or sex=1), progress, id INTEGER PRIMARY KEY, image)";
    private String tableVaccine = "create table Vaccine (name, dose, month, description, def, id INTEGER PRIMARY KEY)";
    private String tableReminder = "create table Reminder (date, time, message, babyID, vaccineID, id, PRIMARY KEY(babyID, vaccineID, id)" + "FOREIGN KEY(babyID) references Baby(id) on delete cascade," + "FOREIGN KEY(vaccineID) references Vaccine(id) on delete cascade)";
    private String tableHistoric = "create table Historic (weight, height, month, babyID, PRIMARY KEY(babyID, month)" + "FOREIGN KEY(babyID) references Baby(id) on delete cascade)";
    private String tableTakenVaccine = "create table TakenVaccine (babyID, vaccineID, month, PRIMARY KEY(babyID, vaccineID)," + "FOREIGN KEY(babyID) references Baby(id) on delete cascade," + "FOREIGN KEY(vaccineID) references Vaccine(id) on delete cascade)";
    private String tableProgress = "create table Progress (babyID, progress, PRIMARY KEY(babyID, progress)," + " FOREIGN KEY(babyID) references Baby(id) on delete cascade)";
    
    private SQLiteDatabase database = null;
    
    public SQLiteHelper(Context context) {
    	
        super(context, SQLiteHelper.DATABASE_NAME,
        		null, SQLiteHelper.DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	try {
        db.execSQL(this.tableBaby);
        db.execSQL(this.tableVaccine);
        db.execSQL(this.tableReminder);
        db.execSQL(this.tableHistoric);
        db.execSQL(this.tableTakenVaccine);
        db.execSQL(this.tableProgress);
    	} catch (Exception e) {

    		e.printStackTrace();
		}
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        
        db.execSQL("DROP TABLE IF EXISTS Baby");
        db.execSQL("DROP TABLE IF EXISTS Vaccine");
        db.execSQL("DROP TABLE IF EXISTS Historic");
        db.execSQL("DROP TABLE IF EXISTS TakenVaccine");
        db.execSQL("DROP TABLE IF EXISTS Reminder");
        db.execSQL("DROP TABLE IF EXISTS LastSelectedBaby");
        db.execSQL("DROP TABLE IF EXISTS Progress");
        
        onCreate(db);
    }
    
    public boolean open() {
    	
        try {
        	
        	this.database = this.getWritableDatabase();
        } catch(SQLException error) {
        	
            return false;
        }
        
        return true;
    }
    
    public boolean executeSQL(String sql) {
    	
        if(this.database == null) {
        	
            return false;
        }
        try {
        	
            this.database.execSQL(sql);
        } catch(SQLException ex) {
        	
            return false;
        }
        
        return true;
    }
    
    public Cursor executeQuery(String sql) {
    	
        if(this.database == null) {
        	
            return null;
        }
        
        return this.database.rawQuery(sql, null);
    }
    
    public long insertContent(ContentValues content, String table) {
    	
        if(this.database == null) {
        	
            return -1;
        }
        
        return this.database.insert(table, null, content);
        
    }
    
    public int updateContent(ContentValues values, String table, String whereClause) {
    	
        if (this.database == null) {
        	
            return 0;
        }
        
        return this.database.update(table, values, whereClause, null);
    }    
}