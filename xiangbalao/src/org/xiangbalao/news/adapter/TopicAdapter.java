package org.xiangbalao.news.adapter;

import java.util.ArrayList;

import org.xiangbalao.news.R;
import org.xiangbalao.news.bean.TopicListBean.Topic;
import org.xiangbalao.news.utils.CommonUtil;
import org.xiangbalao.news.utils.Constants;
import org.xiangbalao.news.utils.SharePrefUtil;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

/**
 * 顶部大图 适配器
 * @author longtaoge
 *
 */
public class TopicAdapter extends BaseAdapter {
	private ArrayList<Topic> datas;
	private Context context;
	/**
	 * 图片处理工具
	 */
	private BitmapUtils bitmapUtils;
	private String countCommentUrl;
	int read_model;

	public TopicAdapter(Context context, ArrayList<Topic> datas,
			String countCommentUrl) {
		this.context = context;
		this.datas = datas;
		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		this.countCommentUrl = countCommentUrl;
		read_model = SharePrefUtil.getInt(context, Constants.READ_MODEL, 1);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_item_topic, null);
			holder.title = (TextView) convertView
					.findViewById(R.id.tv_topic_title);
			holder.topic_iv = (ImageView) convertView.findViewById(R.id.iv_topic);
			int width = context.getResources().getDisplayMetrics().widthPixels-CommonUtil.dip2px(context, 20);
			int height = width*320/640;
			LayoutParams ivLp = holder.topic_iv.getLayoutParams();
			ivLp.width = width;
			ivLp.height= height;
			holder.topic_iv.setLayoutParams(ivLp);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Topic topic = datas.get(position);
		holder.title.setText(topic.title);
		if (TextUtils.isEmpty(topic.listimage)) {
			holder.topic_iv.setVisibility(View.GONE);
		} else {
			switch (read_model) {
			case 1:
				int type = CommonUtil.isNetworkAvailable(context);
				if (type == 1) {
					holder.topic_iv.setVisibility(View.VISIBLE);
					bitmapUtils.display(holder.topic_iv,
							topic.listimage);
				} else {
					holder.topic_iv.setImageResource(R.drawable.transparent);
				}
				break;
			case 2:
				holder.topic_iv.setVisibility(View.VISIBLE);
				bitmapUtils.display(holder.topic_iv,
						topic.listimage);
				break;
			case 3:
				holder.topic_iv.setImageResource(R.drawable.transparent);
				break;

			default:
				break;
			}

		}
		return convertView;
	}

	public void notifyData() {
		read_model = SharePrefUtil.getInt(context, Constants.READ_MODEL, 1);
		notifyDataSetChanged();
	}
	class ViewHolder {
		TextView title;
		ImageView topic_iv;
	}

//	public class TopicDetailAdapter extends BaseAdapter {
//		private Context ct;
//		private List<TopicItemNews> news;
//
//		public TopicDetailAdapter(Context ct, List<TopicItemNews> news) {
//			this.ct = ct;
//			this.news = news;
//
//		}
//
//		@Override
//		public int getCount() {
//			return news.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return news.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(final int position, View convertView,
//				ViewGroup parent) {
//			ViewHolder holder;
//			LayoutInflater inflater = LayoutInflater.from(context);
//			if (convertView == null) {
//				holder = new ViewHolder();
//				convertView = inflater.inflate(
//						R.layout.layout_item_topic_detail, null);
//				holder.title = (TextView) convertView
//						.findViewById(R.id.tv_news_title);
//				holder.pub_time = (TextView) convertView
//						.findViewById(R.id.tv_pub_time);
//				holder.news_iv = (ImageView) convertView
//						.findViewById(R.id.iv_news);
//				int width = ct.getResources().getDisplayMetrics().widthPixels-CommonUtil.dip2px(ct, 20);
//				int height = width*360/600;
//				LayoutParams ivLp = holder.news_iv.getLayoutParams();
//				ivLp.width = width;
//				ivLp.height= height;
//				holder.news_iv.setLayoutParams(ivLp);
////				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
////						AbsListView.LayoutParams.FILL_PARENT,
////						CommonUtil.dip2px(ct, 150));
////				convertView.setLayoutParams(lp);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			convertView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context,
//							NewsDetailActivity.class);
//					String url = "";
//					String commentUrl = "";
//					String newsId;
//					TopicItemNews newsItem = news.get(position);
//					url = newsItem.url;
//					boolean comment = newsItem.comment;
//					commentUrl = newsItem.commenturl;
//					newsId = newsItem.id;
//					String title = newsItem.title;
//					String imgUrl = newsItem.listimage;
//					intent.putExtra("url", url);
//					intent.putExtra("commentUrl", commentUrl);
//					intent.putExtra("newsId", newsId);
//					intent.putExtra("imgUrl", imgUrl);
//					intent.putExtra("title", title);
//					intent.putExtra("countCommentUrl", countCommentUrl);
//					intent.putExtra("comment", comment);
//					intent.putExtra("commentListUrl", newsItem.commentlist);
//					ct.startActivity(intent);
//
//				}
//			});
//			holder.title.setText(news.get(position).title);
//			holder.pub_time.setText(news.get(position).pubdate);
//			if (TextUtils.isEmpty(news.get(position).listimage)) {
//				holder.news_iv.setVisibility(View.GONE);
//			} else {
//				switch (read_model) {
//				case 1:
//					int type = CommonUtil.isNetworkAvailable(context);
//					if (type == 1) {
//						holder.news_iv.setVisibility(View.VISIBLE);
//						bitmapUtils.display(holder.news_iv,
//								news.get(position).listimage);
//					} else {
//						holder.news_iv.setImageResource(R.drawable.transparent);
//					}
//					break;
//				case 2:
//					holder.news_iv.setVisibility(View.VISIBLE);
//					bitmapUtils.display(holder.news_iv,
//							news.get(position).listimage);
//					break;
//				case 3:
//					holder.news_iv.setImageResource(R.drawable.transparent);
//					break;
//
//				default:
//					break;
//				}
//
//			}
//			return convertView;
//		}
//
//		class ViewHolder {
//			TextView title;
//			TextView pub_time;
//			ImageView news_iv;
//		}
//
//	}


}
