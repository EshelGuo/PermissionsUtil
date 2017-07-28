package com.eshel.takeout.permission;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: GooglePlay
 * 创建人: Eshel
 * 创建时间:2017/7/12 19时11分
 * 描述: 动态权限管理类
 * 该类会自动判断系统版本,如果高于23,则会动态申请权限
 * 在MainActivity onCreate中:
 * Permissions.requestPermissionAll(this);
 * 调用该方法将触发请求权限
 * 在MainActivity中 需要将以下内容复制到Activity 中, 作用是判断权限是否请求成功 :
 *
		@Override
		public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Permissions.changePermissionState(this,permissions[0],true);
			}
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
 */

public class Permissions {
	private static List<Permission> permissions = new ArrayList<>();
	public static boolean need;//需要动态申请权限
	static {
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
			need = true;
			initPermissions();
		}
	}
	public static void addPermission(Permission permission){
		if(need) {
			permissions.add(permission);
		}
	}
	/**
	 * 初始化权限
	 */
	private static void initPermissions() {
		if(need) {
			// TODO: 2017/7/28  在此声明所需 动态添加 权限
			addPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
			addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			addPermissions(
					Manifest.permission.ACCESS_COARSE_LOCATION,
					Manifest.permission.ACCESS_FINE_LOCATION,
					Manifest.permission.READ_PHONE_STATE
			);

		}
	}
private static int index = 0;
	/**
	 * 请求所有权限
	 * @param activity
	 */
	public static void requestPermissionAll(Activity activity){
		if(need) {
			if(index>=permissions.size())
				return;
			Permission permission = permissions.get(index);
			boolean succeed = RequestPermissionUtil.requestPermission(activity, permission.permission, permission.requestCode);
			permission.hasPermission = succeed;
			index++;
			if(succeed){
				requestPermissionAll(activity);
			}
		}
	}

	/**
	 * 改变权限的状态
	 * @param permission 权限名
	 * @param success 是否请求成功
	 */
	public static void changePermissionState(Activity activity, String permission, boolean success){
		if(need) {
			findPermission(permission).hasPermission = success;
		}
		requestPermissionAll(activity);
	}
	public static Permission findPermission(String permission){
		if(need) {
			for (Permission p : permissions) {
				if (p.permission.equals(permission)) {
					return p;
				}
			}
		}
		return addPermission(permission);
	}
	public static Permission addPermission(String permission){
		if(need) {
			Permission p = new Permission(permissions.size(), permission, false);
			permissions.add(p);
			return p;
		}
		return null;
	}
	public static void addPermissions(String ... permissions){
		for (String permission : permissions) {
			if(need) {
				Permission p = new Permission(Permissions.permissions.size(), permission, false);
				Permissions.permissions.add(p);
			}
		}
	}

	/**
	 *  检查用户是否授权
	 * @param permission
	 * @return
	 */
	public static boolean checkPermission(String permission){
		if(need) {
			return findPermission(permission).hasPermission;
		}
		return true;
	}
	public static void requestPermission(Activity activity,Permission permission){
		if(need) {
			RequestPermissionUtil.requestPermission(activity, permission.permission, permission.requestCode);
		}
	}
}
