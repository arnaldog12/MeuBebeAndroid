package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.controller.SQLiteHelper;
import gecko.mybaby.model.Baby;
import gecko.mybaby.model.Progress;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ProgressActivity extends Activity {
	
	public static ProgressActivity instance = null;
	
	private static UpdateRemoteProgress updating = null;
	
	private ArrayList<Boolean> new_progress = new ArrayList<Boolean>();
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;
	private LinkedHashMap<String, LinkedList<String> > questions = new LinkedHashMap<String, LinkedList<String>>(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_progress);
		
		ProgressActivity.instance = this;
		
		String[] values = getResources().getStringArray(R.array.questions);
		for (int i = 0; i < values.length; i += 2) {
			
			String question = values[i];
			String mes = values[i + 1];
			
			LinkedList<String> mes_questions =  questions.get(mes);
			if (mes_questions == null) {
				
				mes_questions = new LinkedList<String>();
				questions.put(mes, mes_questions);
			}
				
			mes_questions.add(question);			
		}
		
		this.getReferences();

		Baby baby = MyBabyActivity.instance.getSelectedBaby();
		SQLiteHelper helper = new SQLiteHelper(this);
		try {
			
			Log.d("MeuBebe", "Query PRO: " + "SELECT * FROM Progress WHERE babyID = " + baby.getId());
			helper.open();
			Cursor c = helper.executeQuery("SELECT * FROM Progress WHERE babyID = " + baby.getId());
			if (c.moveToFirst()) {
				
				String pg = c.getString(c.getColumnIndex("progress"));
				
				ArrayList<Boolean> valu = new ArrayList<Boolean>(Progress.PROGRESS_LIST_SIZE);
				
				for (int i = 0; i < pg.length(); i++) {
				
					if (pg.charAt(i) == '1') {
						
						valu.add(Boolean.TRUE);
					} else {
						
						valu.add(Boolean.FALSE);
					}
				}
				
				Log.d("MeuBebe", "DB Progress: " + pg);
				baby.setProgress(new Progress(valu));
			} else {
				
				Log.d("MeuBebe", "Error first");
			}
		} catch (Exception e) {
			
			Log.d("MeuBebe", "Error execet");
			e.printStackTrace();
		}
		
		for (int i = 0; i < Progress.PROGRESS_LIST_SIZE; i++) {
		
			this.new_progress.add(baby.getProgress().getProgressAt(i));
		}
		
		this.updateProgessView();
		
		this.updateActivityDecoration();
		
		this.saveRemote();
	}
	
	@Override
	protected void onPause() {

		super.onPause();
		
		Log.d("MeuBebe", "New progress len: " + new_progress.size());
		MyBabyActivity.instance.getSelectedBaby().setProgress(new Progress(new_progress));
		
		this.saveLocal();
		this.saveRemote();
	}
	
	private void saveLocal() {
		
		try {
			
			Baby baby = MyBabyActivity.instance.getSelectedBaby();
			StringBuilder sb = new StringBuilder();
			
			for (Boolean b : baby.getProgress().getProgress()) {
				
				if (b.equals(Boolean.TRUE)) {
					
					sb.append("1");
				} else {
					
					sb.append("0");
				}
			}
			
			SQLiteHelper helper = new SQLiteHelper(this);
			helper.open();
			Log.d("MeuBebe", "QUERY: " + "UPDATE Progress SET progress = '" + sb.toString() + "' WHERE babyID = " + baby.getId());
			helper.executeSQL("UPDATE Progress SET progress = '" + sb.toString() + "' WHERE babyID = " + baby.getId());
			
			helper.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	private void updateProgessView() {
		
		Baby baby = MyBabyActivity.instance.getSelectedBaby();
		
		LinearLayout progress_root = (LinearLayout) findViewById(R.id.progess_root);

		int question_index = 0;
		for (Entry<String, LinkedList<String> > s : this.questions.entrySet()) {
			
			Log.d("MeuBebe", "Mes: " + s.getKey());
			LinearLayout root_mes = (LinearLayout) View.inflate(this, R.layout.progess_mes_item, null);
			TextView tv = (TextView) root_mes.findViewById(R.id.progress_mes_header);
			tv.setBackgroundResource(MyBabyActivity.getBackgroundDivider());
			tv.setText(s.getKey() + " meses");
			
			LinkedList<String> mes_question = s.getValue();
			for (String ques : mes_question) {
				
				LinearLayout question_root = (LinearLayout) View.inflate(this, R.layout.progress_question_item, null);
				TextView question_tv = (TextView) question_root.findViewById(R.id.progress_question);
				question_tv.setText(ques);
				
				ToggleButton tg = (ToggleButton) question_root.findViewById(R.id.progress_toggle_button);
				tg.setTag(question_index);
				
				tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

						int pos = ((Integer) buttonView.getTag()).intValue();
						new_progress.set(pos, isChecked);
					}
				});
				tg.setChecked(baby.getProgress().getProgressAt(question_index).equals(Boolean.TRUE));
				
				root_mes.addView(question_root);
				question_index++;
			}
			
			progress_root.addView(root_mes);
		}
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
	
	public void remindersClicked(View view) {
		
		
	}
	
	public void graphicsClicked(View view) {

		ProgressActivity.instance.finish();
		
		//Initiate GraphicsActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, GraphicsActivity.class);
	    this.startActivity(intent);
	}
	
	public void vaccinesClicked(View view) {
		
		ProgressActivity.instance.finish();
		
		//Initiate VaccinesActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, VaccinesActivity.class);
	    this.startActivity(intent);
	}
	
	public void myBabyClicked(View view) {
		
		ProgressActivity.instance.finish();
	}
	
	public void progressClicked(View view) {
	}
	
	public void tipsClicked(View view) {
		
		ProgressActivity.instance.finish();
		
		//Initiate TipsActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, TipsActivity.class);
	    this.startActivity(intent);
	}
	
	private void saveRemote() {
		
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {

				synchronized (ProgressActivity.class) {
				
					if (ProgressActivity.updating != null) {
					
						ProgressActivity.updating.cancel(true);
					}
					
					ProgressActivity.updating = new UpdateRemoteProgress();
					ProgressActivity.updating.execute();
				}				
			}
		});
		
	}
	
	private class UpdateRemoteProgress extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			try {
				
				Baby baby = MyBabyActivity.instance.getSelectedBaby();
				StringBuilder sb = new StringBuilder("http://ws.geckoapps.com.br/editar-progresso.php?id=");
				sb.append(baby.getId()).append("&progresso=");
				
				for (Boolean b : baby.getProgress().getProgress()) {
					
					if (b.equals(Boolean.TRUE)) {
						
						sb.append("1");
					} else {
						
						sb.append("0");
					}
				}
				
				Log.d("MeuBebe", "URL Progress: " + sb.toString());
				
				HttpClient cli = new DefaultHttpClient();
				HttpGet get = new HttpGet(sb.toString());
				
				cli.execute(get);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			return null;
		}
	}
}