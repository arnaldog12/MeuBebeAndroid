/*
 * @author GeckoApps Team
 */
package gecko.mybaby.controller;

import gecko.mybaby.model.Baby;
import gecko.mybaby.model.Historic;
import gecko.mybaby.model.Progress;
import gecko.mybaby.webservice.AddRemoteBaby;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BabyController {
    
    private SQLiteHelper dbHelper;
    private HistoricController historicController;
    
    public BabyController(Context context) {
    	
        this.dbHelper = new SQLiteHelper(context);
        this.historicController = new HistoricController(context);
    }
    
    public boolean addBaby(Baby baby) {
    	
        if (!this.dbHelper.open() || baby == null) {
        	
            return false;
        }
        
        String name = baby.getName();
        String birth = baby.getBirth();
        int gender = baby.getGender();
        Bitmap image = baby.getImage();
        if (image == null) {

            return false;
        }
        
        // Take the bytes of the baby's photo.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        
        // Create the row to be inserted.
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("birth", birth);
        values.put("sex", gender);
        values.putNull("id"); // Baby's id is defined by the DBMS. To this
                              // happens, the value must be null.
        
        values.put("image", bytes);
        
        long new_id = this.dbHelper.insertContent(values, "Baby");
        Log.d("MeuBebe", "Adicionando bebe");
        ContentValues pv = new ContentValues();
        pv.put("babyID", new_id);
        pv.put("progress", AddRemoteBaby.URL_PROGRESS_NULL);
        
        try {
        
        	this.dbHelper.insertContent(pv, "Progress");
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }
        boolean result = new_id != -1L;
        this.dbHelper.close();
        
        return result;
    }
    
    public Baby getBabyPerId(int id) {
    	
        String sql = "select * from Baby where id = " + id;
        
        Historic historic = this.historicController.getHistoricPerBaby(id);
        Bitmap image = null;
        Baby baby = null;
        
        if (!this.dbHelper.open() || (historic == null)) {
        	
            return null;
        }
        
        Cursor cursor = this.dbHelper.executeQuery(sql);
        if (cursor == null || (cursor.getCount() == 0)) {
        	
            this.dbHelper.close();
            
            return null;
        }
        
        cursor.moveToFirst();
        byte[] bytes = cursor.getBlob(5);
        image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        
        try {
        	
            baby = new Baby(cursor.getString(0), cursor.getString(1), cursor.getInt(2), new Historic(), new Progress(), image);
        } catch (Exception ex) {
        	
            baby = null;
        }
        
        cursor.close();
        
        this.dbHelper.close();
        return baby;
    }
    
    public ArrayList<Baby> getAllBabys() {
    	
        if (!this.dbHelper.open()) {
        	
            return null;
        }
        
        String sql = "select * from Baby";
        
        Cursor cursor = this.dbHelper.executeQuery(sql);
        ArrayList<Baby> babys = new ArrayList<Baby>();
        Baby item;
        Bitmap image = null;
        byte[] bytes = null;
        
        if (cursor == null) {
        	
            this.dbHelper.close();
            return null;
        }
        
        try {
        	
            while (cursor.moveToNext()) {
            	
                bytes = cursor.getBlob(5);
                image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                item = new Baby(cursor.getString(0), cursor.getString(1), (short) cursor.getInt(2), new Historic(), new Progress(), image);
                item.setId(cursor.getInt(4));
                babys.add(item);
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
            babys = null;
        }
        
        cursor.close();
        
        this.dbHelper.close();
        return babys;
    }
    
    public boolean removeBaby(int id) {
    	
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        String sql = "delete from Baby where id = " + id;
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
    public boolean setBabyName(int babyID, String name) {
    	
        if (!this.dbHelper.open()) {
        	
            return false;
        }
        
        String sql = "update Baby set name = \"" + name + "\" where id = " + babyID;
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
    public boolean setBabySex(int babyID, int sex) {
    	
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        String sql = "update Baby set sex = " + sex + " where id = " + babyID;
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
    public boolean setBabyBirth(int babyID, String birth) {
    	
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        String sql = "update Baby set birth = \"" + birth + "\" where id = " + babyID;
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
    public boolean setBabyImage(int babyID, Bitmap image) {
    	
        if (!this.dbHelper.open() || image == null) {
        	
            return false;
        }
        
        String whereClause = "id = " + babyID;
        
        ContentValues values = new ContentValues();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        values.put("image", bytes);
        
        this.dbHelper.updateContent(values, "Baby", whereClause);
        this.dbHelper.close();
        
        return true;
    }
    
    public boolean setBabyProgress(int babyID, long progress) {
    	
        if (this.dbHelper.open() == false) {
        	
            return false;
        }
        
        String sql = "update Baby set progress = \"" + progress + "\" where id = " + babyID;
        
        boolean result = this.dbHelper.executeSQL(sql);
        this.dbHelper.close();
        
        return result;
    }
    
}