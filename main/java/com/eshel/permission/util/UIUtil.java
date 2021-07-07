package com.eshel.permission.util;
import android.content.Context;
/**
 * createBy Eshel
 * createTime: 2017/10/5 01:46
 */

public class UIUtil {
	public static String getString(Context context,int resId){
		return context.getApplicationContext().getResources().getString(resId);
	}
}
