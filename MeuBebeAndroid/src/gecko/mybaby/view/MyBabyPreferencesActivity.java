package gecko.mybaby.view;

import gecko.mybaby.R;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyBabyPreferencesActivity extends PreferenceActivity {
	
	public static Activity instance = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.layout.activity_preferences);
        
        MyBabyPreferencesActivity.instance = this;
    }
	
}