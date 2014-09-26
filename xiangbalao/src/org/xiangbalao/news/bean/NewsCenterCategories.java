package org.xiangbalao.news.bean;

import java.util.ArrayList;

/**
 * 新闻中心
 * 分类 javaBean
 * @author longtaoge
 *
 */
public class NewsCenterCategories extends BaseBean {
	public ArrayList<NewsCategory> data;
	public int[] extend;
	/**
	 * 新闻种类
	 * @author longtaoge
	 *
	 */
	public static class NewsCategory{
		public int id;
		public String title = "";
		public int type;
		public String url = "";
		public String url1 ="";
		public String excurl;
		public String weekurl;
		public String dayurl;
		public ArrayList<ChildNewsCate> children = new ArrayList<NewsCenterCategories.ChildNewsCate>();
		


	}

	public static class ChildNewsCate{
		public int id;
		public String title = "";
		public int type;
		public String url = "";


	}

}
