package org.xiangbalao.news.bottommenu;

import org.xiangbalao.news.base.BasePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class FunctionPage extends BasePage {

	public FunctionPage(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View initView(LayoutInflater inflater) {
		TextView textView = new TextView(ct);
		textView.setText("这里添加首页");
		return textView;
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
