package gecko.mybaby.model;

import gecko.mybaby.exceptions.MeasureUpdateException;

import java.util.ArrayList;
import java.util.Collections;

public class Historic {
	
	private static final int HISTORIC_LIST_SIZE = 64;

	private ArrayList<WeightHeight> measures;
	
	public Historic() {
		
		this.measures = new ArrayList<WeightHeight>(Historic.HISTORIC_LIST_SIZE);
	}
	
	public ArrayList<WeightHeight> getMeasures() {
		
		return this.measures;
	}
	
	public WeightHeight getMeasuresByMonth(int month) {
		
		int index = this.measures.indexOf(month);
		
		if (index == -1) {
			
			return null;
		} else {
			
			return this.measures.get(index);
		}
	}
	
	public void addMeasure(WeightHeight measure) throws MeasureUpdateException {
		
		if (!this.measures.contains(measure)) {
			
			this.measures.add(measure);
			
			Collections.sort(this.measures);
		} else {
			
			throw new MeasureUpdateException(measure.month);
		}
	}
	
}