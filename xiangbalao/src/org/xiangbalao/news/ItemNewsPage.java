package org.xiangbalao.news;


import java.util.ArrayList;
import java.util.HashSet;

import org.xiangbalao.news.adapter.NewsAdapter;
import org.xiangbalao.news.base.BasePage;
import org.xiangbalao.news.bean.CountList;
import org.xiangbalao.news.bean.NewsListBean;
import org.xiangbalao.news.bean.NewsListBean.News;
import org.xiangbalao.news.bean.NewsListBean.TopNews;
import org.xiangbalao.news.utils.CommonUtil;
import org.xiangbalao.news.utils.Constants;
import org.xiangbalao.news.utils.QLParser;
import org.xiangbalao.news.utils.SharePrefUtil;
import org.xiangbalao.news.view.RollViewPager;
import org.xiangbalao.news.view.RollViewPager.OnPagerClickCallback;
import org.xiangbalao.news.view.pullrefreshview.PullToRefreshBase;
import org.xiangbalao.news.view.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import org.xiangbalao.news.view.pullrefreshview.PullToRefreshListView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ItemNewsPage extends BasePage {
	@ViewInject(R.id.lv_item_news)
	private PullToRefreshListView ptrLv;
	@ViewInject(R.id.top_news_title)
	private TextView topNewsTitle;
	@ViewInject(R.id.top_news_viewpager)
	private LinearLayout mViewPagerLay;
	@ViewInject(R.id.dots_ll)
	private LinearLayout dotLl;
	private View topNewsView;
	private String url;
	private String moreUrl;
	private ArrayList<News> news = new ArrayList<NewsListBean.News>();
	private ArrayList<TopNews> topNews;
	private NewsAdapter adapter;
	private ArrayList<View> dotList;
	private ArrayList<String> titleList, urlList;
	private HashSet<String> readSet = new HashSet<String>();
	private String  hasReadIds;
	private RollViewPager mViewPager;
	public boolean isLoadSuccess;
	private String countCommentUrl;

	public ItemNewsPage(Context context, String url) {
		super(context);
		this.url = url;
	}

	@Override
	protected View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_item_news, null);
		topNewsView = inflater.inflate(R.layout.layout_roll_view, null);
		ViewUtils.inject(this, view);
		ViewUtils.inject(this, topNewsView);
		// 上拉加载不可用 
		ptrLv.setPullLoadEnabled(false);
		 // 滚动到底自动加载可用  
		ptrLv.setScrollLoadEnabled(true);
		// 得到实际的ListView  设置点击
		ptrLv.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(ct, NewsDetailActivity.class);
						String url = "";
						String title;
						/**
						 * 单条新闻的Item
						 */
						News newsItem;
						if (ptrLv.getRefreshableView().getHeaderViewsCount() > 0) {
							newsItem = news.get(position - 1);
						} else {
							newsItem = news.get(position);
						}
						url = newsItem.url;
						if(!newsItem.isRead){
							readSet.add(newsItem.id);
							newsItem.isRead= true;
							SharePrefUtil.saveString(ct, Constants.READ_NEWS_IDS, hasReadIds+","+newsItem.id);
						}
						
						title = newsItem.title;
						intent.putExtra("url", url);
						intent.putExtra("title", title);
						ct.startActivity(intent);

					}
				});
		setLastUpdateTime();
		// 设置下拉刷新的listener  
		ptrLv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewsList(url, true);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewsList(moreUrl, false);

			}
		});
		return view;
	}

	@Override
	public void initData() {
		hasReadIds = SharePrefUtil.getString(ct, Constants.READ_NEWS_IDS, "");
		String[] ids =hasReadIds.split(",");
		for(String id : ids){
			readSet.add(id);
		}
		if (!TextUtils.isEmpty(url)) {
			String result = SharePrefUtil.getString(ct, url, "");
			if(!TextUtils.isEmpty(result)){
				processDataFromCache(true, result);
			}
			getNewsList(url, true);
		}

	}

	private void getNewsList(final String loadUrl, final boolean isRefresh) {
		loadData(HttpMethod.GET, loadUrl, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> info) {
				LogUtils.d("response_json---" + info.result);
				if(isRefresh){
					SharePrefUtil.saveString(ct, url, info.result);
				}
				processData(isRefresh, info.result);

			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				LogUtils.d("fail_json---" + arg1);
				onLoaded();

			}
		});
	}

	private void getNewsCommentCount(String countcommenturl,
			final ArrayList<News> newsList, final boolean isRefresh) {
		StringBuffer sb = new StringBuffer(countcommenturl);
		for (News news : newsList) {
			sb.append(news.id + ",");
		}
		loadData(HttpMethod.GET, sb.toString(), null,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> info) {
						LogUtils.d("response_json---" + info.result);
						CountList countList = QLParser.parse(info.result,
								CountList.class);
						for (News news : newsList) {
							news.commentcount = countList.data
									.get(news.id + "");
							if(readSet.contains(news.id)){
								news.isRead= true;
							}else{
								news.isRead = false;
							}
						}
						if (isRefresh) {
							news.clear();
							news.addAll(newsList);
						} else {
							news.addAll(newsList);
						}
						
						if (adapter == null) {
							adapter = new NewsAdapter(ct, news, 0);
							ptrLv.getRefreshableView().setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}
						onLoaded();
						LogUtils.d("moreUrl---" + moreUrl);
						if (TextUtils.isEmpty(moreUrl)) {
							ptrLv.setHasMoreData(false);
						} else {
							ptrLv.setHasMoreData(true);
						}
						setLastUpdateTime();

					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						LogUtils.d("fail_json---" + arg1);
						onLoaded();
					}
				});

	}

	@Override
	protected void processClick(View v) {

	}

	private void onLoaded() {
		dismissLoadingView();
		ptrLv.onPullDownRefreshComplete();
		ptrLv.onPullUpRefreshComplete();
	}

	private void initDot(int size) {
		dotList = new ArrayList<View>();
		dotLl.removeAllViews();
		for (int i = 0; i < size; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					CommonUtil.dip2px(ct, 6), CommonUtil.dip2px(ct, 6));
			params.setMargins(5, 0, 5, 0);
			View m = new View(ct);
			if (i == 0) {
				m.setBackgroundResource(R.drawable.dot_focus);
			} else {
				m.setBackgroundResource(R.drawable.dot_normal);
			}
			m.setLayoutParams(params);
			dotLl.addView(m);
			dotList.add(m);
		}
	}

	private void setLastUpdateTime() {
		String text = CommonUtil.getStringDate();
		ptrLv.setLastUpdatedLabel(text);
	}

	public void processData(final boolean isRefresh, String result) {
		NewsListBean newsList = QLParser.parse(result, NewsListBean.class);
		if (newsList.retcode != 200) {
		} else {
			isLoadSuccess = true;
			countCommentUrl = newsList.data.countcommenturl;
			if (isRefresh) {
				topNews = newsList.data.topnews;
				if (topNews != null) {
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					for (TopNews news : topNews) {
						titleList.add(news.title);
						urlList.add(news.topimage);
					}
					initDot(topNews.size());
					mViewPager = new RollViewPager(ct, dotList,
							R.drawable.dot_focus, R.drawable.dot_normal,
							new OnPagerClickCallback() {
								@Override
								public void onPagerClick(int position) {
									TopNews news = topNews.get(position);
									if (news.type.equals("news")) {
										Intent intent = new Intent(ct,
												NewsDetailActivity.class);
										String url = topNews.get(position).url;
										String title = topNews.get(position).title;
										intent.putExtra("url", url);
										intent.putExtra("title", title);
										ct.startActivity(intent);
									} else if (news.type.equals("topic")) {
										
									}
								}
							});
					mViewPager.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					//top新闻的图片地址
					mViewPager.setUriList(urlList);
					mViewPager.setTitle(topNewsTitle, titleList);
					mViewPager.startRoll();
					mViewPagerLay.removeAllViews();
					mViewPagerLay.addView(mViewPager);
					if (ptrLv.getRefreshableView().getHeaderViewsCount() < 1) {
						ptrLv.getRefreshableView().addHeaderView(topNewsView);
					} 
				}
			} 
			moreUrl = newsList.data.more;
			if (newsList.data.news != null) {
				getNewsCommentCount(newsList.data.countcommenturl,
						newsList.data.news, isRefresh);
			}
		}
	}
	
	public void processDataFromCache(boolean isRefresh, String result) {
		NewsListBean newsList = QLParser.parse(result, NewsListBean.class);
		if (newsList.retcode != 200) {
		} else {
			isLoadSuccess = true;
			countCommentUrl = newsList.data.countcommenturl;
			if (isRefresh) {
				topNews = newsList.data.topnews;
				if (topNews != null) {
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					for (TopNews news : topNews) {
						titleList.add(news.title);
						urlList.add(news.topimage);
					}
					initDot(topNews.size());
					mViewPager = new RollViewPager(ct, dotList,
							R.drawable.dot_focus, R.drawable.dot_normal,
							new OnPagerClickCallback() {
								@Override
								public void onPagerClick(int position) {
									TopNews news = topNews.get(position);
									if (news.type.equals("news")) {
										Intent intent = new Intent(ct,
												NewsDetailActivity.class);
										String url = topNews.get(position).url;
										String commentUrl = topNews
												.get(position).commenturl;
										String newsId = topNews.get(position).id;
										String commentListUrl = topNews
												.get(position).commentlist;
										String title = topNews.get(position).title;
										String imgUrl = topNews.get(position).topimage;
										boolean comment = topNews.get(position).comment;
										intent.putExtra("url", url);
										intent.putExtra("commentUrl",
												commentUrl);
										intent.putExtra("newsId", newsId);
										intent.putExtra("imgUrl", imgUrl);
										intent.putExtra("title", title);
										intent.putExtra("comment", comment);
										intent.putExtra("countCommentUrl",
												countCommentUrl);
										intent.putExtra("commentListUrl",
												commentListUrl);
										ct.startActivity(intent);
									} else if (news.type.equals("topic")) {
									
									}
								}
							});
					mViewPager.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					mViewPager.setUriList(urlList);
					mViewPager.setTitle(topNewsTitle, titleList);
					mViewPager.startRoll();
					mViewPagerLay.removeAllViews();
					mViewPagerLay.addView(mViewPager);
					if (ptrLv.getRefreshableView().getHeaderViewsCount() < 1) {
						ptrLv.getRefreshableView().addHeaderView(topNewsView);
					} 
				}
			} 
			moreUrl = newsList.data.more;
			if (isRefresh) {
				news.clear();
				news.addAll(newsList.data.news);
			} else {
				news.addAll(newsList.data.news);
			}
			for (News newsItem : news) {
				if(readSet.contains(newsItem.id)){
					newsItem.isRead= true;
				}else{
					newsItem.isRead = false;
				}
			}
			if (adapter == null) {
				adapter = new NewsAdapter(ct, news, 0);
				ptrLv.getRefreshableView().setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
			onLoaded();
			LogUtils.d("moreUrl---" + moreUrl);
			if (TextUtils.isEmpty(moreUrl)) {
				ptrLv.setHasMoreData(false);
			} else {
				ptrLv.setHasMoreData(true);
			}
			setLastUpdateTime();
		}
	}
	

}
