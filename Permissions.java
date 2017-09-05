package com.example.permissiondemo.permission;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 动态权限管理类
 * 该类会自动判断系统版本,如果高于23,则会动态申请权限
 * 在MainActivity onCreate中:
 * Permissions.requestPermissionAll(this);
 * 调用该方法将触发请求权限
 * 在 需要申请权限的 Activity 中 , 重写 onRequestPermissionsResult() 方法, 在方法中调用
 * RequestPermissionUtil.onRequestPermissionsResult(); 将需要的参数传入,如:
 *
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		RequestPermissionUtil.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
 */

public class Permissions {
	protected static boolean isStop;
	private static List<Permission> permissions = new ArrayList<>();
	public static boolean need;//需要动态申请权限
	private static boolean requesting = false;
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
			// TODO: 在此声明所需 动态添加 权限
			/*addPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
			addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			addPermissions(
					Manifest.permission.ACCESS_COARSE_LOCATION,
					Manifest.permission.ACCESS_FINE_LOCATION,
					Manifest.permission.READ_PHONE_STATE
			);*/

		}
	}
private static int index = 0;
	/**
	 * 请求所有权限
	 * @param activity
	 */
	public synchronized static void requestPermissionAll(Activity activity){
		if(need) {
			requesting = true;
			if(isStop){
				index = permissions.size();
				isStop = false;
			}
			if(index>=permissions.size()) {
				index = 0;
				permissions.clear();
				requesting = false;
				return;
			}
			Permission permission = permissions.get(index);
			boolean succeed;
			if(!permissionUpdate) {
				if(permission.hasPermission){
					Log.i("permission","已经授权");
					succeed = true;
				}else {
					succeed = RequestPermissionUtil.requestPermission(activity, permission.permission, permission.requestCode);
					permission.hasPermission = succeed;
				}
			}else {
				succeed = RequestPermissionUtil.requestPermission(activity, permission.permission, permission.requestCode);
				permission.hasPermission = succeed;
			}
			index++;
			if(succeed){
				RequestCallback callback = permission.callback;
				if(callback != null){
					callback.havePermissioned(permission);
				}
				requestPermissionAll(activity);
			}
		}
	}

	/**
	 * 改变权限的状态
	 * @param permission 权限名
	 * @param success 是否请求成功
	 */
	public static synchronized void changePermissionState(Activity activity, int requestCode,String permission, boolean success){
		if(need) {
			Permission permission1 = findPermission(permission);
			if(success) {
				if (permission1 != null) {
					Group group = Group.findGroupByPermission(permission1);
					if(group != null)
						group.changePermissionState(true,requestCode);
				}
			}else{
				RequestCallback callback = permission1.callback;
				if(callback != null){
					if(callback.requestFailed(permission1)){
						isStop = true;
					}
				}
			}
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
			Permission p = Permission.findPermissionByName(permission);
			if(p != null) {
				if(p.isDangerPermission) {
					permissions.add(p);
				}
				return p;
			}
		}
		// TODO: 2017/9/5  权限没有被找到, 打印错误日志上传服务器
		return null;
	}
	public static void addPermissions(String ... permissions){
		if(need) {
			for (String permission : permissions) {
				addPermission(permission);
			}
		}
	}

	/**
	 * 检查用户是否授权
	 * @param permission
	 * @return
	 */
	public static synchronized boolean checkPermission(String permission){
		if(need) {
			Permission permission1 = Permission.findPermissionByName(permission);
			if(permission1 != null)
				return permission1.hasPermission;
			else
				return false;
		}
		return true;
	}

	/**
	 * use this
	 * @param activity
	 * @param permissions
	 */
	public static synchronized void requestPermission(Activity activity,Permission ... permissions){
		if(!requesting) {
			if(Permissions.permissions != null) {
//				Permissions.permissions.clear();
				for (Permission permission : permissions) {
					Permissions.permissions.add(permission);
				}
				requestPermissionAll(activity);
			}
		}
	}

	/**
	 * use this
	 * @param activity
	 * @param permissions
	 */
	public static synchronized void requestPermission(Activity activity,String ... permissions){
		if(!requesting) {
			if(Permissions.permissions != null) {
//				Permissions.permissions.clear();
				addPermissions(permissions);
				requestPermissionAll(activity);
			}
		}
	}

	/**
	 * use this
	 * @param activity
	 * @param permission
	 * @param callback
	 */
	public static synchronized void requestPermission(Activity activity,Permission permission,RequestCallback callback){
		requestPermission(activity,permission.setCallback(callback));
	}
	public static synchronized void requestPermission(Activity activity,String permission,RequestCallback callback){
		Permission permission1 = Permission.findPermissionByName(permission);
		if(permission1!=null)
			requestPermission(activity,permission1.setCallback(callback));
	}
	static boolean permissionUpdate;

	/**
	 * useless
	 */
	@Deprecated
	public static void updatePermissionState(){
		permissionUpdate = true;
	}
}
