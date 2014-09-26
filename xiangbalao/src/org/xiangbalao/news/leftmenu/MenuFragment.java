package org.xiangbalao.news.leftmenu;

import java.util.ArrayList;
import java.util.List;

import org.xiangbalao.news.MainActivity;
import org.xiangbalao.news.R;
import org.xiangbalao.news.R.color;
import org.xiangbalao.news.R.drawable;
import org.xiangbalao.news.R.id;
import org.xiangbalao.news.R.layout;
import org.xiangbalao.news.base.BaseFragment;
import org.xiangbalao.news.base.XblBaseAdapter;
import org.xiangbalao.news.bottommenu.HomeFragment2;
import org.xiangbalao.news.bottommenu.NewsCenterPage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings.System;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

/**
 * 侧栏菜单
 * @author longtaoge
 *
 */
public class MenuFragment extends BaseFragment {
	public static final int NEWS_CENTER = 1;

	/**
	 * 设置左侧菜单栏的背景等
	 */
	@Override
	protected View initView(LayoutInflater inflater) {
		/**
		 * 侧栏菜单的组件
		 */
		View view = inflater.inflate(R.layout.layout_left_menu, null);

		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		act = (MainActivity) ct;
		switchMenu(menuType);

	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub
		
		

	}
	/**
	 * 侧栏菜单的索引
	 */
	private int menuType = 0;
	public static int newsCenterPosition = 0;
	private MenuAdapter newsCenterAdapter = null;
	private ArrayList<String> newsCenterMenu = new ArrayList<String>();
	
	
	
	@ViewInject(R.id.lv_menu_news_center)
	private ListView newsCenterclassifyLv;
	@ViewInject(R.id.lv_menu_smart_service)
	private ListView smartServiceclassifyLv;
	@ViewInject(R.id.lv_menu_govaffairs)
	private ListView govAffairsclassifyLv;

	/**
	 * 初始化新闻左侧菜单
	 * 接收一个列表
	 * @param menuList
	 */
	public void initNewsCenterMenu(ArrayList<String> menuList) {
		
		newsCenterMenu.clear();
		newsCenterMenu.addAll(menuList);
		if (newsCenterAdapter == null) {
			newsCenterAdapter = new MenuAdapter(ct, newsCenterMenu);
			newsCenterclassifyLv.setAdapter(newsCenterAdapter);
			
			
		} else {
			
			
			newsCenterAdapter.notifyDataSetChanged();
		}
		newsCenterAdapter.setSelectedPosition(newsCenterPosition);

	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
		switchMenu(menuType);
		
	}
	/**
	 * 上下文
	 */
	private MainActivity act;
	/**
	 *  FragmentManager
	 * 得到Activity中存在的fragment：
	 */
	private FragmentManager fragManager;
	/**
	 * 新闻中心的页面
	 */
	private NewsCenterPage newsCenterFragment;
	/**
	 * 分类菜单
	 */
	@ViewInject(R.id.tv_menu_classify)
	private TextView classifyTv;

	public void switchMenu(int type) {
		newsCenterclassifyLv.setVisibility(View.GONE);
		smartServiceclassifyLv.setVisibility(View.GONE);
		govAffairsclassifyLv.setVisibility(View.GONE);
	
		switch (type) {
		case NEWS_CENTER:
			newsCenterclassifyLv.setVisibility(View.VISIBLE);
			fragManager = act.getSupportFragmentManager();
			newsCenterFragment = ((HomeFragment2) fragManager
					.findFragmentByTag("Home")).getNewsCenterPage();
			classifyTv.setText("分类");
			if (newsCenterAdapter == null) {
				newsCenterAdapter = new MenuAdapter(ct, newsCenterMenu);
				newsCenterclassifyLv.setAdapter(newsCenterAdapter);
			} else {
				newsCenterAdapter.notifyDataSetChanged();
			}
			newsCenterAdapter.setSelectedPosition(newsCenterPosition);

			break;
	
		default:
			break;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("newsCenter_position", newsCenterPosition);
		super.onSaveInstanceState(outState);
	}

	@OnItemClick(R.id.lv_menu_news_center)
	public void onNewsCenterItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		// 当前位置等于点击位置直接切换
		if (position == newsCenterPosition) {
			sm.toggle();
			return;
		}
	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null
				&& savedInstanceState.containsKey("newsCenter_position")) {
			newsCenterPosition = savedInstanceState
					.getInt("newsCenter_position");
		}

		super.onCreate(savedInstanceState);
	}

	/**
	 * 
	 * 左侧菜单适配器
	 * @author longtaoge
	 *
	 */
	
	class MenuAdapter extends XblBaseAdapter<String, ListView> {
		/**
		 * 选中的位置
		 */
		private int selectedPosition = 0;// 选中的位置

		public MenuAdapter(Context context, List<String> list) {
			super(context, list);
			// TODO Auto-generated constructor stub
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
			notifyDataSetInvalidated();

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ct, R.layout.layout_item_menu, null);
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.tv_menu_item);
			ImageView iv = (ImageView) convertView
					.findViewById(R.id.iv_menu_item);
			tv.setText(list.get(position));
			if (selectedPosition == position) {
				convertView.setSelected(true);
				convertView.setPressed(true);
				/**
				 * 修改左侧菜单项的选中时的背景色
				 */
			convertView.setBackgroundResource(R.drawable.menu_item_bg_select);
				tv.setTextColor(ct.getResources().getColor(
						R.color.menu_item_text_color));
				iv.setBackgroundResource(R.drawable.menu_arr_select);
			} else {
				convertView.setSelected(false);
				convertView.setPressed(false);
				convertView.setBackgroundColor(Color.RED);
				//convertView.setBackgroundColor(Color.TRANSPARENT);
				iv.setBackgroundResource(R.drawable.menu_arr_normal);
				tv.setTextColor(ct.getResources().getColor(R.color.white));
			}
			return convertView;
		}

	}
}
