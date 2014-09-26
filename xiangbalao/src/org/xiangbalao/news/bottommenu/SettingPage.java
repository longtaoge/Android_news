package org.xiangbalao.news.bottommenu;

import org.xiangbalao.news.R;
import org.xiangbalao.news.base.BasePage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lidroid.xutils.ViewUtils;

/**
 * 设置中心
 * @author longtaoge
 *
 */

public class SettingPage extends BasePage {

	public SettingPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.news_center_frame, null);
		ViewUtils.inject(this, view);
		initTitleBar(view);
		return view;

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
