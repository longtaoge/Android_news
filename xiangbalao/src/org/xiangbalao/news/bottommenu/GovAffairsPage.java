package org.xiangbalao.news.bottommenu;

import org.xiangbalao.news.base.BasePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;


/**
 * 政务指南
 * @author longtaoge
 *
 */
public class GovAffairsPage extends BasePage {

	public GovAffairsPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected View initView(LayoutInflater inflater) {
		TextView textView = new TextView(ct);
		WebView view=new WebView(ct);
	String url="http://www.xiangbalao.org";
	view.loadUrl(url);
		textView.setText("政务指南页面");
		
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
