package gecko.mybaby.view;

import gecko.mybaby.R;
import gecko.mybaby.controller.VaccineController;
import gecko.mybaby.model.Vaccine;
import gecko.mybaby.view.custom.Page;
import gecko.mybaby.view.custom.VaccinesPage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class VaccinesActivity extends Activity {
	
	public static Activity instance = null;
	
	private LinearLayout externalLayout;
	private RelativeLayout tabBar;
	private LinearLayout navigationBar;

	private Button allVaccines;
	private Button takenVaccines;
	private Button reminders;
	
	private ViewPager viewPager;

	private List<Vaccine> allVaccinesList;
	private List<Vaccine> takenVaccinesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_vaccines);
		
		VaccinesActivity.instance = this;
		
		this.getReferences();
		
		this.updateActivityDecoration();
		
		this.setPager();
	}
	
	private void getReferences() {

		this.externalLayout = ((LinearLayout) this.findViewById(R.id.external_layout));
		this.tabBar = ((RelativeLayout) this.findViewById(R.id.tab_bar));
		this.navigationBar = ((LinearLayout) this.findViewById(R.id.navigation_bar));

		this.allVaccines = ((Button) this.findViewById(R.id.all_vaccines));
		this.takenVaccines = ((Button) this.findViewById(R.id.taken_vaccines));
		this.reminders = ((Button) this.findViewById(R.id.reminders));
		
		this.viewPager = ((ViewPager) this.findViewById(R.id.view_pager));
	}
	
	private void updateActivityDecoration() {
	
		this.externalLayout.setBackgroundResource(MyBabyActivity.getBackgroundExternal());
		this.tabBar.setBackgroundResource(MyBabyActivity.getBackgroundTabBar());
		this.navigationBar.setBackgroundResource(MyBabyActivity.getBackgroundNavigationBar());
	}
	
	private void setPager() {
		
		VaccineController controller = new VaccineController(this);

		this.allVaccinesList = controller.getAllVaccines();
		this.takenVaccinesList = new ArrayList<Vaccine>();
		
		this.viewPager.setAdapter(new VaccinesPagerAdapter(this, this.viewPager, this.allVaccinesList, this.takenVaccinesList));
	}
	
	public void addVaccine(Vaccine vaccine) {
		
		VaccineController controller = new VaccineController(this);
		controller.addVaccine(vaccine);

		this.allVaccinesList = controller.getAllVaccines();
		
		((VaccinesPagerAdapter) this.viewPager.getAdapter()).updateLists(this.allVaccinesList, this.takenVaccinesList);
	}
	
	public void addVaccineClicked(View view) {
		
		//Initiate AddVaccineActivity.
        Intent intent = new Intent(this, AddVaccineActivity.class);
        this.startActivity(intent);
	}
	
	public void allVaccinesClicked(View view) {
		
		
	}
	
	public void takenVaccinesClicked(View view) {
		
		
	}
	
	public void remindersClicked(View view) {
		
		
	}
	
	public void graphicsClicked(View view) {

		VaccinesActivity.instance.finish();
		
		//Initiate GraphicsActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, GraphicsActivity.class);
	    this.startActivity(intent);
	}
	
	public void vaccinesClicked(View view) {}
	
	public void myBabyClicked(View view) {

		VaccinesActivity.instance.finish();
	}
	
	public void progressClicked(View view) {

		VaccinesActivity.instance.finish();
		
		//Initiate ProgressActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, ProgressActivity.class);
	    this.startActivity(intent);
	}
	
	public void tipsClicked(View view) {

		VaccinesActivity.instance.finish();
		
		//Initiate TipsActivity.
	    Intent intent = new Intent(MyBabyActivity.instance, TipsActivity.class);
	    this.startActivity(intent);
	}
	
	private class VaccinesPagerAdapter extends PagerAdapter {

		private static final int VACCINE_PAGES_COUNT = 3;
		
		private Activity activity;
		private ViewPager viewPager;
		private List<Vaccine> allVaccines;
		private List<Vaccine> takenVaccines;
        private List<Page> pages;
		
		public VaccinesPagerAdapter( Activity activity, ViewPager viewPager,
									 List<Vaccine> allVaccines, List<Vaccine> takenVaccines ) {
			
			this.activity = activity;
			this.viewPager = viewPager;
			this.allVaccines = allVaccines;
			this.takenVaccines = takenVaccines;
			
            this.pages = new ArrayList<Page>();
            
            this.setPages();
		}
		
		private void setPages() {

			List<List<Vaccine>> allVaccinesPerMonth = this.getAllVaccines();
			List<List<Vaccine>> takenVaccinesPerMonth = new LinkedList<List<Vaccine>>();
			List<List<Vaccine>> remindersPerMonth = new LinkedList<List<Vaccine>>();
			
			VaccinesPage allVaccinesPage = new VaccinesPage(this.activity, allVaccinesPerMonth);
			VaccinesPage takenVaccinesPage = new VaccinesPage(this.activity, takenVaccinesPerMonth);
			VaccinesPage remindersPage = new VaccinesPage(this.activity, remindersPerMonth);

			this.pages.add(allVaccinesPage);
			this.pages.add(takenVaccinesPage);
			this.pages.add(remindersPage);
		}
		
		private List<List<Vaccine>> getAllVaccines() {
			
			List<List<Vaccine>> allVaccinesPerMonth = new LinkedList<List<Vaccine>>();
			
			int allVaccinesCurrentMonth = this.allVaccines.get(0).getMonth();
			LinkedList<Vaccine> allVaccinesCurrentList = new LinkedList<Vaccine>();
			
			for (Vaccine vaccine : this.allVaccines) {
				
				if (vaccine.getMonth() == allVaccinesCurrentMonth) {
					
					allVaccinesCurrentList.add(vaccine);
				} else {
					
					allVaccinesPerMonth.add(allVaccinesCurrentList);
					
					allVaccinesCurrentList = new LinkedList<Vaccine>();
					allVaccinesCurrentList.add(vaccine);
					
					allVaccinesCurrentMonth = vaccine.getMonth();
				}
			}
			allVaccinesPerMonth.add(allVaccinesCurrentList);
			
			return allVaccinesPerMonth;
		}
		
		public void updateLists(List<Vaccine> allVaccines, List<Vaccine> takenVaccines) {
			
			this.allVaccines = allVaccines;
			this.takenVaccines = takenVaccines;

			this.updatePages();
		}
		
		private void updatePages() {

			List<List<Vaccine>> allVaccinesPerMonth = this.getAllVaccines();
			List<List<Vaccine>> takenVaccinesPerMonth = new LinkedList<List<Vaccine>>();
			List<List<Vaccine>> remindersPerMonth = new LinkedList<List<Vaccine>>();

			((VaccinesPage) this.pages.get(0)).updateList(allVaccinesPerMonth);
			((VaccinesPage) this.pages.get(1)).updateList(takenVaccinesPerMonth);
			((VaccinesPage) this.pages.get(2)).updateList(remindersPerMonth);
			
			this.notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			
			return VaccinesPagerAdapter.VACCINE_PAGES_COUNT;
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