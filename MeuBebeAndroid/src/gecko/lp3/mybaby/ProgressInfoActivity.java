package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ProgressInfoActivity extends Activity {
    
    private static ProgressInfoActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.progress_info.
        setContentView(R.layout.progress_info);
        
        ProgressInfoActivity.activity = this;
    }
    
    @Override
    public void onBackPressed() {
    	
        finish();
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadGraphics(View v) {
    	
        finish();
        
        ProgressActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadGraphics(null);
    }
    
    public void loadVaccines(View v) {
        finish();
        ProgressActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadVaccines(null);
    }
    
    public void loadMain(View v) {
    	
        finish();
        
        ProgressActivity.getInstance().finish();
    }
    
    public void loadProgress(View v) {
    	
        finish();
    }
    
    public void loadTips(View v) {
    	
        finish();
        
        ProgressActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadTips(null);
    }
    
    public static ProgressInfoActivity getInstance() {
    	
        return ProgressInfoActivity.activity;
    }
    
}