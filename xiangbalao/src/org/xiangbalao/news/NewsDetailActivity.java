package org.xiangbalao.news;
import org.xiangbalao.news.base.BaseActivity;


import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 新联详情
 * @author longtaoge
 *
 */
public class NewsDetailActivity extends BaseActivity {
	@ViewInject(R.id.news_detail_wv)
	private WebView mWebView;
	private WebSettings settings;
	private ImageButton textSizeBtn;

	@Override
	protected void initView() {
		setContentView(R.layout.act_news_detail);
		initTitleBar();
		ViewUtils.inject(this);
		rightBtn.setImageResource(R.drawable.icon_share);
		rightBtn.setVisibility(View.VISIBLE);
		rightBtn.setOnClickListener(this);
	}

	private String url;
	private String title;

	@Override
	protected void initData() {
		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		dealNewsDetail();

	}

	public void loadurl(final WebView view, final String url) {
		view.loadUrl(url);
	}

	private void dealNewsDetail() {
		settings = mWebView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setLoadWithOverviewMode(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				loadurl(view, url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.e("onPageStarted", "");
				showLoadingView();
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				Log.e("onPageFinished", "");
				dismissLoadingView();
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(ct, "加载失败，请检查网络", 0).show();
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		loadurl(mWebView, url);
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_right:
			// 分享
			showShare();
			break;

		default:
			break;
		}

	}

	private void showShare() {
		Toast.makeText(ct, "分享", 0).show();
//		OnekeyShare oks = new OnekeyShare();
//		oks.setNotification(R.drawable.icon,
//				ct.getString(R.string.app_name));
//
//		oks.setTitle(title);
//		oks.setText("分享新闻:"+title);
//		if(!TextUtils.isEmpty(imgUrl)){
//			oks.setImageUrl(imgUrl);
//		}else{
//			oks.setImageUrl(Constants.APP_ICON_URL);
//		}
//		oks.setUrl(url);
//		oks.show(ct);
	}

}
