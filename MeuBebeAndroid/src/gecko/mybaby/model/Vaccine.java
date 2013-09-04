package gecko.mybaby.model;

import java.io.Serializable;

public class Vaccine implements Serializable {
	
	private static final long serialVersionUID = -6859948563376395360L;
	
	private int id;
    private String name;
    private String dose;
    private int month;
    private String description;
    private boolean def;
    
    public Vaccine( String name, String dose, int month,
    				String description, boolean def ) {

    	this.name = name;
    	this.dose = dose;
    	this.month = month;
    	this.description = description;
    	this.def = def;
    }
	
	public int getId() {
		
		return this.id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
    
	public String getName() {
		
		return this.name;
	}
	
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getDose() {
		
		return this.dose;
	}
	
	public void setDose(String dose) {
		
		this.dose = dose;
	}
	
	public int getMonth() {
		
		return this.month;
	}
	
	public void setMonth(int month) {
		
		this.month = month;
	}
	
	public String getDescription() {
		
		return this.description;
	}
	
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	public boolean isDefault() {
		
		return this.def;
	}
	
	public void setDefault(boolean def) {
		
		this.def = def;
	}
	
}