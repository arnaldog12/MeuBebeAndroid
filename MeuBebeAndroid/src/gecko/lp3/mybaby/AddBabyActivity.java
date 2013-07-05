package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import gecko.lp3.mybaby.model.Baby;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AddBabyActivity extends Activity {
    
    public static final int BACKGROUND_ANIMATION_TIME = 250;
    
    public static final int IMAGE_CAM = 1;
    public static final int IMAGE_GALLERY = 2;
	
    private static AddBabyActivity activity;
    
    private short sexAddBaby = Baby.SEX_UNKNOWN;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.add_baby.
        setContentView(R.layout.add_baby);
        
        AddBabyActivity.activity = this;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode != RESULT_OK) {
        	
            return;
        }
        if(requestCode == IMAGE_CAM) {
        	
            openFromCamera();
        } else if(requestCode == IMAGE_GALLERY) {
        	
            openFromFile(data);
        }
    }
    
    public void malePressedAddBaby(View v) {
    	
        Button masc = (Button) findViewById(R.id.buttonMaleAddBaby);
        Button femin = (Button) findViewById(R.id.buttonFemaleAddBaby);
        
        RelativeLayout background = (RelativeLayout) findViewById(R.id.layout);
        LinearLayout tabBar = (LinearLayout) findViewById(R.id.tabBar);
        LinearLayout navigationBar = (LinearLayout) findViewById(R.id.navigationBar);
        
        if(this.sexAddBaby != Baby.SEX_BOY) {
        	
            ((TransitionDrawable) masc.getBackground()).startTransition(BACKGROUND_ANIMATION_TIME);
            
            if(this.sexAddBaby == Baby.SEX_GIRL) {
            	
                ((TransitionDrawable) femin.getBackground()).reverseTransition(BACKGROUND_ANIMATION_TIME);
            }
            
            background.setBackgroundResource(R.drawable.background_boy);
            tabBar.setBackgroundResource(R.drawable.tab_bar_boy);
            navigationBar.setBackgroundResource(R.drawable.navigation_bar_boy);
            
            this.sexAddBaby = Baby.SEX_BOY;
        } else {
        	
            ((TransitionDrawable) masc.getBackground()).reverseTransition(BACKGROUND_ANIMATION_TIME);
            
            background.setBackgroundResource(R.drawable.background_neutral);
            tabBar.setBackgroundResource(R.drawable.tab_bar_neutral);
            navigationBar.setBackgroundResource(R.drawable.navigation_bar_neutral);
            
            this.sexAddBaby = Baby.SEX_UNKNOWN;
        }
    }
    
    public void femalePressedAddBaby(View v) {
    	
        Button masc = (Button) findViewById(R.id.buttonMaleAddBaby);
        Button femin = (Button) findViewById(R.id.buttonFemaleAddBaby);
        
        RelativeLayout background = (RelativeLayout) findViewById(R.id.layout);
        LinearLayout tabBar = (LinearLayout) findViewById(R.id.tabBar);
        LinearLayout navigationBar = (LinearLayout) findViewById(R.id.navigationBar);
        
        if(this.sexAddBaby != Baby.SEX_GIRL) {
        	
            ((TransitionDrawable) femin.getBackground()).startTransition(BACKGROUND_ANIMATION_TIME);

            if(this.sexAddBaby == Baby.SEX_BOY) {
            	
                ((TransitionDrawable) masc.getBackground()).reverseTransition(BACKGROUND_ANIMATION_TIME);
            }
            
            background.setBackgroundResource(R.drawable.background_girl);
            tabBar.setBackgroundResource(R.drawable.tab_bar_girl);
            navigationBar.setBackgroundResource(R.drawable.navigation_bar_girl);
            
            this.sexAddBaby = Baby.SEX_GIRL;
        } else {
        	
            ((TransitionDrawable) femin.getBackground()).reverseTransition(BACKGROUND_ANIMATION_TIME);
            
            background.setBackgroundResource(R.drawable.background_neutral);
            tabBar.setBackgroundResource(R.drawable.tab_bar_neutral);
            navigationBar.setBackgroundResource(R.drawable.navigation_bar_neutral);
            
            this.sexAddBaby = Baby.SEX_UNKNOWN;
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
    
    public static AddBabyActivity getInstance() {
    	
        return AddBabyActivity.activity;
    }
    
}
