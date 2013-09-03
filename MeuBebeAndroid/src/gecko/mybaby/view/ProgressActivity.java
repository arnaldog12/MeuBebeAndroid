package gecko.mybaby.view;

import gecko.lp3.mybaby.TipsActivity;
import gecko.mybaby.R;
import gecko.mybaby.controller.VaccineController;
import gecko.mybaby.model.Vaccine;
import gecko.mybaby.view.custom.Page;
import gecko.mybaby.view.custom.VaccinesPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProgressActivity extends Activity {
	
	public static ProgressActivity instance;
	
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
		
		LinearLayout progress_root = (LinearLayout) findViewById(R.id.progess_root);
		
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
				
				root_mes.addView(question_root);
			}
			
			progress_root.addView(root_mes);
		}
		
		this.getReferences();
		
		this.updateActivityDecoration();
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
	
	public void graphicsClicked(View v) {
		
		Intent inte = new Intent(this, GraphicsActivity.class);
		this.startActivity(inte);
	}
	
	public void vaccinesClicked(View v) {
		
		Intent inte = new Intent(this, VaccinesActivity.class);
		this.startActivity(inte);
	}
	
	public void myBabyClicked(View v) {
		
		Intent inte = new Intent(this, MyBabyActivity.class);
		this.startActivity(inte);
	}
	
	public void tipsClicked(View v) {
		
		Intent inte = new Intent(this, TipsActivity.class);
		this.startActivity(inte);
	}
}