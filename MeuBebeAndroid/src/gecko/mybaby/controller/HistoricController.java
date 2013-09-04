/*
 * @author GeckoApps Team
 */
package gecko.mybaby.controller;

import gecko.mybaby.model.Historic;
import gecko.mybaby.model.WeightHeight;
import android.content.Context;
import android.database.Cursor;

public class HistoricController {
    
    private SQLiteHelper dbHelper;
    
    public HistoricController(Context context) {
    	
        this.dbHelper = new SQLiteHelper(context);
    }
    
    public boolean addHistoric(WeightHeight wh, int babyId) {
    	
        if (wh == null) {
        	
            return false;
        }
        
        double weight = wh.getWeight();
        double height = wh.getHeight();
        int month = wh.getMonth();
        
        String sql = "insert into Historic values (" + weight + "," + height + "," + month + "," + babyId + ")";
        
        if (!this.dbHelper.open()) {
        	
            return false;
        }
        
        this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return true;
    }
    
    public Historic getHistoricPerBaby(int babyId) {
    	
        String sql = "select * from Historic where babyID = " + babyId;
        Historic historic = new Historic();
        WeightHeight item = null;
        
        if (!this.dbHelper.open()) {
        	
            return null;
        }
        
        Cursor cursor = this.dbHelper.executeQuery(sql);
        
        if (cursor == null) {
        	
            return null;
        }
        
        try {
        	
            while(cursor.moveToNext()) {
            	
                item = new WeightHeight(cursor.getInt(2), cursor.getFloat(0), cursor.getFloat(1));
                
                historic.addMeasure(item);
            }
        } catch (Exception e) {
        	
            historic = null;
        }
        
        cursor.close();
        this.dbHelper.close();
        
        return historic;
    }
    
    public boolean removeHistoric(int month, int babyID) {
    	
        String sql = "delete from Historic where month = " + month + " and babyID = " + babyID;
        
        if (!this.dbHelper.open()) {
        	
            return false;
        }
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
}