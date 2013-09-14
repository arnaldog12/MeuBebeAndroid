package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.controller.ReminderController;
import gecko.mybaby.exceptions.PastTimeException;
import gecko.mybaby.model.Reminder;
import gecko.mybaby.model.Vaccine;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

public class VaccineDetailsActivity extends Activity {
	
	public static Activity instance = null;

	private static final int VACCINE_ID_MASK = 0x00001FFF; //13 bits mask.
	private static final int REMINDER_ID_MASK = 0x00000007; //3 bits mask.
	
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;

	private EditText vaccineName;
	private EditText vaccineMonth;
	private EditText vaccineDose;
	private EditText vaccineDescription;
	
	private ListView listView;
	
	private Vaccine vaccine;

	private List<Reminder> reminders;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_vaccine_details);
		
		VaccineDetailsActivity.instance = this;
		
		this.getReferences();
		
		this.updateActivityDecoration();
		
		this.vaccine = (Vaccine) this.getIntent().getSerializableExtra(
				this.getResources().getString(R.string.vaccine));
		
		this.updateTextFields();
		
		this.updateListView();
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));

		this.vaccineName = ((EditText) this.findViewById(R.id.vaccine_name));
		this.vaccineMonth= ((EditText) this.findViewById(R.id.vaccine_month));
		this.vaccineDose = ((EditText) this.findViewById(R.id.vaccine_dose));
		this.vaccineDescription = ((EditText) this.findViewById(R.id.vaccine_description));
		
		this.listView = ((ListView) this.findViewById(R.id.list_view));
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
	
	private void updateTextFields() {

		this.vaccineName.setText(this.vaccine.getName());
		this.vaccineMonth.setText(this.getAgeStr(this.vaccine.getMonth()));
		this.vaccineDose.setText(this.vaccine.getDose());
		this.vaccineDescription.setText(this.vaccine.getDescription());
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
    
    private void updateListView() {
    	
    	ReminderController controller = new ReminderController(this);
    	this.reminders = controller.getRemindersPerBabyAndVaccine(
    			MyBabyActivity.instance.getSelectedBaby().getId(), this.vaccine.getId());
    	
    	Log.v("Meu Bebê", "reminders size = " + this.reminders.size());
    	this.listView.setAdapter(new RemindersAdapter(this, this.reminders));
    }
	
	public void graphicsClicked(View view) {

		VaccineDetailsActivity.instance.finish();
		VaccinesActivity.instance.finish();
		
		//Initiate GraphicsActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, GraphicsActivity.class);
	    this.startActivity(intent);
	}
	
	public void vaccinesClicked(View view) {

		VaccineDetailsActivity.instance.finish();
		VaccinesActivity.instance.finish();
		
		//Initiate VaccinesActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, VaccinesActivity.class);
	    this.startActivity(intent);
	}
	
	public void myBabyClicked(View view) {

		VaccineDetailsActivity.instance.finish();
		VaccinesActivity.instance.finish();
	}
	
	public void progressClicked(View view) {

		VaccineDetailsActivity.instance.finish();
		VaccinesActivity.instance.finish();
		
		//Initiate ProgressActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, ProgressActivity.class);
	    this.startActivity(intent);
	}
	
	public void tipsClicked(View view) {

		VaccineDetailsActivity.instance.finish();
		VaccinesActivity.instance.finish();
		
		//Initiate TipsActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, TipsActivity.class);
	    this.startActivity(intent);
	}
    
    private class RemindersAdapter extends ArrayAdapter<Reminder> {
    	
    	public RemindersAdapter(Activity activity, List<Reminder> reminders) {
    		
    		super(activity, 0, reminders);
    	}
    	
    	@Override
    	public int getCount() {
    		
    		return ((super.getCount() == 8) ? (super.getCount()) : (super.getCount() + 1));
    	}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LinearLayout layout;
			
			Reminder reminder;
			if (super.getCount() == Reminder.MAX_NUMBER) {
				
				reminder = this.getItem(position);
				
				layout = (LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.reminder, null);

				EditText reminderDate = (EditText) layout.findViewById(R.id.reminder_date);
				EditText reminderTime = (EditText) layout.findViewById(R.id.reminder_time);
				ImageButton reminderDelete = (ImageButton) layout.findViewById(R.id.reminder_delete);
				
				reminderDate.setText(reminder.getDate());
				reminderTime.setText(reminder.getTime());
				reminderDelete.setOnClickListener(new OnReminderDeleteClickListener(
						VaccineDetailsActivity.this, reminder));
			} else {
				
				if (position != 0) {
					
					reminder = this.getItem(position - 1);
					
					layout = (LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.reminder, null);

					EditText reminderDate = (EditText) layout.findViewById(R.id.reminder_date);
					EditText reminderTime = (EditText) layout.findViewById(R.id.reminder_time);
					ImageButton reminderDelete = (ImageButton) layout.findViewById(R.id.reminder_delete);

					reminderDate.setText(reminder.getDate());
					reminderTime.setText(reminder.getTime());
					reminderDelete.setOnClickListener(new OnReminderDeleteClickListener(
							VaccineDetailsActivity.this, reminder));
				} else {
					
					layout = (LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.add_reminder, null);
					
					EditText reminderDate = (EditText) layout.findViewById(R.id.reminder_date);
					EditText reminderTime = (EditText) layout.findViewById(R.id.reminder_time);
					ImageButton reminderSave = (ImageButton) layout.findViewById(R.id.reminder_save);
					
					reminderDate.setOnClickListener(new OnNewReminderDateClickListener(
							VaccineDetailsActivity.this, reminderDate));
					
					reminderTime.setOnClickListener(new OnNewReminderTimeClickListener(
							VaccineDetailsActivity.this, reminderTime));
					
					reminderSave.setOnClickListener(new OnNewReminderSaveClickListener(
							VaccineDetailsActivity.this, VaccineDetailsActivity.this.vaccine,
							reminderDate, reminderTime));
				}
			}
			
			return layout;
		}
		
    }
    
    private class OnReminderDeleteClickListener implements OnClickListener {

    	private Activity activity;
    	private Reminder reminder;
    	
    	public OnReminderDeleteClickListener(Activity activity, Reminder reminder) {
    		
    		this.activity = activity;
    		this.reminder = reminder;
    	}

		@Override
		public void onClick(View view) {
			
			this.cancelAlarm(this.reminder.getBabyId(), this.reminder.getVaccineId(),
					this.reminder.getReminderId());
			
			ReminderController controller = new ReminderController(VaccineDetailsActivity.this);
			controller.removeReminder(this.reminder.getBabyId(), this.reminder.getVaccineId(),
					this.reminder.getReminderId());
			
			VaccineDetailsActivity.this.updateListView();
		}
		
	    public void cancelAlarm(int babyId, int vaccineId, int reminderId) {
	    	
	        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	        
	        int reminderRequestCode = VaccineDetailsActivity.this.getReminderRequestCode(babyId, vaccineId, reminderId);
	        
	        Intent updateServiceIntent = new Intent(VaccineDetailsActivity.this, AlarmReceiverActivity.class);
	        PendingIntent pendingUpdateIntent = PendingIntent.getActivity(VaccineDetailsActivity.this,
	        		reminderRequestCode, updateServiceIntent, PendingIntent.FLAG_CANCEL_CURRENT);
	        
	        try {
	        	
	            alarmManager.cancel(pendingUpdateIntent);
	        } catch(Exception e) {
	            
	        	e.printStackTrace();
	        }
	    }
    	
    }
    
    private class OnNewReminderDateClickListener implements OnClickListener, OnDateSetListener {
    	
    	private Activity activity;
    	private EditText editText;
    	
    	public OnNewReminderDateClickListener(Activity activity, EditText editText) {
    		
    		this.activity = activity;
    		this.editText = editText;
    	}
    	
		@Override
		public void onClick(View view) {
			
			DatePickerDialog dialog;

			int day;
			int month;
			int year;

			String dateStr = ((EditText) view).getText().toString();
			
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
			
			dialog = new DatePickerDialog(this.activity, this, year, month, day);
			dialog.show();
		}

		@Override
		public void onDateSet( DatePicker view, int year,
							   int monthOfYear, int dayOfMonth ) {
			
			this.editText.setText(this.mountDateString(year, monthOfYear, dayOfMonth));
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
    
    private class OnNewReminderTimeClickListener implements OnClickListener, OnTimeSetListener {

    	private Activity activity;
    	private EditText editText;
    	
    	public OnNewReminderTimeClickListener(Activity activity, EditText editText) {
    		
    		this.activity = activity;
    		this.editText = editText;
    	}
    	
		@Override
		public void onClick(View view) {
			
			TimePickerDialog dialog;

			String dateStr = ((EditText) view).getText().toString();
			
			int hour;
			int minute;
			
			if (dateStr.equals("")) {

				Date date = new Date();
				
				hour = date.getHours();
				minute = date.getMinutes();
			} else {
			
				String dateStrSplitted[] = dateStr.split(":");
				
				hour = Integer.parseInt(dateStrSplitted[0]);
				minute = Integer.parseInt(dateStrSplitted[1]);
			}
			
			dialog = new TimePickerDialog(this.activity, this, hour, minute, true);
			dialog.show();
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			
			this.editText.setText(this.mountTimeString(hourOfDay, minute));
		}
		
		private String mountTimeString(int hourOfDay, int minute) {
			
			StringBuilder builder =
					new StringBuilder().append((hourOfDay < 10) ? ("0") : (""))
									   .append(hourOfDay)
									   .append(":")
									   .append((minute < 10) ? ("0") : (""))
									   .append(minute);
			
			return builder.toString();
		}
		
    }
    
    private class OnNewReminderSaveClickListener implements OnClickListener {

    	private Activity activity;
    	private Vaccine vaccine;
    	private EditText date;
    	private EditText time;
    	
    	public OnNewReminderSaveClickListener( Activity activity, Vaccine vaccine,
    										   EditText date,     EditText time ) {
    		
    		this.activity = activity;
    		this.vaccine = vaccine;
    		this.date = date;
    		this.time = time;
    	}
    	
		@Override
		public void onClick(View view) {
			
			ReminderController controller = new ReminderController(this.activity);

			String dateStr = this.date.getText().toString();
			String timeStr = this.time.getText().toString();
			int vaccineId = this.vaccine.getId();

			if (dateStr.equals("")) {

				Toast.makeText( this.activity,
							    this.activity.getResources().getString(R.string.reminder_date_not_set),
							    Toast.LENGTH_LONG).show();
				return;
			}
			if (timeStr.equals("")) {

				Toast.makeText( this.activity,
							    this.activity.getResources().getString(R.string.reminder_time_not_set),
							    Toast.LENGTH_LONG).show();
				return;
			}

			String[] dateStrSplitted = dateStr.split("/");
			String[] timeStrSplitted = timeStr.split(":");
			
			int minute;
			int hour;
			int day;
			int month;
			int year;

			hour = Integer.parseInt(timeStrSplitted[0]);
			minute = Integer.parseInt(timeStrSplitted[1]);
			
			day = Integer.parseInt(dateStrSplitted[0]);
			month = Integer.parseInt(dateStrSplitted[1]) - 1;
			year = Integer.parseInt(dateStrSplitted[2]);
			
			String message = this.createMessage() + " at " + dateStr + " at " + timeStr;
			Reminder reminder = new Reminder( dateStr, timeStr,
											  message,
											  MyBabyActivity.instance.getSelectedBaby().getId(), vaccineId );
			
			reminder.setReminderId(this.findNewReminderId());
			
			try {
				
				this.addAlarm(message, minute, hour, day, month, year, reminder.getReminderId());
			} catch (PastTimeException exception) {
				
				Toast.makeText( this.activity,
							    this.activity.getResources().getString(R.string.past_time),
							    Toast.LENGTH_LONG).show();
				return;
			}
			controller.addReminder(reminder);
			
			
			VaccineDetailsActivity.this.updateListView();
		}
		
		private int findNewReminderId() {
			
			int i;
			int cont;
			
			for (i = 0, cont = 0 ; i < Reminder.MAX_NUMBER ; i++, cont = 0) {
				
				for (Reminder reminder : VaccineDetailsActivity.this.reminders) {
					
					if (reminder.getReminderId() == i) {
						
						cont++;
						break;
					}
				}
				
				if (cont == 0) {
					
					return i;
				}
			}
			
			return Reminder.MAX_NUMBER;
		}
		
		public String createMessage() {
			
			return this.vaccine.getName();
		}
	    
	    public void addAlarm( String message, int minute, int hour,
	    					  int day, int month, int year, int reminderId ) throws PastTimeException {
	    	
	        Calendar cal = Calendar.getInstance();
	        cal.set(year, month, day, hour, minute, 0);
	        if (!cal.after(Calendar.getInstance())) {
	        	
	        	throw new PastTimeException();
	        }
	        
	        int babyId = MyBabyActivity.instance.getSelectedBaby().getId();
	        int gender = MyBabyActivity.instance.getSelectedBaby().getGender();
	        int reminderRequestCode = VaccineDetailsActivity.this.getReminderRequestCode(babyId, this.vaccine.getId(), reminderId);
	        
	        Intent intent = new Intent(VaccineDetailsActivity.this, AlarmReceiverActivity.class);
	        intent.putExtra("message", message);
	        intent.putExtra("vaccine_id", String.valueOf(this.vaccine.getId()));
	        intent.putExtra("baby_id", String.valueOf(babyId));
	        intent.putExtra("reminder_id", String.valueOf(reminderId));
	        intent.putExtra("gender", gender);
	        
	        PendingIntent pendingIntent = PendingIntent.getActivity(
	        		VaccineDetailsActivity.this, reminderRequestCode,
	        		intent, PendingIntent.FLAG_CANCEL_CURRENT);
	        
	        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
	        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
	    }
    	
    }
    
    public int getReminderRequestCode(int babyId, int vaccineId, int reminderId) {
    	
    	return ((babyId << 16) |
    		   ((vaccineId & VaccineDetailsActivity.VACCINE_ID_MASK) << 3) |
    		   (reminderId & VaccineDetailsActivity.REMINDER_ID_MASK));
    }
    
}