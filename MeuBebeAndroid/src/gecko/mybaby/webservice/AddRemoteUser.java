package gecko.mybaby.webservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gecko.mybaby.R;
import gecko.mybaby.model.Book;
import gecko.mybaby.view.BooksResultsActivity;
import gecko.mybaby.view.LoginActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddRemoteUser extends Activity{
	
	public final static String baseURL = "http://ws.geckoapps.com.br/adicionar-usuario.php?";
	
	public final static String TAG_STATUS = "status";
	public final static String STATUS_SUCCESSFULL = "true";
	public final static String STATUS_FAILURE = "false";
	
	public EditText editTextLogin;
	public EditText editTextPassword;
	public EditText editTextConfirmPassword;

	private String email;
	private String senha;
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_add_user);
		
		this.editTextLogin = (EditText) findViewById(R.id.username);
		this.editTextPassword = (EditText)findViewById(R.id.password);		
		this.editTextConfirmPassword = (EditText)findViewById(R.id.confirm_password);
	}
	
	public void addUserClicked(View viewClicked){
		String senhaDigitada = this.editTextPassword.getText().toString();
		String senhaConfirmada = this.editTextConfirmPassword.getText().toString();
	
		if(!senhaDigitada.equals(senhaConfirmada)){
			
			Toast.makeText(this,"Senhas nâo conferem", Toast.LENGTH_SHORT).show();
			return;
		}
		
		this.email = this.editTextLogin.getText().toString();
		
		MessageDigest md = null;
		
		try {
			
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			return;
		}
		
		md.update(senhaDigitada.getBytes());
		byte[] hashMd5 = md.digest();
		
		this.senha = stringHexa(hashMd5).toUpperCase();
		
		addUserToWebService();
	}
	
	private String stringHexa(byte[] bytes) {
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			
		    int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
		    int parteBaixa = bytes[i] & 0xf;
		    
		    if (parteAlta == 0) {
		    	
		    	s.append('0');
		    }
		    
		    s.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		return s.toString();
	}
	
	public void addUserToWebService(){
		// Exibe uma janela de aguarde
        final ProgressDialog dialog = ProgressDialog.show(this, "Aguarde",
            "adicionando usuário, por favor aguarde...", false, true);
        
    	new Thread(){
    		
          public void run() {              
	         try {
	        	 StringBuilder url = new StringBuilder()
					.append(AddRemoteUser.baseURL)
					.append("usuario=")
					.append(AddRemoteUser.this.email)
					.append("&senha=")
					.append(AddRemoteUser.this.senha);
	        	 
	             Log.v("url", url.toString());
	             
	             JSONParser jParser = new JSONParser();
	             JSONObject jsonResult = jParser.getJSONFromUrl(url.toString());
	             
	             boolean status = jsonResult.getBoolean(AddRemoteUser.TAG_STATUS);
	             if(status){
	            	 AddRemoteUser.this.runOnUiThread(new Runnable() {					
	 					@Override
	 					public void run() {
	 						Toast.makeText(AddRemoteUser.this, "Usuário Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
	 					}
	 				});
	            	 
	            	 	            	 
	            	 Intent intent = new Intent(AddRemoteUser.this, LoginActivity.class);
	            	 AddRemoteUser.this.startActivity(intent);
	            	 
	             }else{
	            	 
	            	 AddRemoteUser.this.runOnUiThread(new Runnable() {					
		 					@Override
		 					public void run() {
		 						Toast.makeText(AddRemoteUser.this, "Erro ao adicionar o usuário", Toast.LENGTH_SHORT).show();
		 					}
		 				});
	            	 
	            	 
	             }
	             
	         } catch (JSONException e) {
				
				AddRemoteUser.this.runOnUiThread(new Runnable() {					
					@Override
					public void run() {
						Toast.makeText(AddRemoteUser.this, "JSON exception", Toast.LENGTH_SHORT).show();
					}
				});
				
			} catch (Exception e) {
				
				e.printStackTrace();
				AddRemoteUser.this.runOnUiThread(new Runnable() {					
					@Override
					public void run() {
						Toast.makeText(AddRemoteUser.this, "Erro desconhecido.", Toast.LENGTH_SHORT).show();
						
					}
				});
			} finally {
	             // Fecha a janela de aguarde
	             dialog.dismiss();
	        }
          }
          
    	}.start();
		
	}
	
}
