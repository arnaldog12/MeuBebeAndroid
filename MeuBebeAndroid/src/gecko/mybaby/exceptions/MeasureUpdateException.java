package gecko.mybaby.exceptions;

public class MeasureUpdateException extends Exception {
	
	private static final long serialVersionUID = -2746459318217938641L;
	
	private int month;
	
	public MeasureUpdateException(int month) {
		
		this.month = month;
	}
	
	public int getMonth() {
		
		return this.month;
	}
	
}