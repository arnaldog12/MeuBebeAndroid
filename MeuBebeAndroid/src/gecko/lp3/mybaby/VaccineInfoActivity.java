package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class VaccineInfoActivity extends Activity {
    
    private static VaccineInfoActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

    	// Sets the current View to R.layout.vaccine_info.
        setContentView(R.layout.vaccine_info_default);
        
        VaccineInfoActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadGraphics(View v) {
    	
        finish();
        
        VaccinesActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadGraphics(null);
    }
    
    public void loadVaccines(View v) {
    	
        finish();
    }
    
    public void loadMain(View v) {
    	
        finish();
        
        VaccinesActivity.getInstance().finish();
    }
    
    public void loadProgress(View v) {
    	
        finish();
        
        VaccinesActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadProgress(null);
    }
    
    public void loadTips(View v) {
    	
        finish();
        
        VaccinesActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadTips(null);
    }
    
    public static VaccineInfoActivity getInstance() {
    	
        return activity;
    }
    
}