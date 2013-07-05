package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TipsActivity extends Activity {
    
    private static TipsActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.tips.
        setContentView(R.layout.tips);
        
        TipsActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
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
        
    }
    
    public static TipsActivity getInstance() {
        
    	return TipsActivity.activity;
    }
    
}