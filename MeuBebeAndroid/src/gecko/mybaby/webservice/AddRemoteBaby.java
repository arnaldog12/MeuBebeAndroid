package gecko.mybaby.webservice;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import gecko.mybaby.model.Baby;
import gecko.mybaby.webservice.LoginAutenticator.LoginCallback;

public class AddRemoteBaby implements Runnable {
	
	private static final String URL_BASE = "http://ws.geckoapps.com.br/adicionar-bebe.php?";
	private static final String URL_USER = "usuario=";
	private static final String URL_NAME = "&nome=";
	private static final String URL_AGE = "&idade=";
	private static final String URL_SEX = "&sexo=";
	private static final String URL_PROGRESS = "&progresso=";
	
	private static final String URL_PROGRESS_NULL = "0000000000000000000000000000000000000000000000000000000000000000";
	
	public interface AddBabyCallback {

		public static final int STATUS_SUCCESS = 1;
		public static final int STATUS_ERROR = 2;
		
		public void addBabyResponse(int status, Baby baby);
	}
	
	private String url;
	private String username;
	private AddBabyCallback callback;
	private Baby baby;
	
	public AddRemoteBaby(String url, String username, AddBabyCallback callback, Baby baby) {
		
		this.url = url;
		this.username = username;
		this.callback = callback;
		this.baby = baby;
	}
	
	public static void addBaby(String username, Baby baby, AddBabyCallback callback) {
		
		String url = AddRemoteBaby.mountURL(username, baby);
		
		AddRemoteBaby addRemoteBaby = new AddRemoteBaby(url, username, callback, baby);
		Thread thread = new Thread(addRemoteBaby);
		thread.start();
	}
	
	private static String mountURL(String username, Baby baby) {
		
		StringBuilder builder =
				new StringBuilder().append(AddRemoteBaby.URL_BASE)
								   .append(AddRemoteBaby.URL_USER)
								   .append(username)
								   .append(AddRemoteBaby.URL_NAME)
								   .append(baby.getName())
								   .append(AddRemoteBaby.URL_AGE)
								   .append(baby.getAgeInMonths())
								   .append(AddRemoteBaby.URL_SEX)
								   .append(baby.getGender() == Baby.GENDER_BOY ? "1" : "0")
								   .append(AddRemoteBaby.URL_PROGRESS)
								   .append(AddRemoteBaby.URL_PROGRESS_NULL);
		return builder.toString();
	}
	
	//http://ws.geckoapps.com.br/adicionar-bebe.php?usuario=arnaldo.g12@gmail.com&nome=Joao&idade=12&sexo=1&progresso=0000000000000000000000000000000000000000000000000000000000000000
	
	@Override
	public void run() {
		
        JSONParser jParser = new JSONParser();
        JSONObject jsonObject= jParser.getJSONFromUrl(this.url);
        
        int id = 0;
        
        try {

			String status = jsonObject.getString("status");
			if (status.equals("false")) {
				
				this.callback.addBabyResponse(LoginCallback.STATUS_ERROR, null);
				return;
			}

			id = Integer.parseInt(jsonObject.getString("bebe_id"));
        } catch (JSONException exception) {
			
			exception.printStackTrace();
			this.callback.addBabyResponse(LoginCallback.STATUS_ERROR, null);
			return;
		} catch (Exception exception) {

			exception.printStackTrace();
			this.callback.addBabyResponse(LoginCallback.STATUS_ERROR, null);
			return;
		}
        
        this.baby.setId(id);
        this.callback.addBabyResponse(LoginCallback.STATUS_SUCCESS, this.baby);
	}
	
}