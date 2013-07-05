package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ImageAreaPickerActivity extends Activity {

    private static ImageAreaPickerActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.add_baby.
        setContentView(R.layout.image_area_picker);
        
        ImageAreaPickerActivity.activity = this;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        getMenuInflater().inflate(R.menu.image_picker_menu, menu);
        return true;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public static ImageAreaPickerActivity getInstance() {
    	
        return ImageAreaPickerActivity.activity;
    }
    
}
