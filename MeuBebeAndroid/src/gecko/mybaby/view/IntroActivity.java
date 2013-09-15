/*
 * @author GeckoApps Team
 */
package gecko.mybaby.view;

import gecko.mybaby.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends Activity {
    
    public static Activity instance;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_intro);
        
        IntroActivity.instance = this;
        
        new IntroTask().execute((Void[]) null);
    }
    
    public void loadMain(View v) {
    	
        this.finish();
        
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
    }
    
    class IntroTask extends AsyncTask<Void, Void, Void> {
    	
        private static final int SLEEP_TIME = 2000;
        
        @Override
        protected Void doInBackground(Void... arg0) {
        	
            try {
            	
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
            	
                ;
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(Void a) {
        	
            ((IntroActivity) IntroActivity.instance).loadMain(null);
        }
        
    }
    
}