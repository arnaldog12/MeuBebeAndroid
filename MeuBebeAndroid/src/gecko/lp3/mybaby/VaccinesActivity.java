package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VaccinesActivity extends Activity {
	
    private static VaccinesActivity activity;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the current View to R.layout.vaccines.
        setContentView(R.layout.vaccines);
        
        VaccinesActivity.activity = this;
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void allVaccinesPressed(View v) {
    	
        ((Button) findViewById(R.id.buttonAllVaccines)).setBackgroundResource(R.drawable.button_selection_left_on);
        ((Button) findViewById(R.id.buttonTakenVaccines)).setBackgroundResource(R.drawable.button_selection_middle_off);
        ((Button) findViewById(R.id.buttonReminders)).setBackgroundResource(R.drawable.button_selection_right_off);
    }
    
    public void takenVaccinesPressed(View v) {
    	
        ((Button) findViewById(R.id.buttonAllVaccines)).setBackgroundResource(R.drawable.button_selection_left_off);
        ((Button) findViewById(R.id.buttonTakenVaccines)).setBackgroundResource(R.drawable.button_selection_middle_on);
        ((Button) findViewById(R.id.buttonReminders)).setBackgroundResource(R.drawable.button_selection_right_off);
    }
    
    public void remindersPressed(View v) {
    	
        ((Button) findViewById(R.id.buttonAllVaccines)).setBackgroundResource(R.drawable.button_selection_left_off);
        ((Button) findViewById(R.id.buttonTakenVaccines)).setBackgroundResource(R.drawable.button_selection_middle_off);
        ((Button) findViewById(R.id.buttonReminders)).setBackgroundResource(R.drawable.button_selection_right_on);
    }
    
    public void infoVaccinesPressed(View v) {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.info_tela_vacinas)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            
        	@Override
            public void onClick(DialogInterface dialog, int id) {
            	
                dialog.cancel();
            }
            
        });
        
        builder.create().show();
    }
    
    public void loadAddVaccine(View v) {
    	
        Intent openAddVaccine = new Intent(this, AddVaccineActivity.class);
        this.startActivity(openAddVaccine);
    }
    
    public void loadGraphics(View v) {
    	
        finish();
        
        MyBabyActivity.getInstance().loadGraphics(null);
    }
    
    public void loadVaccines(View v) {
        
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
    
    public static VaccinesActivity getInstance() {
    	
        return VaccinesActivity.activity;
    }

    
}