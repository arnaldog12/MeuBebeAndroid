package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AddHistoricActivity extends Activity {
    
    private static AddHistoricActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.add_historic.
        setContentView(R.layout.add_historic);
        
        AddHistoricActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadGraphics(View v) {
    	
        finish();
        
        HistoricActivity.getInstance().finish();
    }
    
    public void loadVaccines(View v) {
    	
        finish();
        
        HistoricActivity.getInstance().finish();
        GraphicsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadVaccines(null);
    }
    
    public void loadMain(View v) {
    	
        finish();
        
        HistoricActivity.getInstance().finish();
        GraphicsActivity.getInstance().finish();
    }
    
    public void loadProgress(View v) {
    	
        finish();
        
        HistoricActivity.getInstance().finish();
        GraphicsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadProgress(null);
    }
    
    public void loadTips(View v) {
    	
        finish();
        
        HistoricActivity.getInstance().finish();
        GraphicsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadTips(null);
    }
    
    public static AddHistoricActivity getInstance() {
    	
        return AddHistoricActivity.activity;
    }
    
}