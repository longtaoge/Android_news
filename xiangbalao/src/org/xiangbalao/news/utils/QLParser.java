package org.xiangbalao.news.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xiangbalao.news.bean.BaseBean;

import android.text.TextUtils;


public class QLParser {
	public static <T extends BaseBean> T parse(String jsonString, Class<T> cls) {
		if (jsonString != null) {
			String json = beforeParse(jsonString);
			if (!TextUtils.isEmpty(json))
				return GsonTools.changeGsonToBean(json, cls);
		}
		return null;

	}

	private static String beforeParse(String jsonString) {
		int code;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			code = jsonObject.getInt("retcode");
			// if(code==200){
			return jsonString;
			// }
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
