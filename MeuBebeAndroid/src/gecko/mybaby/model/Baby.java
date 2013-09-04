/*
 * @author GeckoApps Team
 */
package gecko.mybaby.model;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Bitmap;

public class Baby implements Serializable {
    
	private static final long serialVersionUID = -4050782008947973979L;
	
	public static final int GENDER_GIRL = 0;
    public static final int GENDER_BOY = 1;
    public static final int GENDER_UNKNOWN = -1;
    
    private int id;
    private String name;
    private int gender;
    private String birth;
    private Historic historic;
    private Progress progress;
    private Bitmap image;
    
    public Baby(String name, String birth, int gender, Historic historic, Progress progress, Bitmap foto) {
    	
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.progress = progress;
        this.historic = historic;
        this.image = foto;
    }
    
    public String getName() {
    	
        return this.name;
    }
    
    public void setName(String name) {
    	
        this.name = name;
    }
    
    public String getBirth() {
    	
        return this.birth;
    }
    
    public void setBirth(String birth) {
    	
        this.birth = birth;
    }
    
    public int getGender() {
    	
        return this.gender;
    }
    
    public void setGender(int gender) {
    	
        this.gender = gender;
    }
    
    public Historic getHistoric() {
    	
    	return this.historic;
    }
    
    public void setHistoric(Historic historic) {
    	
    	this.historic = historic;
    }
    
    public Progress getProgress() {
    	
        return this.progress;
    }
    
    public void setProgress(Progress progress) {
    	
        this.progress = progress;
    }
    
    public int getId() {
    	
        return this.id;
    }
    
    public void setId(int id) {
    	
        this.id = id;
    }
    
    public Bitmap getImage() {
    	
        return this.image;
    }
    
    public void setImage(Bitmap image) {
    	
        this.image = image;
    }
    
    public String getAge() {
    	
    	int age = this.getAgeInMonths();
    	
    	String ageStr;
    	if (age < 12) {
        	
    		ageStr = ((age == 1) ? "1 mês" : (age + " meses"));
        } else if (age < 24) {
        	
            if (age == 12) {
            	
            	ageStr = "1 ano";
            } else {
            	
            	ageStr = "1 ano e " + (((age % 12) == 1) ? "1 mês" : ((age % 12) + " meses"));
            }
        } else {
        	
            if ((age % 12) == 0) {
            	
            	ageStr = (age / 12) + " anos";
            } else {
            	
            	ageStr = (age / 12) + " anos e " + (((age % 12) == 1) ? "1 mês" : ((age % 12) + " meses"));
            }
        }
    	
    	return ageStr;
    }
    
    public int getDayBirthBaby() {
    	
        return Integer.parseInt(this.birth.split("/")[0]);
    }
    
    private int getMonthBirthBaby() {
    	
        return Integer.parseInt(this.birth.split("/")[1]);
    }
    
    private int getYearBirthBaby() {
    	
        return Integer.parseInt(this.birth.split("/")[2]);
    }
    
    public int getAgeInMonths() {
    	
        Date date = new Date();
        int monthCurrent = date.getMonth() + 1;
        
        if (date.getDate() < this.getDayBirthBaby()) {
        	
            monthCurrent--;
        }
        
        return (12 * ((date.getYear() + 1900) - this.getYearBirthBaby())) +
        		(monthCurrent - this.getMonthBirthBaby());
    }
    
    @Override
    public boolean equals(Object object) {
    	
    	if (object instanceof Baby) {
    		
    		return (((Baby) object).getId() == this.getId());
    	}
    	return false;
    }
    
}