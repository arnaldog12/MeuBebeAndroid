package gecko.mybaby.webservice;

import gecko.mybaby.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddRemoteUser extends Activity{
	
	public EditText editTextLogin;
	public EditText editTextPassword;

	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_login);
		
		this.editTextLogin = (EditText) findViewById(R.id.username);
		this.editTextPassword = (EditText)findViewById(R.id.password);		
				
	}
	
	public void addUserClicked(View viewClicked){
		Log.v("login", "password");
	}
	
}
