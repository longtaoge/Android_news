package org.xiangbalao.news.utils;




import android.app.Dialog;
import android.content.Context;

/**
 * 对话框创建工具类
 * 
 */
public class DialogUtil {


	/**
	 * 创建进度对话框
	 * @param context
	 * @param content
	 * @return
	 */
	public static Dialog createProgressDialog(Context context,String content){
		return new CustomProgressDialog(context, content);
	}
	
}
