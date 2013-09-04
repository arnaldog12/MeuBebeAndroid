package gecko.mybaby.model;

import gecko.mybaby.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter {
	private Context context;
	private List<Book> books;
	
	public BookAdapter(Context context, List<Book> books){
		super();
		this.context = context;
		this.books = books;
	}
	
	@Override
	public int getCount() {

		return this.books.size();
	}

	@Override
	public Object getItem(int index) {

		return this.books.get(index);
	}

	@Override
	public long getItemId(int posicao) {

		return posicao;
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		
		// Recupera o Livro da posição atual
	    Book book = books.get(posicao);

	    // LayoutInflater permite instanciar uma View a partir de um arquivo de layout XML
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    // Cria a view a partir do arquivo detalhes_livro.xml
	    View view = inflater.inflate(R.layout.detalhes_livro, null);

	    // Atualiza o valor dos campos da tela
	    TextView titulo = (TextView) view.findViewById(R.id.titulo);
	    titulo.setText(book.getTitulo());

	    TextView autor = (TextView) view.findViewById(R.id.autor);
	    autor.setText(book.getAutor());

	    TextView editora = (TextView) view.findViewById(R.id.editora);
	    editora.setText(book.getEditora());

	    return view;
	}

}
