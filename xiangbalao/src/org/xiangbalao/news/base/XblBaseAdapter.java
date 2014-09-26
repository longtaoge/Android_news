package org.xiangbalao.news.base;

import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;


/**
 * Adapter基类
 * 
 * @author longtaoge
 *
 * @param <T> 新闻列表泛型
 * @param <Q> Item的类型，泛型
 */
public abstract class XblBaseAdapter<T, Q> extends BaseAdapter {

	public Context context;
	public List<T> list;//
	public Q view; // 这里不一定是ListView,比如GridView,CustomListView


	/**
	 * 构造
	 * @param context
	 * @param list
	 * @param view
	 */
	public XblBaseAdapter(Context context, List<T> list, Q view) {
		this.context = context;
		this.list = list;
		this.view = view;
	}

	/**
	 * 构造 
	 * @param context
	 * @param list
	 */
	public XblBaseAdapter(Context context, List<T> list) {
		this.context = context;
		this.list = list;
		
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	


}
