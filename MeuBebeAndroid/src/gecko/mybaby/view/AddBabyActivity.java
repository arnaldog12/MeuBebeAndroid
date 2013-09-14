package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.model.Baby;
import gecko.mybaby.model.Historic;
import gecko.mybaby.model.Progress;

import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AddBabyActivity extends Activity implements OnDateSetListener {
	
	public static Activity instance = null;
	
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;
	
	private EditText babyName;
	private EditText babyBirth;
	private Button babyBoy;
	private Button babyGirl;
	private ImageButton babyImage;
	
	private int gender = Baby.GENDER_UNKNOWN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_baby);
		
		AddBabyActivity.instance = this;
		
		this.getReferences();
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));
		
		this.babyName = (EditText) this.findViewById(R.id.baby_name);
		this.babyBirth = (EditText) this.findViewById(R.id.baby_birth);
		this.babyBoy = (Button) this.findViewById(R.id.baby_boy);
		this.babyGirl = (Button) this.findViewById(R.id.baby_girl);
		this.babyImage = (ImageButton) this.findViewById(R.id.baby_image);
	}
	
	public void saveBabyClicked(View view) {
		
		String name = this.babyName.getText().toString();
		String birth = this.babyBirth.getText().toString();
		
		if (name.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.baby_name_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		
		if (birth.equals("")) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.baby_birth_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		
		if (this.gender == Baby.GENDER_UNKNOWN) {
			
			Toast.makeText( this,
						    this.getResources().getString(R.string.baby_gender_not_set),
						    Toast.LENGTH_LONG).show();
			return;
		}
		
		Bitmap img = BitmapFactory.decodeResource(this.getResources(), R.drawable.no_photo_120x120);
		Baby baby = new Baby(name, birth, this.gender, new Historic(), new Progress(), img);
		
		MyBabyActivity.instance.addBaby(baby);
		
		this.finish();
	}
	
	public void boyClicked(View view) {

		this.gender = Baby.GENDER_BOY;
		
		this.babyBoy.setBackgroundResource(R.drawable.button_selection_left_on);
		this.babyGirl.setBackgroundResource(R.drawable.button_selection_right_off);
		
		this.updateActivityDecoration();
	}
	
	public void girlClicked(View view) {
		
		this.gender = Baby.GENDER_GIRL;
		
		this.babyBoy.setBackgroundResource(R.drawable.button_selection_left_off);
		this.babyGirl.setBackgroundResource(R.drawable.button_selection_right_on);
		
		this.updateActivityDecoration();
	}
	
	public void babyBirthClicked(View view) {
		
		DatePickerDialog dialog;

		int day;
		int month;
		int year;

		String dateStr = this.babyBirth.getText().toString();
		
		if (dateStr.equals("")) {

			Date date = new Date();
			
			day = date.getDate();
			month = date.getMonth();
			year = date.getYear() + 1900;
		} else {
		
			String dateStrSplitted[] = dateStr.split("/");
			
			day = Integer.parseInt(dateStrSplitted[0]);
			month = Integer.parseInt(dateStrSplitted[1]) - 1;
			year = Integer.parseInt(dateStrSplitted[2]);
		}
		
		dialog = new DatePickerDialog(this, this, year, month, day);
		dialog.show();
	}
	
	private void updateActivityDecoration() {
	
		switch (this.gender) {
		
			case Baby.GENDER_BOY:
				this.externalLayout.setBackgroundResource(R.drawable.background_activity_boy);
				this.tabBar.setBackgroundResource(R.drawable.tab_bar_boy);
				this.navigationBar.setBackgroundResource(R.drawable.navigation_bar_boy);
				break;
		
			case Baby.GENDER_GIRL:
				this.externalLayout.setBackgroundResource(R.drawable.background_activity_girl);
				this.tabBar.setBackgroundResource(R.drawable.tab_bar_girl);
				this.navigationBar.setBackgroundResource(R.drawable.navigation_bar_girl);
				break;
				
			default:
				this.externalLayout.setBackgroundResource(R.drawable.background_activity_neutral);
				this.tabBar.setBackgroundResource(R.drawable.tab_bar_neutral);
				this.navigationBar.setBackgroundResource(R.drawable.navigation_bar_neutral);
				
		}
	}

	@Override
	public void onDateSet( DatePicker view, int year,
						   int monthOfYear, int dayOfMonth ) {
		
		this.babyBirth.setText(this.mountDateString(year, monthOfYear, dayOfMonth));
	}
	
	private String mountDateString(int year, int monthOfYear, int dayOfMonth) {
		
		StringBuilder builder =
				new StringBuilder().append((dayOfMonth < 10) ? ("0") : (""))
								   .append(dayOfMonth)
								   .append("/")
								   .append((monthOfYear < 9) ? ("0") : (""))
								   .append(monthOfYear + 1)
								   .append("/")
								   .append(year);

		return builder.toString();
	}

}