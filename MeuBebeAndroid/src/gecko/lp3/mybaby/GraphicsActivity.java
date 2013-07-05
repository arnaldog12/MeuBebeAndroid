package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GraphicsActivity extends Activity {
    
    private static GraphicsActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.graphics.
        setContentView(R.layout.graphics);
        
        GraphicsActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadHistoric(View v) {
    	
        Intent openHistoric = new Intent(this, HistoricActivity.class);
        this.startActivity(openHistoric);
    }
    
    public void loadGraphics(View v) {
        
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
    
    public static GraphicsActivity getInstance() {
    	
        return GraphicsActivity.activity;
    }
    
}