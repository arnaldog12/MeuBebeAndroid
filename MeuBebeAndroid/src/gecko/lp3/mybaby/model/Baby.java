/*
 * @author GeckoApps Team
 */
package gecko.lp3.mybaby.model;

import java.util.Date;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Baby {
    
    public static final short SEX_GIRL = 0;
    public static final short SEX_BOY = 1;
    public static final short SEX_UNKNOWN = -1;
    
    private short id;
    private String name;
    private short sex;
    private String birth;
    private long progress;
    private Bitmap image;
    
    public Baby(String name, String birth, short sex, long progress, short id, Bitmap foto) {
    	
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.progress = progress;
        this.id = id;
        this.image = foto;
    }
    
    public Baby(String name, String birth, short sex, long progress, short id, String imageURL) {
    	
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.progress = progress;
        this.id = id;
        this.image = BitmapFactory.decodeFile(imageURL);
    }
    
    public Baby(String name, String birth, short sex, long progress, short id, Resources r, int resource) {
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.progress = progress;
        this.id = id;
        this.image = BitmapFactory.decodeResource(r, resource);
    }
    
    public String getName() {
    	
        return name;
    }
    
    public void setName(String name) {
    	
        this.name = name;
    }
    
    public String getBirth() {
    	
        return birth;
    }
    
    public void setBirth(String birth) {
    	
        this.birth = birth;
    }
    
    public short getSex() {
    	
        return sex;
    }
    
    public void setSex(short sex) {
    	
        this.sex = sex;
    }
    
    public long getProgress() {
    	
        return progress;
    }
    
    public boolean getProgressAt(short index) {
    	
        return ((((long) 1) << index) & progress) != 0;
    }
    
    public void setProgress(long progress) {
    	
        this.progress = progress;
    }
    
    public void setProgressAt(short index, boolean marked) {
    	
        if(marked) {
        	
            progress = ((((long) 1) << index) | progress);
        } else if(getProgressAt(index)) {
        	
            progress -= (((long) 1) << index);
        }
    }
    
    public int getId() {
    	
        return id;
    }
    
    public void setId(short id) {
    	
        this.id = id;
    }
    
    public Bitmap getImage() {
    	
        return this.image;
    }
    
    public void setImage(Bitmap image) {
    	
        this.image = image;
    }
    
    public int getDayBirthBaby() {
    	
        return Integer.parseInt(birth.split("/")[0]);
    }
    
    public int getMonthBirthBaby() {
    	
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String monthBirthString = birth.split("/")[1];
        int monthBirth = 0;
        
        for(int i = 0 ; i < months.length ; i++) {
        	
            if(months[i].equals(monthBirthString)) {
            	
                monthBirth = i;
            }
        }
        return monthBirth;
    }
    
    public String getMonthBirthStringBaby() {
    	
        return birth.split("/")[1];
    }
    
    public int getYearBirthBaby() {
    	
        return Integer.parseInt(birth.split("/")[2]);
    }
    
    public int getAgeInMonths() {
    	
        Date d = new Date();
        int monthCurrent = d.getMonth();
        if(d.getDate() < getDayBirthBaby()) {
        	
            monthCurrent--;
        }
        
        return (12 * ((d.getYear() + 1900) - getYearBirthBaby())) + (monthCurrent - getMonthBirthBaby());
    }
    
}