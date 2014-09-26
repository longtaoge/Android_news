package org.xiangbalao.news.bottommenu;

import java.util.ArrayList;

import org.xiangbalao.news.MainActivity;
import org.xiangbalao.news.R;
import org.xiangbalao.news.base.BaseFragment;
import org.xiangbalao.news.base.BasePage;
import org.xiangbalao.news.leftmenu.MenuFragment;
import org.xiangbalao.news.view.CustomViewPager;
import org.xiangbalao.news.view.LazyViewPager.OnPageChangeListener;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 底部菜单
 * @author longtaoge
 *
 */
public class HomeFragment2 extends BaseFragment {

	@Override
	protected View initView(LayoutInflater inflater) {
		/**
		 * 返回首页布局
		 */
		View view = inflater.inflate(R.layout.frag_home2, null);
		ViewUtils.inject(this, view);
		return view;
	}
	/**
	 * 主页适配器
	 */
	private HomePagerAdapter adapter;
	/**
	 * 所有页面的集合
	 */
	private ArrayList<BasePage> pages = new ArrayList<BasePage>();
	@ViewInject(R.id.viewpager)
	private CustomViewPager viewPager;
	@ViewInject(R.id.main_radio)
	public RadioGroup mainRg;
	private int curCheckId = R.id.rb_function;
	
	//private int curCheckId = R.id.rb_smart_service;
	
	private int curIndex;
	/**
	 * 底部菜单
	 */
	private MenuFragment menuFragment;
	@Override
	protected void initData(Bundle savedInstanceState) {
		menuFragment = (MenuFragment) ((MainActivity) getActivity())
				.getSupportFragmentManager().findFragmentByTag("Menu");
		
		
		pages.add(new FunctionPage(ct));
		pages.add(new NewsCenterPage(ct));
		pages.add(new SmartServicePage(ct));
		pages.add(new GovAffairsPage(ct));
		pages.add(new SettingPage(ct));
		
		
		
		adapter = new HomePagerAdapter(ct, pages);
		viewPager.setAdapter(adapter);
		viewPager.setScrollable(false);
		//不进行预加载
		viewPager.setOffscreenPageLimit(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				BasePage page = pages.get(position);
 				LogUtils.i(page+"---------------------------------"+position);
				if (!page.isLoadSuccess) {
					page.initData();
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
		pages.get(0).initData();
		viewPager.setCurrentItem(0);
		mainRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				LogUtils.d(checkedId + "");

				switch (checkedId) {
				case R.id.rb_function:
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					curIndex=0;
					viewPager.setCurrentItem(0, false);
					break;
				case R.id.rb_news_center:
					//全屏可以滑动出来菜单
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					NewsCenterPage page = (NewsCenterPage) pages.get(1);
//					page.refreshSmMode();
					pages.get(1).onResume();
					curIndex=1;
					viewPager.setCurrentItem(1, false);
					if (menuFragment != null) {
						menuFragment.setMenuType(MenuFragment.NEWS_CENTER);
						// sm.showMenu();
					}
					break;
				case R.id.rb_smart_service:
					curIndex=2;
					viewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_gov_affairs:
					curIndex=3;
					viewPager.setCurrentItem(3, false);
					break;
				case R.id.rb_setting:
					sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					curIndex=4;
					viewPager.setCurrentItem(4, false);
					break;
				default:
					break;
				}
				curCheckId = checkedId;

			}
		});
		mainRg.check(curCheckId);
	}
	public NewsCenterPage getNewsCenterPage() {
		NewsCenterPage page =(NewsCenterPage) pages.get(1);
		return page;

	}
	/**
	 * Viewpager适配器 适配页面与底部的RadioGroup
	 * @author longtaoge
	 *
	 */
	class HomePagerAdapter extends PagerAdapter {
		private Context mContext;
		private ArrayList<BasePage> pages;
		public HomePagerAdapter(Context ct, ArrayList<BasePage> pages) {
			this.mContext = ct;
			this.pages = pages;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg1 == arg0;
		}
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((CustomViewPager) container).removeView(pages.get(position)
					.getContentView());
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
			((CustomViewPager) arg0).addView(pages.get(arg1).getContentView(), 0);
			return pages.get(arg1).getContentView();
		}

	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub

	}

}
