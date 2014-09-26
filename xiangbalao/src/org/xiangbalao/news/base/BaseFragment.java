package org.xiangbalao.news.base;

import org.xiangbalao.news.MainActivity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 基类 Fragment 轻量级的Activity
 * 包含一个 滑动菜单
 * SlidingMenu
 * 
 * @author longtaoge
 * 
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {
	/**
	 * 上下文
	 */
	protected Context ct;
	/**
	 * SlidingMenu 滑动菜单
	 */
	protected SlidingMenu sm;
	/**
	 * 初始化的View
	 */
	public View rootView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		sm = ((MainActivity) getActivity()).getSlidingMenu();
		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		ct = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = initView(inflater);
		return rootView;
	}

	public View getRootView() {
		return rootView;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}


	/**
	 * 强制子类初始化 View
	 * 
	 * @param inflater
	 * @return 子类View对象
	 */
	protected abstract View initView(LayoutInflater inflater);

	/**
	 * 初始化数据
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void initData(Bundle savedInstanceState);

	/**
	 * 处理监听事件
	 * 
	 * @param View
	 */
	protected abstract void processClick(View v);
}
