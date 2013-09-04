package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.R.id;
import gecko.mybaby.R.layout;
import gecko.mybaby.R.string;
import gecko.mybaby.model.Vaccine;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AddVaccineActivity extends Activity {
	
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;

	private EditText vaccineName;
	private EditText vaccineMonth;
	private EditText vaccineDose;
	private EditText vaccineDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_vaccine);
		
		this.getReferences();
		
		this.updateActivityDecoration();
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));

		this.vaccineName = ((EditText) this.findViewById(R.id.vaccine_name));
		this.vaccineMonth= ((EditText) this.findViewById(R.id.vaccine_month));
		this.vaccineDose = ((EditText) this.findViewById(R.id.vaccine_dose));
		this.vaccineDescription = ((EditText) this.findViewById(R.id.vaccine_description));
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
	
	public void saveVaccineClicked(View view) {

		String name = this.vaccineName.getText().toString();
		String monthStr = this.vaccineMonth.getText().toString();
		String dose = this.vaccineDose.getText().toString();
		String description = this.vaccineDescription.getText().toString();
		
		if (name.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.vaccine_name_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		if (monthStr.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.vaccine_month_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		if (dose.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.vaccine_dose_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		
		int month = Integer.parseInt(monthStr);
		
		Vaccine vaccine = new Vaccine(name, dose, month, description, false);
		VaccinesActivity.instance.addVaccine(vaccine);
		
		this.finish();
	}

}
