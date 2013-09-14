/*
 * @author GeckoApps Team
 */
package gecko.mybaby.controller;

import gecko.mybaby.model.Reminder;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class ReminderController {
    
    private SQLiteHelper dbHelper;
    
    public ReminderController(Context context) {
    	
        this.dbHelper = new SQLiteHelper(context);
    }
    
    public boolean addReminder(Reminder reminder) {

        String date = reminder.getDate();
        String time = reminder.getTime();
        String message = reminder.getMessage();
        int babyId = reminder.getBabyId();
        int vaccineId = reminder.getVaccineId();
        int reminderId = reminder.getReminderId();
        
        String sql = "insert into Reminder values (" + "\"" + date + "\",\"" + time + "\",\"" + message + "\",\"" + 
        			  babyId + "\",\"" + vaccineId + "\",\"" + reminderId + "\")";
        
        if (!this.dbHelper.open()) {
        	
            return false;
        }
        
        try {
        	
	        this.dbHelper.executeSQL(sql);
	        this.dbHelper.close();
        } catch (Exception exception) {
        	
        	exception.printStackTrace();
        }
        
        return true;
    }
    
    public ArrayList<Reminder> getAllReminders() {
    	
        if (!this.dbHelper.open()) {
        	
            return null;
        }
        
        String sql = "select * from Reminder";
        
        Cursor cursor = this.dbHelper.executeQuery(sql);
        Reminder item;
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        
        if (cursor == null) {
        	
            this.dbHelper.close();
        	
            return null;
        }
        
        try {
        	
            while (cursor.moveToNext()) {
            	
                item = new Reminder(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                item.setReminderId(cursor.getInt(5));
                reminders.add(item);
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            reminders = null;
        }
        
        cursor.close();
        this.dbHelper.close();
        
        return reminders;
    }
    
    public ArrayList<Reminder> getRemindersPerBaby(int babyId) {
    	
        if (!this.dbHelper.open()) {
        	
            return null;
        }
        
        String[] babyIds = {String.valueOf(babyId)};
        Cursor cursor = dbHelper.getReadableDatabase().query("Reminder", null, "babyID = ?", babyIds, null, null, null);
        // String sql = "select * from Reminder where babyID = " + babyID;
        // Cursor cursor = this.dbHelper.executeQuery(sql);
        Reminder item;
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        
        if (cursor == null) {
        	
            this.dbHelper.close();
            return null;
        }
        
        try {
        	
            while (cursor.moveToNext()) {
            	
                item = new Reminder(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                item.setReminderId(cursor.getInt(5));
                reminders.add(item);
            }
        } catch (Exception e) {

            e.printStackTrace();
            reminders = null;
        }
        
        this.dbHelper.close();
        
        return reminders;
    }
    
    public ArrayList<Reminder> getRemindersPerBabyAndVaccine(int babyId, int vaccineId) {
    	
        if (!this.dbHelper.open()) {
        	
            return null;
        }
        
        String[] selectionArgs = {String.valueOf(babyId), String.valueOf(vaccineId)};
        Cursor cursor = this.dbHelper.getReadableDatabase().query("Reminder", null, "babyID = ? and vaccineID = ?", selectionArgs, null, null, null);
        
        Reminder item;
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        
        if (cursor == null) {
        	
            this.dbHelper.close();
            return null;
        }
        
        try {
        	
            while (cursor.moveToNext()) {
            	
                item = new Reminder(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                item.setReminderId(cursor.getInt(5));
                reminders.add(item);
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            reminders = null;
        }
        
        this.dbHelper.close();
        
        return reminders;
    }
    
    public ArrayList<Reminder> getRemindersPerDate(String date) {
    	
        if (!this.dbHelper.open()) {
        	
            return null;
        }
        
        String sql = "select * from Reminder where date = " + "\"" + date + "\"";
        Cursor cursor = this.dbHelper.executeQuery(sql);
        Reminder item;
        ArrayList<Reminder> lembretes = new ArrayList<Reminder>();
        
        if (cursor == null) {
        	
            this.dbHelper.close();
            return null;
        }
        
        try {
        	
            while(cursor.moveToNext()) {
            	
                item = new Reminder(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                item.setReminderId(cursor.getInt(5));
                lembretes.add(item);
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            lembretes = null;
        }
        
        this.dbHelper.close();
        
        return lembretes;
    }
    
    public boolean removeReminder(int babyId, int vaccineId, int reminderId) {
    	
        if (!this.dbHelper.open()) {
        	
            return false;
        }
        
        String[] str = {String.valueOf(babyId), String.valueOf(vaccineId), String.valueOf(reminderId)};
        this.dbHelper.getWritableDatabase().delete("Reminder", "babyID = ? and vaccineID = ? and id = ?", str);
        
        this.dbHelper.close();
        
        return true;
    }
}