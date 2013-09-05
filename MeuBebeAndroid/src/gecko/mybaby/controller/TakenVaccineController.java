/*
 * @author GeckoApps Team
 */
package gecko.mybaby.controller;

import gecko.mybaby.model.TakenVaccine;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class TakenVaccineController {
    
    private SQLiteHelper dbHelper;
    
    public TakenVaccineController(Context context) {
    	
        this.dbHelper = new SQLiteHelper(context);
    }
    
    //Adiciona uma vacina a lista de vacinas tomadas por um bebe.
    public boolean addTakenVaccine(int babyID, int vaccineID, int month) {
    	
        String sql = "insert into TakenVaccine values (" + babyID + "," + vaccineID + "," + month + ")";
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
    //Remove uma vacina da lista de vacinas tomadas por um bebe.
    public boolean removeTakenVaccine(int babyID, int vaccineID, int month) {
    	
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        String sql = "delete from TakenVaccine where babyID = " + babyID + " and vaccineID = " + vaccineID + " and month = " + month;
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }

	public boolean updateTakenVaccine(int babyID, int vaccineID, int month) {
    	
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        String sql = "update TakenVaccine set month = \"" + month + "\" where vaccineId = " + vaccineID + " and babyID = " + babyID;
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
    public ArrayList<TakenVaccine> takenVaccinesPerBaby(int babyID) {
    	
        ArrayList<TakenVaccine> list = new ArrayList<TakenVaccine>();
        String sql = "select vaccineID, month from TakenVaccine where babyID = " + babyID;
        
        if (this.dbHelper.open() == false) {
        	
            return null;
        }
        
        Cursor cursor = this.dbHelper.executeQuery(sql);
        if (cursor == null) {
        	
            return null;
        }
        
        try {
        	
            while (cursor.moveToNext()) {
            	
                list.add(new TakenVaccine(cursor.getInt(0), cursor.getInt(1)));
            }
        } catch (Exception e) {
        	
            list = null;
        }
        
        cursor.close();
        this.dbHelper.close();
        
        return list;
    }
    
}