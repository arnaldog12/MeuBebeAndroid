package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class AddVaccineActivity extends Activity {
    
    private static AddVaccineActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.add_vaccine.
        setContentView(R.layout.add_vaccine);
        
        AddVaccineActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void infoAddVaccinePressed(View v) {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.info_adicionar_vacina)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            
        	@Override
            public void onClick(DialogInterface dialog, int id) {
            	
                dialog.cancel();
            }
            
        });
        
        builder.create().show();
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
    
    public static AddVaccineActivity getInstance() {
    	
        return AddVaccineActivity.activity;
    }
    
}