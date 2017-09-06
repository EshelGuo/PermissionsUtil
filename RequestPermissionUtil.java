package com.example.permissiondemo.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * 创建人: Eshel
 * 创建时间:2017/7/12 19时02分
 * 描述: TODO
 */

public class RequestPermissionUtil {
	/**
	 *	@param permission Manifest.permission.***
	 * @return true 代表用户已经授权 false 代表正在请求权限中, 请求权限需要时间, 所以不能立即返回结果
	 */
	public static boolean requestPermission(Activity activity,String permission,int requestCode){
		//检查权限: 检查用户是不是已经授权
		int checkSelfPermission = ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission);
		//拒绝 : 检查到用户之前拒绝授权
		if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
			//申请权限
			ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
		}else if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
			//已经授权
			return true;
		}else {
			ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
		}
		return false;
	}

	public static void onRequestPermissionsResult(Activity activity,int requestCode, String[] permissions, int[] grantResults){
		for (int i = 0; i < grantResults.length; i++) {
			if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
				//user Permission Granted (yong hu shou quan)
				Permissions.changePermissionState(activity,requestCode,permissions[i],true);
			}else if(grantResults[i] == PackageManager.PERMISSION_DENIED){
				// user Permission Denied (yong hu ju jue shou quan)
				Log.i("permission","用户拒绝");
				boolean isCallback = false;
				if(!ActivityCompat.shouldShowRequestPermissionRationale(activity,permissions[i])){
					Permission permission = Permission.findPermissionByName(permissions[i]);
					if(permission != null && permission.callback != null){
						boolean isStop = permission.callback.userSelectNeverAgain(permission, NeverAgainUtil.newInstance());
						if(isStop) {
							Permissions.isStop = true;
						}
						isCallback = true;
					}
				}else
					isCallback = false;
				Permissions.changePermissionState(activity,requestCode,permissions[i],false,isCallback);
			}

		}
	}
}
