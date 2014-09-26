package org.xiangbalao.news.bean;

import java.util.ArrayList;


public class TopicListBean extends BaseBean {
	public TopicList data;
	public static class TopicList{
		public String more;
		public ArrayList<Topic> topic;
//		public String countcommenturl;
	}
	
	public static class Topic{
		public String id;
		public String title;
		public String url;
		public String listimage;
		public String description;
//		public ArrayList<TopicItemNews> news;
//		public String countcommenturl;
	}
	
	
}
