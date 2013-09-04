/*
 * @author GeckoApps Team
 */
package gecko.mybaby.model;

public class Reminder {
	
	public static final int MAX_NUMBER = 8;

    private String date;
    private String time;
    private String message;
    private int babyId;
    private int vaccineId;
    private int reminderId;
    
    public Reminder(String date, String time, String message, int babyId, int vaccineId) {

        this.date = date;
        this.time = time;
        this.vaccineId = vaccineId;
        this.message = message;
        this.babyId = babyId;
    }
    
    public String getMessage() {
    	
        return this.message;
    }
    
    public void setMessage(String message) {
    	
        this.message = message;
    }
    
    public int getBabyId() {
    	
        return this.babyId;
    }
    
    public void setBabyId(int babyId) {
    	
        this.babyId = babyId;
    }
    
    public int getVaccineId() {
    	
        return this.vaccineId;
    }
    
    public void setVaccineId(int vaccineId) {
    	
        this.vaccineId = vaccineId;
    }
    
    public int getReminderId() {
    	
        return this.reminderId;
    }
    
    public void setReminderId(int reminderId) {
    	
        this.reminderId = reminderId;
    }
    
    public String getDate() {
    	
        return this.date;
    }
    
    public void setDate(String date) {
    	
        this.date = date;
    }
    
    public String getTime() {
    	
        return this.time;
    }
    
    public void setTime(String time) {
    	
        this.time = time;
    }
    
    @Override
    public boolean equals(Object reminder) {
    	
        if(reminder instanceof Reminder) {
        	
            if((((Reminder) reminder).getBabyId() == this.babyId) &&
            		(((Reminder) reminder).getVaccineId() == this.vaccineId)) {
            	
                return true;
            } else {
            	
                return false;
            }
        } else {
        	
            return false;
        }
    }
    
}