package com.example.permissiondemo.permission;


import android.Manifest;
import android.util.Log;

/**
 * Created by guoshiwen on 2017/9/5.
 */

public enum Group {
	CONTACTS("android.permission-group.CONTACTS",0,
			Permission.WRITE_CONTACTS,
			Permission.GET_ACCOUNTS,
			Permission.READ_CONTACTS),
	PHONE("android.permission-group.CONTACTS",1,
			Permission.READ_CALL_LOG,
			Permission.READ_PHONE_STATE,
			Permission.CALL_PHONE,
			Permission.WRITE_CALL_LOG,
			Permission.USE_SIP,
			Permission.PROCESS_OUTGOING_CALLS,
			Permission.ADD_VOICEMAIL),
	CALENDAR("android.permission-group.CALENDAR",2,
			Permission.READ_CALENDAR,
			Permission.WRITE_CALENDAR),
	CAMERA("android.permission-group.CAMERA",3,
			Permission.CAMERA),
	SENSORS("android.permission-group.SENSORS",4,
			Permission.BODY_SENSORS),
	LOCATION("android.permission-group.LOCATION",5,
			Permission.ACCESS_FINE_LOCATION,
			Permission.ACCESS_COARSE_LOCATION
	),
	STORAGE("android.permission-group.STORAGE",6,
			Permission.READ_EXTERNAL_STORAGE,
			Permission.WRITE_EXTERNAL_STORAGE),
	MICROPHONE("android.permission-group.MICROPHONE",7,
			Permission.RECORD_AUDIO),
	SMS("android.permission-group.SMS",8,
			Permission.READ_SMS,
			Permission.RECEIVE_WAP_PUSH,
			Permission.RECEIVE_MMS,
			Permission.RECEIVE_SMS,
			Permission.SEND_SMS
//			Permission.READ_CELL_BROADCASTS
	);
	private String groupName;
	private int groupCode;
	private Permission[] mPermissions;
	Group(String groupName,int groupCode, Permission ... permission){
		this.groupName = groupName;
		this.groupCode = groupCode;
		this.mPermissions = permission;
	}
	public static Group findGroupByName(String groupName){
		for (Group group : values()) {
			if(group.groupName.equals(groupName))
				return group;
		}
		return null;
	}
	public static Group findGroupByCode(int groupCode){
		for (Group group : values()) {
			if(group.groupCode == groupCode)
				return group;
		}
		return null;
	}
	public static Group findGroupByPermission(Permission permission){
		for (Group group : values()) {
			for (Permission permission1 : group.mPermissions) {
				if(permission.requestCode == permission1.requestCode){
					return group;
				}
			}
		}
		return null;
	}
	public void changePermissionState(boolean hasPermission,int permissionCode){
		changePermissionState(hasPermission,permissionCode,false);
	}
	public void changePermissionState(boolean hasPermission,int permissionCode,boolean callbacked){
		for (Permission permission : mPermissions) {
			permission.hasPermission = hasPermission;
			if(permission.requestCode == permissionCode){
				Log.i("permission","hasPermission: "+(permission.hasPermission?"true":"false"));
				if(!callbacked) {
					RequestCallback callback = permission.callback;
					if (callback != null) {
						if (hasPermission)
							callback.requestSuccess(permission);
						else {
							if (callback.requestFailed(permission)) {
								Permissions.isStop = true;
							}
						}
					}
				}
			}
		}
	}
}
