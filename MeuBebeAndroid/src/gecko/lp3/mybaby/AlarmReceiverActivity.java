package gecko.lp3.mybaby;

import gecko.lp3.meubebe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AlarmReceiverActivity extends Activity {
    
    private static AlarmReceiverActivity activity = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // Sets the view to R.layout.alarm.
        setContentView(R.layout.alarm);
        
        AlarmReceiverActivity.activity = this;
    }
    
    @Override
    public void onBackPressed() {
    	
        loadMain(null);
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public void loadGraphics(View v) {
    	
        Toast.makeText(this, "Escolha um bebê na tela principal para ver os Gráficos e Históricos correspondentes.", Toast.LENGTH_LONG).show();
    }
    
    public void loadVaccines(View v) {
    	
        Toast.makeText(this, "Escolha um bebê na tela principal para ver as Vacinas correspondentes.", Toast.LENGTH_LONG).show();
    }
    
    public void loadMain(View v) {
    	
        finish();
        
        Intent openMain = new Intent(this, MyBabyActivity.class);
        this.startActivity(openMain);
    }
    
    public void loadProgress(View v) {
    	
        Toast.makeText(this, "Escolha um bebê na tela principal para ver os Progressos correspondentes.", Toast.LENGTH_LONG).show();
    }
    
    public void loadTips(View v) {
    	
        Toast.makeText(this, "Escolha um bebê na tela principal para ver as Dicas.", Toast.LENGTH_LONG).show();
    }
    
    public static AlarmReceiverActivity getInstance() {
    	
        return AlarmReceiverActivity.activity;
    }
    
}