/*
 * @author GeckoApps Team
 */
package gecko.mybaby.controller;

import gecko.mybaby.model.Vaccine;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class VaccineController {
    
    private SQLiteHelper dbHelper;
    
    public VaccineController(Context context) {
    	
        this.dbHelper = new SQLiteHelper(context);
    }
    
    public boolean addVaccine(Vaccine vaccine) {
    	
        if (vaccine == null) {
        	
        	Log.e("Meu Bebê", "vaccine null");
            return false;
        }
        
        String name = vaccine.getName();
        String dose = vaccine.getDose();
        int month = vaccine.getMonth();
        String description = vaccine.getDescription();
        
        // Create the row to be inserted.
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("dose", dose);
        values.put("month", month);
        values.put("description", description);
        values.put("def", false);
        values.putNull("id"); // Vaccine's id is defined by the DBMS. To this
                              // happens, the value must be null.
        
        if (!this.dbHelper.open()) {

        	Log.e("Meu Bebê", "db not open");
            return false;
        }
        
        boolean result = this.dbHelper.insertContent(values, "Vaccine");
        this.dbHelper.close();
        
        return result;
    }
    
    public boolean updateVaccine(Vaccine vaccine) {
    	    	
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        String sql = "update Vaccine set name = \"" + vaccine.getName() + "\", month = \"" + vaccine.getMonth() + "\", dose = \"" + vaccine.getDose() + "\", description = \"" + vaccine.getDescription() + "\", def = \"" + vaccine.isDefault() + "\" where id = " + vaccine.getId();
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
    public boolean removeVaccine(int vaccineID) {
    	
        String sql = "delete from Vaccine where id = " + vaccineID;
        if (!this.dbHelper.open()) {
        	
            return false;
        }
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        return result;
    }
    
    public ArrayList<Vaccine> getAllVaccines() {
    	
        String sql = "select * from Vaccine order by month";
        ArrayList<Vaccine> vaccines = new ArrayList<Vaccine>();
        Vaccine item = null;
        
        if (!this.dbHelper.open()) {
        	
            return null;
        }
        
        Cursor cursor = this.dbHelper.executeQuery(sql);
        if (cursor == null) {
        	
            return null;
        }
        
        try {
        	
            while (cursor.moveToNext()) {
            	
                //name, dose, month, description, id, def.
                item = new Vaccine(cursor.getString(0), cursor.getString(1), cursor.getShort(2), cursor.getString(3), cursor.getString(4).equals("true"));
                item.setId(cursor.getInt(5));
                vaccines.add(item);
            }
        } catch (Exception e) {
        	
            this.dbHelper.close();
            return null;
        }
        
        cursor.close();
        this.dbHelper.close();
        
        return vaccines;
    }
    
}