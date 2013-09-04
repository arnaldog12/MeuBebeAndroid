package gecko.mybaby.model;

public final class WeightHeight implements Comparable<WeightHeight> {

	protected int month;
	protected double weight;
	protected double height;
	
	public WeightHeight(int month, double weight, double height) {
		
		this.month = month;
		this.weight = weight;
		this.height = height;
	}
	
	public final int getMonth() {
		
		return this.month;
	}
	
	public final double getWeight() {
		
		return this.weight;
	}

	public final double getHeight() {
		
		return this.height;
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (object instanceof WeightHeight) {
			
			WeightHeight whObject = (WeightHeight) object;
			return (this.month == whObject.getMonth()); 
		} else if (object instanceof Integer) {
			
			Integer integerObject = (Integer) object;
			return (this.month == integerObject);
		}
		
		return false;
	}

	@Override
	public int compareTo(WeightHeight another) {
		
		if (this.month < another.getMonth()) {
			
			return -1;
		} else if (this.month > another.getMonth()) {
			
			return 1;
		} else {
			
			return 0;
		}
	}
}