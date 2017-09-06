package com.example.permissiondemo.permission;


import android.Manifest;
import android.util.Log;

/**
 * 创建人: Eshel
 * 创建时间:2017/7/12 19时11分
 * 描述: TODO
 */

public enum  Permission {
	WRITE_CONTACTS(0, "android.permission.WRITE_CONTACTS",true),
	GET_ACCOUNTS(1, "android.permission.GET_ACCOUNTS",true),
	READ_CONTACTS(2, "android.permission.READ_CONTACTS",true),
	READ_CALL_LOG(3, "android.permission.READ_CALL_LOG",true),
	READ_PHONE_STATE(4, "android.permission.READ_PHONE_STATE",true),
	CALL_PHONE(5, "android.permission.CALL_PHONE",true),
	WRITE_CALL_LOG(6, "android.permission.WRITE_CALL_LOG",true),
	USE_SIP(7, "android.permission.USE_SIP",true),
	PROCESS_OUTGOING_CALLS(8, "android.permission.PROCESS_OUTGOING_CALLS",true),
	ADD_VOICEMAIL(9, "com.android.voicemail.permission.ADD_VOICEMAIL",true),
	READ_CALENDAR(10, "android.permission.READ_CALENDAR",true),
	WRITE_CALENDAR(11, "android.permission.WRITE_CALENDAR",true),
	CAMERA(12, "android.permission.CAMERA",true),
	BODY_SENSORS(13, "android.permission.BODY_SENSORS",true),
	ACCESS_FINE_LOCATION(14, "android.permission.ACCESS_FINE_LOCATION",true),
	READ_EXTERNAL_STORAGE(15, "android.permission.READ_EXTERNAL_STORAGE",true),
	WRITE_EXTERNAL_STORAGE(16, "android.permission.WRITE_EXTERNAL_STORAGE",true),
	RECORD_AUDIO(17, "android.permission.RECORD_AUDIO",true),
	READ_SMS(18, "android.permission.READ_SMS",true),
	RECEIVE_WAP_PUSH(19, "android.permission.RECEIVE_WAP_PUSH",true),
	RECEIVE_MMS(20, "android.permission.RECEIVE_MMS",true),
	RECEIVE_SMS(21, "android.permission.RECEIVE_SMS",true),
	SEND_SMS(22, "android.permission.SEND_SMS",true),
	ACCESS_COARSE_LOCATION(23,"android.permission.ACCESS_COARSE_LOCATION",true),
//	READ_CELL_BROADCASTS(24, "android.permission.READ_CELL_BROADCASTS",true),

	ACCESS_LOCATION_EXTRA_COMMANDS(-1,"android.permission.ACCESS_LOCATION_EXTRA_COMMANDS",false),
	ACCESS_NETWORK_STATE(-2,"android.permission.ACCESS_NETWORK_STATE",false),
	ACCESS_WIFI_STATE(-3,"android.permission.ACCESS_WIFI_STATE",false),
	BLUETOOTH(-4,"android.permission.BLUETOOTH",false),
	BLUETOOTH_ADMIN(-5,"android.permission.BLUETOOTH_ADMIN",false),
	BROADCAST_STICKY(-6,"android.permission.BROADCAST_STICKY",false),
	CHANGE_NETWORK_STATE(-7,"android.permission.CHANGE_NETWORK_STATE",false),
	CHANGE_WIFI_MULTICAST_STATE(-8,"android.permission.CHANGE_WIFI_MULTICAST_STATE",false),
	CHANGE_WIFI_STATE(-9,"android.permission.CHANGE_WIFI_STATE",false),
	DISABLE_KEYGUARD(-10,"android.permission.DISABLE_KEYGUARD",false),
	EXPAND_STATUS_BAR(-11,"android.permission.EXPAND_STATUS_BAR",false),
	GET_PACKAGE_SIZE(-12,"android.permission.GET_PACKAGE_SIZE",false),
	INSTALL_SHORTCUT(-13,"com.android.launcher.permission.INSTALL_SHORTCUT",false),
	INTERNET(-14,"android.permission.INTERNET",false),
	KILL_BACKGROUND_PROCESSES(-15,"android.permission.KILL_BACKGROUND_PROCESSES",false),
	MODIFY_AUDIO_SETTINGS(-16,"android.permission.MODIFY_AUDIO_SETTINGS",false),
	NFC(-17,"android.permission.NFC",false),
	READ_SYNC_SETTINGS(-18,"android.permission.READ_SYNC_SETTINGS",false),
	READ_SYNC_STATS(-19,"android.permission.READ_SYNC_STATS",false),
	RECEIVE_BOOT_COMPLETED(-20,"android.permission.RECEIVE_BOOT_COMPLETED",false),
	REORDER_TASKS(-21,"android.permission.REORDER_TASKS",false),
	REQUEST_INSTALL_PACKAGES(-22,"android.permission.REQUEST_INSTALL_PACKAGES",false),
	SET_ALARM(-23,"com.android.alarm.permission.SET_ALARM",false),
	SET_TIME_ZONE(-24,"android.permission.SET_TIME_ZONE",false),
	SET_WALLPAPER(-25,"android.permission.SET_WALLPAPER",false),
	SET_WALLPAPER_HINTS(-26,"android.permission.SET_WALLPAPER_HINTS",false),
	TRANSMIT_IR(-27,"android.permission.TRANSMIT_IR",false),
	UNINSTALL_SHORTCUT(-28,"com.android.launcher.permission.UNINSTALL_SHORTCUT",false),
	USE_FINGERPRINT(-29,"android.permission.USE_FINGERPRINT",false),
	VIBRATE(-30,"android.permission.VIBRATE",false),
	WAKE_LOCK(-31,"android.permission.WAKE_LOCK",false),
	WRITE_SYNC_SETTINGS(-32,"android.permission.WRITE_SYNC_SETTINGS",false),
	ACCESS_NOTIFICATION_POLICY(-33,"android.permission.ACCESS_NOTIFICATION_POLICY",false);
	/**
	 * 请求码
	 */
	public final int requestCode;
	/**
	 *  Manifest.permission.***
	 */
	public String permission;
	/**
	 * 是否有权限
	 */
	public boolean hasPermission;
	/**
	 * 是否是危险权限(需要请求)
	 */
	public boolean isDangerPermission = true;

	public Permission setCallback(RequestCallback callback) {
		this.callback = callback;
		return this;
	}

	protected RequestCallback callback;

	Permission(int requestCode, String permission, boolean isDangerPermission) {
		Log.i("permission","枚举类被创建  "+permission);
		this.requestCode = requestCode;
		this.permission = permission;
		this.isDangerPermission = isDangerPermission;
		this.hasPermission = !isDangerPermission;
	}
	public static Permission findPermissionByCode(int requestCode){
		for (Permission permission : values()) {
			if(permission.requestCode == requestCode)
				return permission;
		}
		return null;
	}
	public static Permission findPermissionByName(String permissionName){
		for (Permission permission : values()) {
			if(permission.permission.equals(permissionName))
				return permission;
		}
		return null;
	}

}
