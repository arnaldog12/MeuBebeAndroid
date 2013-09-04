package gecko.mybaby.model;

public class Book {

	private String titulo;
	private String autor;
	private String editora;
	
	public Book(String titulo, String autor, String editora){
		
		this.titulo = titulo;
		this.autor = autor;
		this.editora = editora;
	}
	
	public void setTitulo(String titulo){
		
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		
		return this.titulo;
	}
	
	public void setAutor(String autor){
		
		this.autor = autor;
	}
	
	public String getAutor(){
		
		return this.autor;
	}
	
	public void setEditora(String editora){
		
		this.editora = editora;
	}
	
	public String getEditora(){
		
		return this.editora;
	}
	
}
