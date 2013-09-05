package gecko.mybaby.view;

import gecko.mybaby.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyBabyPreferencesActivity extends PreferenceActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.layout.activity_preferences);
    }
	
}