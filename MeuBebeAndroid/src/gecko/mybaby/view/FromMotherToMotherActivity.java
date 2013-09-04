package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.model.Tip;
import gecko.mybaby.view.custom.TipAdapter;
import gecko.mybaby.webservice.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FromMotherToMotherActivity extends Activity {
	
	public static Activity instance;
	
	private static final String BASE_URL = "http://ws.geckoapps.com.br/server2.php?data=2013%2F01%2F01%2000%3A00%3A00";
	
	private static final String TAG_AUTOR = "nome";
	private static final String TAG_DICA = "dica";
	private static final String TAG_CATEGORIA = "categoria";
	private static final String TAG_MAIOR_DATA = "maior_data";
	
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;
	
	private ListView listView;
	
	private ArrayList<Tip> tipsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_tips_by_category);
		
		FromMotherToMotherActivity.instance = this;
		
		this.getReferences();
		
		this.updateActivityDecoration();
		
		this.tipsList = new ArrayList<Tip>();

		this.loadResults();
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));
		
		this.listView = ((ListView) this.findViewById(R.id.list_view));
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
    
    private void loadResults() {

        //Exibe uma janela de aguarde.
        final ProgressDialog dialog = ProgressDialog.show(this, "Aguarde",
            "Buscando Dicas, por aguarde...", false, true);
        
    	Runnable runnable = new Runnable() {
    		
    		public void run() {
	        	  
		        try {
		        	 
		        	StringBuilder jsonUrl = new StringBuilder(FromMotherToMotherActivity.BASE_URL);
		        	Log.v("url", jsonUrl.toString());
		             
		            JSONParser jParser = new JSONParser();
		            JSONObject jsonResult = jParser.getJSONFromUrl(jsonUrl.toString());
		             
		            Iterator<String> iterator = jsonResult.keys();
		            
		            while (iterator.hasNext()) {
		            	 
		            	String currentKey = iterator.next();
		            	 
		            	if (currentKey.equals(FromMotherToMotherActivity.TAG_MAIOR_DATA)) {
		            		 
		            		continue;
		            	}
		            	 
		            	JSONObject currentTip = jsonResult.getJSONObject(currentKey);
		            	 
		            	String dica = currentTip.getString(FromMotherToMotherActivity.TAG_DICA);
		            	String autor = currentTip.getString(FromMotherToMotherActivity.TAG_AUTOR);
		            	String categoria = currentTip.getString(FromMotherToMotherActivity.TAG_CATEGORIA);
		            	 
		            	Log.v("dica", dica);
		            	Log.v("autor", autor);
		            	Log.v("categoria", categoria);
		            	 
		            	Tip newTip = new Tip(autor, dica, categoria);
		            	FromMotherToMotherActivity.this.tipsList.add(newTip);
		            }
		             
		            FromMotherToMotherActivity.this.updateList();
	
		        } catch (JSONException e) {
					
		        	FromMotherToMotherActivity.this.runOnUiThread(new Runnable() {					
						
		        		@Override
						public void run() {
							
							Toast.makeText(FromMotherToMotherActivity.this, "JSON exception", Toast.LENGTH_SHORT).show();
						}
						
					});
				} catch (Exception e) {
					
					FromMotherToMotherActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							Toast.makeText(FromMotherToMotherActivity.this, "Erro desconhecido.", Toast.LENGTH_SHORT).show();
						}
						
					});
				} finally {
					
		             //Fecha a janela de aguarde.
		             dialog.dismiss();
		        }
    		}
    	};
        
        Thread thread = new Thread(runnable);
        thread.start();
    }
    
    private void updateList() {
    	
    	Runnable runnable = new Runnable() {
			
    		@Override
    		public void run() {
        	  
	            try {
	
	              if (FromMotherToMotherActivity.this.tipsList != null) {
	               
	            	  FromMotherToMotherActivity.this.runOnUiThread(new Runnable() {
	            		  
	                      @Override
	                      public void run() {
	
	                    	  FromMotherToMotherActivity.this.listView.setAdapter(
	                    			  new TipAdapter(FromMotherToMotherActivity.this, FromMotherToMotherActivity.this.tipsList));
	                      }
	                      
	            	  });
	               }	              
	            } catch (Exception e) {
	            	
	            }
    		}
        };
        
        Thread thread = new Thread(runnable);
        thread.start();
	}
	
	public void graphicsClicked(View view) {
		
		FromMotherToMotherActivity.instance.finish();
		TipsActivity.instance.finish();
		
		//Initiate GraphicsActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, GraphicsActivity.class);
	    this.startActivity(intent);
	}
	
	public void vaccinesClicked(View view) {
		
		FromMotherToMotherActivity.instance.finish();
		TipsActivity.instance.finish();
		
		//Initiate VaccinesActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, VaccinesActivity.class);
	    this.startActivity(intent);
	}
	
	public void myBabyClicked(View view) {
		
		FromMotherToMotherActivity.instance.finish();
		TipsActivity.instance.finish();
	}
	
	public void progressClicked(View view) {
		
		FromMotherToMotherActivity.instance.finish();
		TipsActivity.instance.finish();
		
		//Initiate ProgressActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, ProgressActivity.class);
	    this.startActivity(intent);
	}
	
	public void tipsClicked(View view) {
		
		FromMotherToMotherActivity.instance.finish();
	}
	
}