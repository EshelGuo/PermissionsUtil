package com.example.permissiondemo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.example.permissiondemo.permission.NeverAgainUtil;
import com.example.permissiondemo.permission.Permission;
import com.example.permissiondemo.permission.Permissions;
import com.example.permissiondemo.permission.RequestCallback;

import java.util.HashMap;

/**
 * Created by GuoShiwen on 2017/9/6.
 */
public class PermissionUtil {
	static Activity activity;
	static RequestCallback callback = new RequestCallback() {
		DialogInterface.OnClickListener cancleClick = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO: 2017/9/6
				System.exit(0);
			}
		};
		public DialogInterface.OnClickListener confirmClick = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO: 2017/9/6
				Permissions.goBack(activity,callback);
			}
		};
		DialogInterface.OnClickListener gotoSettingClick = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//// TODO: 2017/9/6
				NeverAgainUtil.newInstance().gotoPermissionSettingUI(activity, 0);
			}
		};

		@Override
		public void requestSuccess(Permission permission) {
			Log.i("permission", "request permission(" + permission.permission + ") success");
		}

		@Override
		public boolean requestFailed(Permission permission) {
			Permissions.savePermission();
			new AlertDialog.Builder(activity)
					.setNegativeButton("取消", cancleClick)
					.setCancelable(false)
					.setMessage("该权限非常重要, 没有该权限游戏将无法运行 , 请确认是否授予该权限")
					.setTitle("警告")
					.setPositiveButton("再次授予权限", confirmClick)
					.show();
			return true;
		}

		@Override
		public void havePermissioned(Permission permission) {
		}

		@Override
		public boolean userSelectNeverAgain(Permission permission, NeverAgainUtil neverAgainUtil) {
			Permissions.savePermission();
			new AlertDialog.Builder(activity)
					.setNegativeButton("取消", cancleClick)
					.setCancelable(false)
					.setMessage("该权限非常重要, 没有该权限游戏将无法运行 , 请确认是否去设置中心授予该权限")
					.setTitle("警告")
					.setPositiveButton("取设置中心", gotoSettingClick)
					.show();
			return true;
		}
	};
	public static void requestPermission(Activity activity, String ... permission){
		PermissionUtil.activity = activity;
		Permissions.requestPermission(activity,callback ,permission);
	}
}