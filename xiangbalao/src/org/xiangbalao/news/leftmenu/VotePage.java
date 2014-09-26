package org.xiangbalao.news.leftmenu;

import org.xiangbalao.news.base.BasePage;
import org.xiangbalao.news.bean.NewsCenterCategories.NewsCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 投票
 * @author longtaoge
 *
 */
public class VotePage extends BasePage {

	private NewsCategory category;

	public VotePage(Context ct, NewsCategory newsCategory) {
		super(ct);
		category = newsCategory;
	}

	@Override
	protected View initView(LayoutInflater inflater) {
//		View view = inflater.inflate(R.layout.frag_topic, null);
//		ViewUtils.inject(this, view);
		
		TextView view = new TextView(ct);
		view.setText("VotePage");

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
