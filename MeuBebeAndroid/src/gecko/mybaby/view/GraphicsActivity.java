package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.R.drawable;
import gecko.mybaby.R.id;
import gecko.mybaby.R.layout;
import gecko.mybaby.model.Baby;
import gecko.mybaby.view.custom.ImagePage;
import gecko.mybaby.view.custom.Page;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GraphicsActivity extends Activity {

	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;
	
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_graphics);
		
		this.getReferences();
		
		this.updateActivityDecoration();
		
		this.viewPager.setAdapter(new GraphicsPageAdapter(this, this.viewPager));
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));
		
		this.viewPager = (ViewPager) this.findViewById(R.id.view_pager);
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
	
	public void historicClicked(View view) {
		
		//Initiate HistoricActivity.
        Intent intent = new Intent(this, HistoricActivity.class);
        this.startActivity(intent);
	}
	
	private class GraphicsPageAdapter extends PagerAdapter {

		private static final int PAGES_COUNT = 3;
		
		private Activity activity;
        private ViewPager viewPager;
        private List<Page> pages;
		
		public GraphicsPageAdapter(Activity activity, ViewPager viewPager) {

        	this.activity = activity;
        	this.viewPager = viewPager;
            this.pages = new ArrayList<Page>();
            
            this.setPages();
		}
		
		private void setPages() {

	        ImagePage height = null;
	        ImagePage weight = null;
	        ImagePage bmi = null;
	        
	        int gender = MyBabyActivity.instance.getSelectedBaby().getGender();
	        
	        if (gender == Baby.GENDER_BOY) {
	        	
	        	height = new ImagePage(this.activity, R.drawable.graphic_height_boys_mini);
	        	weight = new ImagePage(this.activity, R.drawable.graphic_weight_boys_mini);
	        	bmi = new ImagePage(this.activity, R.drawable.graphic_bmi_boys_mini);
	        } else if (gender == Baby.GENDER_GIRL) {
	        	
	        	height = new ImagePage(this.activity, R.drawable.graphic_height_girls_mini);
	        	weight = new ImagePage(this.activity, R.drawable.graphic_weight_girls_mini);
	        	bmi = new ImagePage(this.activity, R.drawable.graphic_bmi_girls_mini);
	        }
	
	        this.pages.add(height);
	        this.pages.add(weight);
	        this.pages.add(bmi);
		}
		
		@Override
		public int getCount() {
			
			return GraphicsPageAdapter.PAGES_COUNT;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

            return view.equals(object);
		}
		
        @Override
        public Object instantiateItem(View pager, int position) {
            
            Page p = (Page) this.pages.get(position);
            p.doRequest();
            
            if (this.viewPager == null) {
                
            	this.viewPager = (ViewPager) pager;
            }
            
            this.viewPager.addView(p.getCurrentView(), 0);
            
            return this.pages.get(position).getCurrentView();
        }
        
        @Override
        public void destroyItem(View pager, int position, Object view) {
            
            this.viewPager.removeView((View) view);
        }
		
	}

}