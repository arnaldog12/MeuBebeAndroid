package gecko.mybaby.view.custom;

import android.view.View;

public interface Page {

	String getTitle();
	
	View getCurrentView();
	
	boolean doRequest();
	
	void releaseViews();
	
}