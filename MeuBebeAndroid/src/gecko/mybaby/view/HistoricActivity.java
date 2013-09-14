package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.controller.HistoricController;
import gecko.mybaby.exceptions.MeasureUpdateException;
import gecko.mybaby.model.Historic;
import gecko.mybaby.model.WeightHeight;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HistoricActivity extends Activity {

	public static HistoricActivity instance = null;
	
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;
	
	private ListView listView;
	
	private Historic historic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_historic);
		
		HistoricActivity.instance = this;
		
		this.getReferences();
		
		this.updateActivityDecoration();
		
		this.getHistoricForSelectedBaby();
		
		this.listView.setAdapter(new HistoricAdapter(this, this.historic));
	}
	
	private void getHistoricForSelectedBaby() {
		
		int id = ((MyBabyActivity) MyBabyActivity.instance).getSelectedBaby().getId();
		
		HistoricController controller = new HistoricController(this);
		this.historic = controller.getHistoricPerBaby(id);
	}

	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));
		
		this.listView = ((ListView) this.findViewById(R.id.list_view));
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
	
	public void addMeasure(WeightHeight wh) throws MeasureUpdateException {
		
		HistoricController controller = new HistoricController(this);
		controller.addHistoric(wh, ((MyBabyActivity) MyBabyActivity.instance).getSelectedBaby().getId());
		
		this.getHistoricForSelectedBaby();
		
		this.listView.setAdapter(new HistoricAdapter(this, this.historic));
	}
	
	public void addHistoricClicked(View view) {
		
		//Initiate AddHistoricActivity.
        Intent intent = new Intent(this, AddHistoricActivity.class);
        this.startActivity(intent);
	}
	
	private class HistoricAdapter extends ArrayAdapter<WeightHeight> {
		
		public HistoricAdapter(Activity activity, Historic historic) {
			
			super(activity, 0, historic.getMeasures());
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			WeightHeight wh = this.getItem(position);
			
			boolean imperial = false;
			String metricSystem = PreferenceManager.getDefaultSharedPreferences(HistoricActivity.this)
					.getString(HistoricActivity.this.getResources().getString(R.string.metric_system), "SI");
			
			if (!metricSystem.equals("SI")) {
				
				imperial = true;
				WeightHeight wh2 = new WeightHeight(wh.getMonth(), (wh.getWeight() * WeightHeight.KG_TO_POUND_FACTOR),
						wh.getHeight() * WeightHeight.CM_TO_FEET_FACTOR);
				wh = wh2;
			}
			
			RelativeLayout layout = (RelativeLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.historic, null);

			TextView age = (TextView) layout.findViewById(R.id.age_value);
			TextView weight = (TextView) layout.findViewById(R.id.weight_value);
			TextView height = (TextView) layout.findViewById(R.id.height_value);
			TextView bmi = (TextView) layout.findViewById(R.id.bmi_value);
			
			age.setBackgroundResource(MyBabyActivity.getBackgroundDivider());
			
			age.setText(this.getAgeStr(wh.getMonth()));
			weight.setText(String.valueOf(wh.getWeight()) + ((imperial) ? (" libras") : (" kg")));
			height.setText(String.valueOf(wh.getHeight()) + ((imperial) ? (" pés") : (" cm")));
			bmi.setText(String.valueOf(wh.getBMI()));
			
			boolean showPrefs = PreferenceManager.getDefaultSharedPreferences(HistoricActivity.this)
					.getBoolean(HistoricActivity.this.getResources().getString(R.string.show_imc), false);
			
			if (!showPrefs) {
				
				bmi.setVisibility(View.GONE);
				((TextView) layout.findViewById(R.id.bmi_label)).setVisibility(View.GONE);
			}
			
			return layout;
		}
	    
	    private String getAgeStr(int age) {
	    	
	    	String ageStr;
	    	if (age < 12) {
	        	
	    		ageStr = ((age == 1) ? "1 mês" : (age + " meses"));
	        } else if (age < 24) {
	        	
	            if (age == 12) {
	            	
	            	ageStr = "1 ano";
	            } else {
	            	
	            	ageStr = "1 ano e " + (((age % 12) == 1) ? "1 mês" : ((age % 12) + " meses"));
	            }
	        } else {
	        	
	            if ((age % 12) == 0) {
	            	
	            	ageStr = (age / 12) + " anos";
	            } else {
	            	
	            	ageStr = (age / 12) + " anos e " + (((age % 12) == 1) ? "1 mês" : ((age % 12) + " meses"));
	            }
	        }
	    	
	    	return ageStr;
	    }
		
	}
	
	public void graphicsClicked(View view) {

		HistoricActivity.instance.finish();
	}
	
	public void vaccinesClicked(View view) {
		
		HistoricActivity.instance.finish();
		GraphicsActivity.instance.finish();
		
		//Initiate VaccinesActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, VaccinesActivity.class);
	    this.startActivity(intent);
	}
	
	public void myBabyClicked(View view) {
		
		HistoricActivity.instance.finish();
		GraphicsActivity.instance.finish();
	}
	
	public void progressClicked(View view) {
		
		HistoricActivity.instance.finish();
		GraphicsActivity.instance.finish();
		
		//Initiate ProgressActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, ProgressActivity.class);
	    this.startActivity(intent);
	}
	
	public void tipsClicked(View view) {
		
		HistoricActivity.instance.finish();
		GraphicsActivity.instance.finish();
		
		//Initiate TipsActivity.
        Intent intent = new Intent(MyBabyActivity.instance, TipsActivity.class);
        this.startActivity(intent);
	}
	
}