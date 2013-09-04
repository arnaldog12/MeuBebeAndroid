package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.R.id;
import gecko.mybaby.R.layout;
import gecko.mybaby.R.string;
import gecko.mybaby.exceptions.MeasureUpdateException;
import gecko.mybaby.model.WeightHeight;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AddHistoricActivity extends Activity {

	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;

	private EditText ageValue;
	private EditText weightValue;
	private EditText heightValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_historic);
		
		this.getReferences();
		
		this.updateActivityDecoration();
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));

		this.ageValue = (EditText) this.findViewById(R.id.age_value);
		this.heightValue = (EditText) this.findViewById(R.id.height_value);
		this.weightValue = (EditText) this.findViewById(R.id.weight_value);
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
	
	public void saveHistoricClicked(View view) {

		String ageStr = this.ageValue.getText().toString();
		String weightStr = this.weightValue.getText().toString();
		String heightStr = this.heightValue.getText().toString();

		if (ageStr.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.historic_age_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		if (weightStr.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.historic_weight_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		if (heightStr.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.historic_height_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		
		int age;
		double weight;
		double height;
		
		WeightHeight wh = null;
		
		try {

			age = Integer.parseInt(ageStr);
			weight = Double.parseDouble(weightStr);
			height = Double.parseDouble(heightStr);
			
			wh = new WeightHeight(age, weight, height);

			HistoricActivity.instance.addMeasure(wh);
		} catch (NumberFormatException exception) {
			
			exception.printStackTrace();
		} catch (MeasureUpdateException exception) {
			
			exception.printStackTrace();
		}
		
		this.finish();
	}

}