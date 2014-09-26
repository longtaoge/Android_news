package org.xiangbalao.news;

import org.apache.http.cookie.SM;
import org.xiangbalao.news.bottommenu.HomeFragment2;
import org.xiangbalao.news.leftmenu.MenuFragment;

import android.os.Bundle;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	
	/**
	 * 侧栏菜单
	 */
	private MenuFragment mMenuFragment;
	/**
	 * 底部菜单
	 */
	private HomeFragment2 mHomeFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/**
		 * 菜单框架布局
		 */
		setBehindContentView(R.layout.menu_frame);
		/**
		 * 内容框架布局
		 */
		setContentView(R.layout.content_frame);
		
		/**
		 * SlidingMenu 
		 * 滑动菜单对象
		 */
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		if(savedInstanceState==null){
			mMenuFragment = new MenuFragment();
			mHomeFragment =new HomeFragment2();
			getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, mMenuFragment,"Menu").commit();
			
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame,mHomeFragment ,"Home").commit();
			
			
			
			
			
		}
		/**
		 * 滑动模式
		 */
		sm.setMode(SlidingMenu.LEFT);
		//sm.setMode(SlidingMenu.ABOVE);
		//sm.setMode(SlidingMenu.RIGHT);
	}


	
	
	
	
	/**
	 * 为左侧菜单赋值
	 * @return 左侧菜单
	 */
	public MenuFragment getMenuFragment(){
		mMenuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("Menu");
		return mMenuFragment;
		
	}
}
