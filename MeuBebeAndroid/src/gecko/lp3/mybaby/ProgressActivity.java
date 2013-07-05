package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProgressActivity extends Activity {
    
    private static ProgressActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.progress.
        setContentView(R.layout.progress);
        
        ProgressActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadProgressInfo() {
    	
        Intent openProgressInfo = new Intent(this, ProgressInfoActivity.class);
        this.startActivity(openProgressInfo);
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
        
    }
    
    public void loadTips(View v) {
    	
        finish();
        
        MyBabyActivity.getInstance().loadTips(null);
    }
    
    public static ProgressActivity getInstance() {
    	
        return ProgressActivity.activity;
    }
    
}