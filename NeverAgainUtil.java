package com.example.permissiondemo.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by guoshiwen on 2017/9/5.
 */

public class NeverAgainUtil {
	public static NeverAgainUtil neverAgainUtil;
	public static NeverAgainUtil newInstance() {
		if(neverAgainUtil == null){
			synchronized (NeverAgainUtil.class) {
				if(neverAgainUtil == null)
					neverAgainUtil = new NeverAgainUtil();
			}
		}
		return neverAgainUtil;
	}
	public void gotoPermissionSettingUI(Activity activity,int requestCode){
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package",activity.getPackageName(),null);
		intent.setData(uri);
		activity.startActivityForResult(intent,requestCode);
	}
}
