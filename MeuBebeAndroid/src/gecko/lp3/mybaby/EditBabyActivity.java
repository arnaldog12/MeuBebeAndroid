package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EditBabyActivity extends Activity {
    
    public static final int IMAGE_CAM = 1;
    public static final int IMAGE_GALLERY = 2;
    
    private static EditBabyActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.add_baby.
        setContentView(R.layout.edit_baby);
        
        EditBabyActivity.activity = this;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode != RESULT_OK) {
        	
            return;
        }
        if(requestCode == EditBabyActivity.IMAGE_CAM) {
        	
            openFromCamera();
        } else if(requestCode == EditBabyActivity.IMAGE_GALLERY) {
        	
            openFromFile(data);
        }
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    private void openFromCamera() {
        
        Intent openImageAreaPicker = new Intent(this, ImageAreaPickerActivity.class);
        this.startActivity(openImageAreaPicker);
    }
    
    private void openFromFile(Intent data) {
        
        Intent openImageAreaPicker = new Intent(this, ImageAreaPickerActivity.class);
        this.startActivity(openImageAreaPicker);
    }
    
    public void loadGraphics(View v) {
    	
        finish();
        
        MyBabyActivity.getInstance().loadGraphics(null);
    }
    
    public void loadVaccines(View v) {
    	
        finish();
        
        MyBabyActivity.getInstance().loadVaccines(null);
    }
    
    public void loadMain(View v) {
    	
        finish();
    }
    
    public void loadProgress(View v) {
    	
        finish();
        
        MyBabyActivity.getInstance().loadProgress(null);
    }
    
    public void loadTips(View v) {
    	
        finish();
        
        MyBabyActivity.getInstance().loadTips(null);
    }
    
    public static EditBabyActivity getInstance() {
    	
        return activity;
    }
    
}