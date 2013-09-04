package gecko.mybaby.view.custom;

import gecko.mybaby.R;
import gecko.mybaby.model.Tip;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class TipAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Tip> lista;

	public TipAdapter(Context context, ArrayList<Tip> lista) {
		this.context = context;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int posicao) {
		return lista.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return posicao;
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		// Recupera o Carro da posição atual
		Tip dica = lista.get(posicao);

		// LayoutInflater permite instanciar uma View a partir de um arquivo de layout XML
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// Cria a view a partir do arquivo carro_linha_tabela.xml
		View view = inflater.inflate(R.layout.tip, null);

		// Atualiza o valor dos campos da tela
		TextView textViewTip = (TextView) view.findViewById(R.id.tip);
		textViewTip.setText(dica.getTip());

		if(!dica.getAuthor().equals("")){
			TextView textViewAutor = (TextView) view.findViewById(R.id.author);
			textViewAutor.setText("Dica enviada por "+dica.getAuthor());
		}

		return view;
	}
}