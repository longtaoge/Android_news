package org.xiangbalao.news.bean;

import java.util.ArrayList;

public class FunctionList extends BaseBean {
	public ArrayList<Function> data;

	public static class Function {
		public String id;
		public String title;
		public ArrayList<FunctionItem> items;
	}

	public static class FunctionItem {
		public String id;
		public String title;
		public String url;
		public String fiticon;
		public String fitselect;
		public String fitsetting;
		public String fitscreen;
		public boolean isSelected;

		public int hashCode() {

			return id.hashCode(); // 这种写法重码率高，不推荐使用

		}

		public boolean equals(Object obj) {

			if (obj instanceof FunctionItem) { //

				FunctionItem f = (FunctionItem) obj;

				return id.equals(f.id) ;

			}
			return false;
		}
	}

}
