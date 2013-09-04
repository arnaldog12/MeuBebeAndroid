package gecko.mybaby.view;

import gecko.mybaby.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TipsActivity extends Activity {
	
	private static String CAT_ALIMENTACAO = "Alimentação";
	private static String CAT_AMAMENTACAO = "Amamentação";
	private static String CAT_ESTIMULACAO = "Estimulação";
	private static String CAT_CUIDADOS = "Cuidados";
	private static String CAT_PRIMEIROS_DIAS = "Primeiros Dias";
	private static String CAT_DE_MAE_PARA_MAE = "De Mãe para Mãe";
	
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;
	
	private ListView listView;
	
	private ArrayList<String> tipsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_tips);
		
		this.getReferences();
		
		this.updateActivityDecoration();
		
		this.tipsList = new ArrayList<String>();
		this.tipsList.add(TipsActivity.CAT_ALIMENTACAO);
		this.tipsList.add(TipsActivity.CAT_AMAMENTACAO);
		this.tipsList.add(TipsActivity.CAT_ESTIMULACAO);
		this.tipsList.add(TipsActivity.CAT_CUIDADOS);
		this.tipsList.add(TipsActivity.CAT_PRIMEIROS_DIAS);
		this.tipsList.add(TipsActivity.CAT_DE_MAE_PARA_MAE);
		
		this.listView.setAdapter(new TipsListAdapter(this, 0, this.tipsList));
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
			
	private class TipsListAdapter extends ArrayAdapter<String> {

		public TipsListAdapter(	Context context,
								int textViewResourceId,
								List<String> objects ) {
			
			super(context, textViewResourceId, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			String category = this.getItem(position);
			
			TextView layout = ((TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.tips_category, null));
			
			layout.setText(category);
			layout.setOnClickListener(new OnTipCategoryClickedListener(category));
			
			return layout;
		}
		
		private class OnTipCategoryClickedListener implements OnClickListener {

			private String category;
			
			public OnTipCategoryClickedListener(String category) {
				
				this.category = category;
				
			}
			
			@Override
			public void onClick(View v) {
				
				Intent intent;
				
				//Initiate the Tips View of Category.
				if (this.category.equals(TipsActivity.CAT_DE_MAE_PARA_MAE)){
					
					intent = new Intent(TipsActivity.this, FromMotherToMotherActivity.class);
					TipsActivity.this.startActivity(intent);
				}
			}			
			
		}
		
	}	
}
