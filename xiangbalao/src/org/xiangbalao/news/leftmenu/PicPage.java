package org.xiangbalao.news.leftmenu;

import org.xiangbalao.news.base.BasePage;
import org.xiangbalao.news.bean.NewsCenterCategories.NewsCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 组图
 * @author longtaoge
 *
 */
public class PicPage extends BasePage {


	private NewsCategory category;
	public PicPage(Context ct, NewsCategory newsCategory) {
		super(ct);
		category = newsCategory;
	}

	@Override
	protected View initView(LayoutInflater inflater) {
//		View view = inflater.inflate(R.layout.frag_news, null);
//		ViewUtils.inject(this, view);
		
		TextView view = new TextView(ct);
		view.setText("PicPage");
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
