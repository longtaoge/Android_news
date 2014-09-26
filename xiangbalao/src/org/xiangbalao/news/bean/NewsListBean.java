package org.xiangbalao.news.bean;

import java.util.ArrayList;


/**
 * 封装 新闻列表
 * @author longtaoge
 *
 */
public class NewsListBean extends BaseBean {
	public NewsList data;
	public static class NewsList{
		public String more;
		public ArrayList<TopNews> topnews;
		public ArrayList<News> news;
		public String countcommenturl;
	}
	
	public static class TopNews{
		public String id;
		public String title;
		public String topimage;
		public String url;
		public String pubdate;
		public boolean comment;
		public String commenturl;
		public String commentlist;
		public int commentcount;
		public String type;
	}
	
	/**
	 * 封装新闻信息的Bean
	 * @author longtaoge
	 *
	 */
	public static class News{
		public String id;
		public String title;
		public String url;
		public String listimage;
		public String smallimage;
		public String largeimage;
		public String pubdate;
		public int commentcount;
		public boolean comment;
		public String commenturl;
		public String commentlist;
		/**
		 * 判断是否读过，用于修改阅读后的背景
		 */
		public boolean isRead;
	}
}
