/*
 * @author GeckoApps Team
 */
package gecko.mybaby.model;

public class TakenVaccine {
    
    private int id;
    private int month;
    
    public TakenVaccine(int id, int month) {
    	
        this.id = id;
        this.month = month;
    }
    
    public int getId() {
    	
        return this.id;
    }
    
    public void setId(int id) {
    	
        this.id = id;
    }
    
    public int getMonth() {
    	
        return this.month;
    }
    
    public void setMonth(int month) {
    	
        this.month = month;
    }
    
    @Override
    public boolean equals(Object vt) {
    	
        if (vt instanceof TakenVaccine) {
        	
            if (((TakenVaccine) vt).getId() == this.id) {
            	
                return true;
            }
        }
        if (vt instanceof Integer) {
        	
            if (((Integer) vt).equals(this.id)) {
            	
                return true;
            }
        }
        if (vt instanceof Vaccine) {
        	
            if (((Vaccine) vt).getId() == this.id) {
            	
                return true;
            }
        }
        
        return false;
    }
    
}