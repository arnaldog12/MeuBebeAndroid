package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyBabyActivity extends Activity {
    
    private static MyBabyActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the view to R.layout.my_baby.
        setContentView(R.layout.my_baby);
        
        MyBabyActivity.activity = this;
    }
    
    public void loadAddBaby(View v) {
    	
        Intent openAddBaby = new Intent(this, AddBabyActivity.class);
        this.startActivity(openAddBaby);
    }
    
    public void loadEditBaby(View v) {
    	
        Intent openEditBaby = new Intent(this, EditBabyActivity.class);
        this.startActivity(openEditBaby);
    }
    
    public void loadGraphics(View v) {
    	
        Intent openGraphics = new Intent(this, GraphicsActivity.class);
        this.startActivity(openGraphics);
    }
    
    public void loadVaccines(View v) {
    	
        Intent openVaccines = new Intent(this, VaccinesActivity.class);
        this.startActivity(openVaccines);
    }
    
    public void loadMain(View v) {
        
    }
    
    public void loadProgress(View v) {
    	
        Intent openProgress = new Intent(this, ProgressActivity.class);
        this.startActivity(openProgress);
    }
    
    public void loadTips(View v) {
    	
        Intent openTips = new Intent(this, TipsActivity.class);
        this.startActivity(openTips);
    }
    
    public static MyBabyActivity getInstance() {
    	
        return MyBabyActivity.activity;
    }

}