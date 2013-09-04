package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.controller.BabyController;
import gecko.mybaby.controller.ReminderController;
import gecko.mybaby.model.Baby;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlarmReceiverActivity extends Activity {
    
    private static AlarmReceiverActivity activity = null;
    private MediaPlayer mediaPlayer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.alarm);
        
        String message = this.getIntent().getExtras().getString("message");
        String vaccineId = this.getIntent().getExtras().getString("vaccine_id");
        String babyId = this.getIntent().getExtras().getString("baby_id");
        String reminderId = this.getIntent().getExtras().getString("reminder_id");
        
        ReminderController rc = new ReminderController(this);
        rc.removeReminder(Integer.parseInt(babyId), Integer.parseInt(vaccineId), Integer.parseInt(reminderId));
        
        ((EditText) findViewById(R.id.textViewVaccineInfo)).setText(message);
        
        finishActivities();
        
        playSound(this, getAlarmUri());
        
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        
        BabyController bc = new BabyController(this);
        int sex = bc.getBabyPerId(Integer.parseInt(babyId)).getGender();
        
        if (sex == Baby.GENDER_GIRL) {
        	
//            findViewById(R.id.layout).setBackgroundResource(R.drawable.background_girl);
//            findViewById(R.id.tabBar).setBackgroundResource(R.drawable.tab_bar_girl);
//            findViewById(R.id.navigationBar).setBackgroundResource(R.drawable.navigation_bar_girl);
        } else if (sex == Baby.GENDER_BOY) {
        	
//            findViewById(R.id.layout).setBackgroundResource(R.drawable.background_boy);
//            findViewById(R.id.tabBar).setBackgroundResource(R.drawable.tab_bar_boy);
//            findViewById(R.id.navigationBar).setBackgroundResource(R.drawable.navigation_bar_boy);
        }
    }
    
    @Override
    public void onBackPressed() {
    	
        loadMain(null);
    }
    
    public void finish(View v) {
    	
        finish();
    }
    
    public MediaPlayer getMediaPlayer() {
    	
        return mediaPlayer;
    }
    
    private void playSound(Context context, Uri alert) {
    	
        mediaPlayer = new MediaPlayer();
        
        try {
        	
            mediaPlayer.setDataSource(context, alert);
            
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            
            if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            	
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch(IOException e) {
        	
            ;
        }
    }
    
    //Get an alarm sound. Try for an alarm. If none set, try notification, otherwise, ringtone.
    private Uri getAlarmUri() {
    	
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        
        if(alert == null) {
        	
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if(alert == null) {
            	
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        
        return alert;
    }
    
    public void stopAlarmPressed(View v) {
    	
        if(((Button) v).getText().toString().equals("Parar Alarme")) {
        	
            mediaPlayer.stop();
            
            ((Button) v).setText("Meu Bebê");
        } else {
        	
            loadMain(v);
        }
    }
    
    private void finishActivities() {
    	
//        if(AddBabyActivity.getInstance() != null) {
//        	
//            AddBabyActivity.getInstance().finish();
//        }
//        if(AddHistoricActivity.getInstance() != null) {
//        	
//            AddHistoricActivity.getInstance().finish();
//        }
//        if(AddVaccineActivity.getInstance() != null) {
//        	
//            AddVaccineActivity.getInstance().finish();
//        }
//        if(AlarmReceiverActivity.getInstance() != null) {
//        	
//            AlarmReceiverActivity.getInstance().finish();
//        }
//        if(EditBabyActivity.getInstance() != null) {
//        	
//            EditBabyActivity.getInstance().finish();
//        }
//        if(GraphicsActivity.getInstance() != null) {
//        	
//            GraphicsActivity.getInstance().finish();
//        }
//        if(HistoricActivity.getInstance() != null) {
//        	
//            HistoricActivity.getInstance().finish();
//        }
//        if(ImageAreaPickerActivity.getInstance() != null) {
//        	
//            ImageAreaPickerActivity.getInstance().finish();
//        }
//        if(MyBabyActivity.getInstance() != null) {
//        	
//            MyBabyActivity.getInstance().finish();
//        }
//        if(ProgressActivity.getInstance() != null) {
//        	
//            ProgressActivity.getInstance().finish();
//        }
//        if(ProgressInfoActivity.getInstance() != null) {
//        	
//            ProgressInfoActivity.getInstance().finish();
//        }
//        if(TipInfoActivity.getInstance() != null) {
//        	
//            TipInfoActivity.getInstance().finish();
//        }
//        if(TipsActivity.getInstance() != null) {
//        	
//            TipsActivity.getInstance().finish();
//        }
//        if(VaccineInfoActivity.getInstance() != null) {
//        	
//            VaccineInfoActivity.getInstance().finish();
//        }
//        if(VaccinesActivity.getInstance() != null) {
//        	
//            VaccinesActivity.getInstance().finish();
//        }
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
    	
        return activity;
    }
    
}