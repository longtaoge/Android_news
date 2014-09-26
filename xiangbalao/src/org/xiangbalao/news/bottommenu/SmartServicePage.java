package org.xiangbalao.news.bottommenu;

import java.util.ArrayList;
import java.util.jar.Pack200;

import org.xiangbalao.news.MainActivity;
import org.xiangbalao.news.NewsPage;
import org.xiangbalao.news.R;
import org.xiangbalao.news.base.BasePage;
import org.xiangbalao.news.bean.NewsCenterCategories;
import org.xiangbalao.news.bean.NewsCenterCategories.NewsCategory;
import org.xiangbalao.news.leftmenu.MenuFragment;
import org.xiangbalao.news.utils.GsonTools;
import org.xiangbalao.news.utils.QLApi;
import org.xiangbalao.news.utils.QLParser;
import org.xiangbalao.news.utils.SharePrefUtil;
import org.xiangbalao.news.utils.SharePrefUtil.KEY;

import android.R.string;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 智慧服务
 * @author longtaoge
 *
 */
public class SmartServicePage extends BasePage {

	public SmartServicePage(Context context) {
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

	
		// TODO Auto-generated method stub
		
	}


