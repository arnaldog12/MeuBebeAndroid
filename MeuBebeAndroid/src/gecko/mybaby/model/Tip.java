package gecko.mybaby.model;

public class Tip {
	
	public String author;
	public String tip;
	public String category;
	
	public Tip(String author, String tip, String category) {
		
		this.author = author;
		this.tip = tip;
		this.category = category;
	}
	
	public void setAuthor(String author){
		
		this.author = author;
	}
	
	public String getAuthor(){
		
		return this.author;
	}
	
	public void setTip(String tip){
		
		this.tip = tip;
	}
	
	public String getTip(){
		
		return this.tip;
	}
	
	public void setCategory(String categoriy){
		
		this.category = categoriy;
	}
	
	public String getCategory(){
		
		return this.category;
	}
	
	@Override
	public String toString(){
		
		return String.format("%s \nDica Enviada por %s", this.tip, this.author);
	}

}