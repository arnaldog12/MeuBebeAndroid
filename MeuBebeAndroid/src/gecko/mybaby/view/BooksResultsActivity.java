package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.model.Book;
import gecko.mybaby.model.BookAdapter;
import gecko.mybaby.webservice.JSONParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BooksResultsActivity extends ListActivity {
	
	public static Activity instance = null;
	
	private static final String baseUrl = "https://www.googleapis.com/books/v1/volumes?maxResults=30&q=";
	private static final String apiKey = "AIzaSyCckSrYqQV_LvXkth-kPpkU5EReN44TmjA";
	
	private static final String[] temas = {"mãe", "pai", "bebê", "criança", "gravidez", "amamentação"};
	
	private static final String TAG_ITEMS = "items";
	private static final String TAG_VOLUME_INFO = "volumeInfo";
	private static final String TAG_TITLE = "title";
	private static final String TAG_AUTHORS = "authors";
	private static final String TAG_PUBLISHER = "publisher";
	private static final String TAG_TOTAL_ITEMS = "totalItems";
	
	private String titulo;
	private String autor;
	private String editora;
	
	private List<Book> results;
	private JSONArray books;
	
	/** Initializes the app when it is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.books_results);
        
        BooksResultsActivity.instance = this;
        
        this.results = new ArrayList<Book>();
        this.books = new JSONArray();
        
        int index = (int) Math.random()*6;
        this.titulo = temas[index];
        
    	this.autor = new String();
    	this.editora = new String();

        this.loadResults();    
    }
    
    private void loadResults() {
    	
      	//Exibe uma janela de aguarde;
    	final ProgressDialog dialog = ProgressDialog.show(this, "Aguarde",
            "Buscando livros, por aguarde...", false, true);
        
    	new Thread() {
    		
    		public void run() {
        	  
    			try {
        		  
		        	StringBuilder jsonUrl = new StringBuilder(baseUrl);
		        	 
		        	if (!BooksResultsActivity.this.titulo.trim().equals("")){
		        		 
		        		jsonUrl.append("+intitle:");
		        		jsonUrl.append(URLEncoder.encode(BooksResultsActivity.this.titulo, "utf-8").toString());
		        	}
		        	 
		            jsonUrl.append("&key="+apiKey);
		            Log.v("url", jsonUrl.toString());
		             
		            JSONParser jParser = new JSONParser();
		            JSONObject jsonResult = jParser.getJSONFromUrl(jsonUrl.toString());
		             
		            int nResults = jsonResult.getInt(BooksResultsActivity.TAG_TOTAL_ITEMS);
		             
		            if (nResults == 0) {
		            	 
		            	BooksResultsActivity.this.runOnUiThread(new Runnable() {					
								
		            		@Override
							public void run() {
		            			 
		            			Toast.makeText( BooksResultsActivity.this,
		            							"Nenhum Livro Encontrado.",
		            							Toast.LENGTH_SHORT ).show();
		            		}
								
		            	});
		            	
		            	return;
		            }            	 
		             
		            BooksResultsActivity.this.books = new JSONArray();
		            BooksResultsActivity.this.books = jsonResult.getJSONArray(BooksResultsActivity.TAG_ITEMS);
		             
		            for (int i = 0; i < BooksResultsActivity.this.books.length(); i++) {
		            	 
		            	JSONObject book = BooksResultsActivity.this.books.getJSONObject(i);
		            	JSONObject volumeInfo = book.getJSONObject(BooksResultsActivity.TAG_VOLUME_INFO);
		            	 
		            	String title = "Sem título";
		            	if (volumeInfo.has(BooksResultsActivity.TAG_TITLE)) {
		            		
		            		title = volumeInfo.getString(BooksResultsActivity.TAG_TITLE);
		            	}
		            	 
		            	String publisher = "Sem editora";	            	 
		            	if (volumeInfo.has(BooksResultsActivity.TAG_PUBLISHER)) {
		            		
		            		publisher = volumeInfo.getString(BooksResultsActivity.TAG_PUBLISHER);
		            	}
		            	 
		            	StringBuilder sbAuthors = new StringBuilder("Sem Autor(es)");
		            	if (volumeInfo.has(BooksResultsActivity.TAG_AUTHORS)) {
		            		
		            		sbAuthors = new StringBuilder();
			            	JSONArray authors = volumeInfo.getJSONArray(BooksResultsActivity.TAG_AUTHORS);
			            	 
			            	for (int j = 0 ; j < authors.length() ; j++, sbAuthors.append(", ")) {
			            		
			            		sbAuthors.append(authors.getString(j));
			            	}
		            	}
		            	 
		            	if ((!BooksResultsActivity.this.titulo.trim().equals("") && !title.equals("Sem título"))
		            		|| (!BooksResultsActivity.this.autor.trim().equals("") && !sbAuthors.equals("Sem Autor(es)"))
		            		|| (!BooksResultsActivity.this.editora.trim().equals("") && !publisher.equals("Sem editora"))){
	
		            		Book b = new Book(title, sbAuthors.toString(), publisher);
			            	BooksResultsActivity.this.results.add(b);
			        	}
	
		            }
		             
		            BooksResultsActivity.this.updateList();
	
    			} catch (UnsupportedEncodingException e) {
	
    				BooksResultsActivity.this.runOnUiThread(new Runnable() {
    					
						@Override
						public void run() {
							
							Toast.makeText(BooksResultsActivity.this, "Erro ao formatar a URL", Toast.LENGTH_SHORT).show();
						}
    				});
		        	 
    			} catch (JSONException e) {
					
					BooksResultsActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							Toast.makeText(BooksResultsActivity.this, "JSON exception", Toast.LENGTH_SHORT).show();
						}
						
					});
					
				} catch (Exception e) {
					
					BooksResultsActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							Toast.makeText(BooksResultsActivity.this, "Erro desconhecido.", Toast.LENGTH_SHORT).show();
						}
						
					});
				} finally {
					
		             // Fecha a janela de aguarde
		             dialog.dismiss();
				}
    		}
          
    	}.start();
    }
    
    private void updateList() {
    	
        new Thread() {
          @Override
          public void run() {
            try {

              if (results != null) {
               
            	  BooksResultsActivity.this.runOnUiThread(new Runnable() {
                      @Override
                      public void run() {

                    	  BookAdapter bookAdapter = new BookAdapter(BooksResultsActivity.this, BooksResultsActivity.this.results);
                    	  BooksResultsActivity.this.setListAdapter(bookAdapter);
                      }
                    });                    
               }
            }catch (Exception e){
            	
            }
          }
          
        }.start();
	}

}
