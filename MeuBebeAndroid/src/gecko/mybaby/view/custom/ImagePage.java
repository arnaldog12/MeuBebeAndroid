package gecko.mybaby.view.custom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class ImagePage implements Page {

	private Activity activity = null;
	private ImageView imageView = null;
	
	public ImagePage(Activity activity, Bitmap bitmap) {
		
		this.activity = activity;
		
		this.imageView = new ImageView(this.activity);
		this.imageView.setImageBitmap(bitmap);
	}
	
	public ImagePage(Activity activity, int resource) {
		
		this.activity = activity;
		
		this.imageView = new ImageView(this.activity);
		this.imageView.setImageResource(resource);
	}
	
	@Override
	public String getTitle() {

		return "Graphics Image"; 
	}

	@Override
	public View getCurrentView() {
		
		return this.imageView;
	}

	@Override
	public boolean doRequest() {
		
		return false;
	}

	@Override
	public void releaseViews() {
		
		if (this.imageView != null) {
			
			this.imageView = null;
		}
	}

}