/*
 * @author GeckoApps Team
 */
package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends Activity {
    
    private static IntroActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.intro.
        setContentView(R.layout.intro);
        
        IntroActivity.activity = this;
        
        new IntroTask().execute((Integer[]) null);
    }
    
    public void loadMain(View v) {
    	
        finish();
        
        Intent openMain = new Intent(this, MyBabyActivity.class);
        this.startActivity(openMain);
    }
    
    class IntroTask extends AsyncTask<Integer, Integer, Integer> {
    	
        private static final int SLEEP_TIME = 2000;
        
        @Override
        protected Integer doInBackground(Integer... arg0) {
        	
            try {
            	
                Thread.sleep(SLEEP_TIME);
            } catch(InterruptedException e) {
            	
                ;
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(Integer a) {
        	
            IntroActivity.getInstance().loadMain(null);
        }
        
    }
    
    public static IntroActivity getInstance() {
    	
        return IntroActivity.activity;
    }
    
}