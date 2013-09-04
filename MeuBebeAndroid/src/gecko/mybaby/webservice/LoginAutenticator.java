package gecko.mybaby.webservice;

import gecko.mybaby.model.Baby;
import gecko.mybaby.model.Historic;
import gecko.mybaby.model.Progress;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LoginAutenticator implements Runnable {

	private static final String URL_BASE = "http://ws.geckoapps.com.br/bebes.php?";
	private static final String URL_LOGIN = "usuario=";
	private static final String URL_PASSWORD = "&senha=";

	private static final String JSON_ID_INDEX = "id";
	private static final String JSON_NAME_INDEX = "nome";
	private static final String JSON_AGE_INDEX = "idade";
	private static final String JSON_SEX_INDEX = "sexo";
	
	public interface LoginCallback {

		public static final int STATUS_SUCCESS = 1;
		public static final int STATUS_ERROR = 2;
		
		public void loginResponse(int status, ArrayList<Baby> babysList, String usernameStr, String passwordStr);
	}
	
	private String url;
	private LoginCallback callback;
	private String username;
	private String password;
	
	private LoginAutenticator(String url, LoginCallback callback, String username, String password) {
		
		this.url = url;
		this.callback = callback;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		
		ArrayList<Baby> babysList = new ArrayList<Baby>();

        JSONParser jParser = new JSONParser();
        JSONObject jsonObject= jParser.getJSONFromUrl(this.url);
        
        try {
        	
			String status = jsonObject.getString("status");
			if (status.equals("false")) {
				
				Log.e("Meu Bebê", "status = false");
				this.callback.loginResponse(LoginCallback.STATUS_ERROR, null, null, null);
				return;
			}
			
			int babysNumber = Integer.parseInt(jsonObject.getString("n_bebes"));
			
			for (int i = 0 ; i < babysNumber ; i++) {
				
				JSONObject babyJsonArray = jsonObject.getJSONObject(String.valueOf(i));
				
				String idStr = babyJsonArray.getString(LoginAutenticator.JSON_ID_INDEX);
				String nameStr = babyJsonArray.getString(LoginAutenticator.JSON_NAME_INDEX);
				String ageStr = babyJsonArray.getString(LoginAutenticator.JSON_AGE_INDEX);
				String sexStr = babyJsonArray.getString(LoginAutenticator.JSON_SEX_INDEX);

				int id = Integer.parseInt(idStr);
				int age = Integer.parseInt(ageStr); //
				int sex = ((sexStr.equals("t")) ? (Baby.GENDER_BOY) : (Baby.GENDER_GIRL));
				
				Baby baby = new Baby(nameStr, "25/12/2011", sex, new Historic(), new Progress(), null);
				baby.setId(id);
				babysList.add(baby);
			}
		} catch (JSONException exception) {
			
			exception.printStackTrace();
			this.callback.loginResponse(LoginCallback.STATUS_ERROR, null, null, null);
			return;
		} catch (Exception exception) {

			exception.printStackTrace();
			this.callback.loginResponse(LoginCallback.STATUS_ERROR, null, null, null);
			return;
		}
		
		this.callback.loginResponse(LoginCallback.STATUS_SUCCESS, babysList, this.username, this.password);
	}
	
	public static void autenticateLogin(String username, String password, LoginCallback callback) {
		
		String url = LoginAutenticator.mountURL(username, password);
		
		LoginAutenticator autenticator = new LoginAutenticator(url, callback, username, password);
		Thread thread = new Thread(autenticator);
		thread.start();
	}
	
	private static String mountURL(String username, String password) {
		
		StringBuilder builder =
				new StringBuilder().append(LoginAutenticator.URL_BASE)
								   .append(LoginAutenticator.URL_LOGIN)
								   .append(username)
								   .append(LoginAutenticator.URL_PASSWORD)
								   .append(password);
		return builder.toString();
	}
	
}