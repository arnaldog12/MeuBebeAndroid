package gecko.mybaby.view.custom;

import gecko.mybaby.R;
import gecko.mybaby.controller.TakenVaccineController;
import gecko.mybaby.model.TakenVaccine;
import gecko.mybaby.model.Vaccine;
import gecko.mybaby.view.MyBabyActivity;
import gecko.mybaby.view.VaccineDetailsActivity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class VaccinesPage implements Page {

	private Activity activity = null;
	private ListView listView = null;
	private List<List<Vaccine>> vaccines;

	public VaccinesPage(Activity activity, List<List<Vaccine>> vaccines) {
		
		this.activity = activity;
		this.vaccines = vaccines;

		this.listView = new ListView(this.activity);
		this.listView.setCacheColorHint(0);
		
		this.updateView();
	}
	
	private void updateView() {
		
		this.listView.setAdapter(new VaccinesAdapter(this.activity, this.vaccines));
	}
	
	public void updateList(List<List<Vaccine>> vaccines) {
		
		this.vaccines = vaccines;
		
		this.updateView();
	}
	
	@Override
	public String getTitle() {

		return "Vaccines Page"; 
	}

	@Override
	public View getCurrentView() {
		
		return this.listView;
	}

	@Override
	public boolean doRequest() {
		
		return false;
	}

	@Override
	public void releaseViews() {
		
		if (this.listView != null) {
			
			this.listView = null;
		}
	}
	
	private class VaccinesAdapter extends ArrayAdapter<List<Vaccine>> {
		
		private Activity activity;
		
		private List<TakenVaccine> takenVaccines;
		
		public VaccinesAdapter(Activity activity, List<List<Vaccine>> vaccines) {
			
			super(activity, 0, vaccines);
			
			this.activity = activity;
			
			TakenVaccineController controller = new TakenVaccineController(this.activity);
			this.takenVaccines = controller.takenVaccinesPerBaby(
					MyBabyActivity.instance.getSelectedBaby().getId());
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			List<Vaccine> vaccines = this.getItem(position);
			
			LinearLayout layout = (LinearLayout) LayoutInflater.from(this.getContext())
					.inflate(R.layout.vaccines, null);
			
			TextView age = (TextView) layout.findViewById(R.id.age_value);
			LinearLayout vaccinesLayout = (LinearLayout) layout.findViewById(R.id.vaccines_layout);
			
			String ageStr;
			if (vaccines.get(0).getMonth() == 0) {
				
				ageStr = "Recém Nascido";
			} else {
				
				ageStr = this.getAgeStr(vaccines.get(0).getMonth());
			}
			age.setBackgroundResource(MyBabyActivity.getBackgroundDivider());
			
			age.setText(ageStr);
			for (Vaccine vaccine : vaccines) {
				
				LinearLayout innerLayout =
						(LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.vaccine, null);
				
				CheckBox vaccineName = (CheckBox) innerLayout.findViewById(R.id.vaccine_name);
				ImageButton vaccineInfo = (ImageButton) innerLayout.findViewById(R.id.vaccine_info);
				
				vaccineName.setText(vaccine.getName() + " - " + vaccine.getDose());
				if (this.takenVaccines.contains(new TakenVaccine(vaccine.getId(), vaccine.getMonth()))) {
					
					vaccineName.setChecked(true);
				}
				vaccineName.setOnCheckedChangeListener(new VaccineCheckedListener(this.activity, vaccine));
				vaccineInfo.setOnClickListener(new VaccineClickListener(this.activity, vaccine));
				vaccinesLayout.addView(innerLayout);
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
	
	private class VaccineClickListener implements OnClickListener {
		
		private Activity activity; 
		private Vaccine vaccine;
		
		public VaccineClickListener(Activity activity, Vaccine vaccine) {
			
			this.activity = activity;
			this.vaccine = vaccine;
		}
		
		@Override
		public void onClick(View v) {

			//Initiate VaccineDetailsActivity.
	        Intent intent = new Intent(this.activity, VaccineDetailsActivity.class);
	        intent.putExtra(this.activity.getResources().getString(R.string.vaccine), this.vaccine);
	        this.activity.startActivity(intent);
		}
		
	}
	
	private class VaccineCheckedListener implements OnCheckedChangeListener {

		private Activity activity;
		private Vaccine vaccine;
		
		public VaccineCheckedListener(Activity activity, Vaccine vaccine) {
			
			this.activity = activity;
			this.vaccine = vaccine;
		}
		
		@Override
		public void onCheckedChanged( CompoundButton buttonView,
									  boolean isChecked ) {
			
			TakenVaccineController controller = new TakenVaccineController(this.activity);
			if (isChecked) {
				
				controller.addTakenVaccine(
						MyBabyActivity.instance.getSelectedBaby().getId(),
						this.vaccine.getId(), this.vaccine.getMonth() );
			} else {
				
				controller.removeTakenVaccine(
						MyBabyActivity.instance.getSelectedBaby().getId(),
						this.vaccine.getId(), this.vaccine.getMonth() );
			}
		}
		
		
	}
	
}