package gecko.mybaby.webservice;

import gecko.mybaby.model.Baby;
import gecko.mybaby.webservice.LoginAutenticator.LoginCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class RemoveRemoteBaby implements Runnable {
	
	private static final String URL_BASE = "http://ws.geckoapps.com.br/remover-bebe.php?";
	private static final String URL_ID = "id=";

	public interface RemoveBabyCallback {

		public static final int STATUS_SUCCESS = 1;
		public static final int STATUS_ERROR = 2;
		
		public void removeBabyResponse(int status, Baby baby);
	}
	
	private String url;
	private RemoveBabyCallback callback;
	private Baby baby;
	
	public RemoveRemoteBaby(String url, RemoveBabyCallback callback, Baby baby) {
		
		this.url = url;
		this.callback = callback;
		this.baby = baby;
	}
	
	public static void removeBaby(int id, RemoveBabyCallback callback, Baby baby) {
		
		String url = RemoveRemoteBaby.mountURL(id);
		
		RemoveRemoteBaby removeRemoteBaby = new RemoveRemoteBaby(url, callback, baby);
		Thread thread = new Thread(removeRemoteBaby);
		thread.start();
	}
	
	private static String mountURL(int id) {

		StringBuilder builder =
				new StringBuilder().append(RemoveRemoteBaby.URL_BASE)
								   .append(RemoveRemoteBaby.URL_ID)
								   .append(id);
		
		return builder.toString();
	}
	
	@Override
	public void run() {
		
        JSONParser jParser = new JSONParser();
        JSONObject jsonObject= jParser.getJSONFromUrl(this.url);
        
        try {

			String status = jsonObject.getString("status");
			if (status.equals("false")) {
				
				this.callback.removeBabyResponse(LoginCallback.STATUS_ERROR, null);
				return;
			}
        } catch (JSONException exception) {
			
			exception.printStackTrace();
			this.callback.removeBabyResponse(LoginCallback.STATUS_ERROR, null);
			return;
		} catch (Exception exception) {

			exception.printStackTrace();
			this.callback.removeBabyResponse(LoginCallback.STATUS_ERROR, null);
			return;
		}
        
        this.callback.removeBabyResponse(LoginCallback.STATUS_SUCCESS, this.baby);
	}
	
	
}