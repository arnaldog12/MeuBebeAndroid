package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HistoricActivity extends Activity {
    
    private static HistoricActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.historic.
        setContentView(R.layout.historic);
        
        HistoricActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadAddHistoric(View v) {
    	
        Intent openAddHistoric = new Intent(this, AddHistoricActivity.class);
        this.startActivity(openAddHistoric);
    }
    
    public void loadGraphics(View v) {
    	
        finish();
    }
    
    public void loadVaccines(View v) {
    	
        finish();
        
        GraphicsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadVaccines(null);
    }
    
    public void loadMain(View v) {
    	
        finish();
        
        GraphicsActivity.getInstance().finish();
    }
    
    public void loadProgress(View v) {
    	
        finish();
        
        GraphicsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadProgress(null);
    }
    
    public void loadTips(View v) {
    	
        finish();
        
        GraphicsActivity.getInstance().finish();
        MyBabyActivity.getInstance().loadTips(null);
    }
    
    public static HistoricActivity getInstance() {
    	
        return HistoricActivity.activity;
    }
    
}