package org.xiangbalao.news.base;

import org.xiangbalao.news.MainActivity;
import org.xiangbalao.news.R;
import org.xiangbalao.news.utils.CommonUtil;
import org.xiangbalao.news.utils.CustomToast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 基本页面，包含头部的控件
 * @author longtaoge
 *
 */
public abstract class BasePage implements OnClickListener {
	/**
	 * 上下文
	 */
	protected Context ct;
	/**
	 * 中间内容
	 */
	protected View contentView;
	/**
	 * 左侧按钮
	 */
	protected Button leftBtn;
	/**
	 * 右侧按钮
	 */
	protected ImageButton rightBtn;
	/**
	 * 左侧第图标
	 */
	protected ImageButton leftImgBtn;
	/**
	 * 右侧图标
	 */
	protected ImageButton rightImgBtn;
	/**
	 * 中间标题
	 */
	protected TextView titleTv;
	/**
	 * 侧滑菜单
	 */
	protected SlidingMenu sm;
	
	/**
	 * 登陆不成功时
	 */
	protected LinearLayout loadfailView;
	
	public boolean isLoadSuccess=false;
	
	//********************************************************//
	
	
	
	/**
	 * 构造方法
	 * @param context
	 */
	public BasePage(Context context) {
		ct = context;
		contentView = initView((LayoutInflater) ct
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));//layout_inflater
		loadingView = contentView.findViewById(R.id.loading_view);
		loadfailView = (LinearLayout) contentView
				.findViewById(R.id.ll_load_fail);
	   if(ct instanceof MainActivity){
			sm = ((MainActivity) ct).getSlidingMenu();
	   }
	}
	/**
	 * 初始化 顶部布局
	 * @param view
	 */
	protected void initTitleBar(View view) {
		
		leftBtn = (Button) view.findViewById(R.id.btn_left);
	
		rightBtn = (ImageButton) view.findViewById(R.id.btn_right);
	
		leftImgBtn = (ImageButton) view.findViewById(R.id.imgbtn_left);
	
		rightImgBtn = (ImageButton) view.findViewById(R.id.imgbtn_right);
		
		/**
		 * 设置图片
		 */
		leftImgBtn.setImageResource(R.drawable.img_menu);
	
		titleTv = (TextView) view.findViewById(R.id.txt_title);
		leftBtn.setVisibility(View.GONE);
		rightBtn.setVisibility(View.GONE);
		if(leftImgBtn!=null)
		leftImgBtn.setOnClickListener(this);

	}
	public View getContentView() {
		return contentView;
	}
	/**
	 * 数据加载成功时显示的布局
	 */
	@ViewInject(R.id.loading_view)
	protected View loadingView;
	public void dismissLoadingView() {
		if (loadingView != null)
			loadingView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 初始化布局
	 * @param inflater
	 * @return
	 */
	protected abstract View initView(LayoutInflater inflater);

	/**
	 * 初始化数据
	 */
	public abstract void initData();
	/**
	 * 设置监听
	 * @param v
	 */
	protected abstract void processClick(View v);

	public void onResume() {

	}
	public void showToast(String msg) {
		showToast(msg, 0);
	}
	public void showToast(String msg, int time) {
		CustomToast customToast = new CustomToast(ct, msg, time);
		customToast.show();
	}
	protected void loadData(HttpRequest.HttpMethod method, String url,
			RequestParams params, RequestCallBack<String> callback) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 1);
		LogUtils.allowD = true;
		if (params != null) {
			if (params.getQueryStringParams() != null)
				LogUtils.d(url + params.getQueryStringParams().toString());
		} else {
			params = new RequestParams();
		}
		//设备ID
//		params.addHeader("x-deviceid", app.deviceId);
		//渠道，统计用
//		params.addHeader("x-channel", app.channel);
		if (0 == CommonUtil.isNetworkAvailable(ct)) {
			showToast("无网络，请检查网络连接！");
		} else {
			http.send(method, url, params, callback);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_left:
			sm.toggle();
			break;

		default:
			break;
		}
		
	}
}
