package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TipInfoActivity extends Activity {
    
    private static TipInfoActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.tips_info.
        setContentView(R.layout.tip_info);
        
        TipInfoActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadGraphics(View v) {
    	
        finish();
        
        TipsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadGraphics(null);
    }
    
    public void loadVaccines(View v) {
    	
        finish();
        
        TipsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadVaccines(null);
    }
    
    public void loadMain(View v) {
    	
        finish();
        
        TipsActivity.getInstance().finish();
    }
    
    public void loadProgress(View v) {
    	
        finish();
        
        TipsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadProgress(null);
    }
    
    public void loadTips(View v) {
    	
        finish();
    }
    
    public static TipInfoActivity getInstance() {
    	
        return TipInfoActivity.activity;
    }
    
}