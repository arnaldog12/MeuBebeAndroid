package gecko.mybaby.controller;

import gecko.mybaby.R;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public final class ShareController {

	private static ShareController _instance = null;
	
	private boolean logged = false;
	
	private SocialAuthAdapter adapter = null;
		
	private ShareController() {
		
		adapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers
		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		/*adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
		adapter.addProvider(Provider.MYSPACE, R.drawable.myspace);
		adapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		adapter.addProvider(Provider.YAMMER, R.drawable.yammer);
		adapter.addProvider(Provider.EMAIL, R.drawable.email);
		adapter.addProvider(Provider.MMS, R.drawable.mms);*/

		// Providers require setting user call Back url
		adapter.addCallBack(Provider.TWITTER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		//adapter.addCallBack(Provider.YAMMER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
	}
	
	public void enableButton(Button but) {
		
		adapter.enable(but);
	}
	
	public boolean post(String message) {
		
		if (!logged) {
			
			return false;
		}
		
		adapter.updateStatus(message, null, false);
		return true;
	}
	
	public boolean isLogged() {
		
		return logged;
	}
	
	public static ShareController getInstance() {
		
		if (_instance == null) {
			
			_instance = new ShareController();
		}
		
		return _instance;
	}
	
	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			Log.d("MeuBebe", "#### Login completed");
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			logged = true;
		}

		@Override
		public void onError(SocialAuthError error) {
		}

		@Override
		public void onCancel() {
		}

		@Override
		public void onBack() {
		}
	} 
}
