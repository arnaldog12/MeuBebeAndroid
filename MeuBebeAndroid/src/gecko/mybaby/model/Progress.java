package gecko.mybaby.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Progress implements Serializable {
	
	private static final long serialVersionUID = -1754735949745824068L;

	public static final int PROGRESS_LIST_SIZE = 64;
	
	private ArrayList<Boolean> progress;

	public Progress() {
		
		this.progress = new ArrayList<Boolean>(Progress.PROGRESS_LIST_SIZE);
		for (int i = 0; i < PROGRESS_LIST_SIZE; i++) {
			
			this.progress.add(Boolean.FALSE);
		}
	}
	
	public Progress(ArrayList<Boolean> progress) {
		
		this.progress = progress;
	}
	
	public Boolean getProgressAt(int index) {
		
		return this.progress.get(index);
	}
	
	public ArrayList<Boolean> getProgress() {
		
		return this.progress;
	}
}