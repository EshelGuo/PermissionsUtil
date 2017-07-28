package com.eshel.takeout.permission;

/**
 * 项目名称: GooglePlay
 * 创建人: Eshel
 * 创建时间:2017/7/12 19时11分
 * 描述: TODO
 */

public class Permission {
	/**
	 * 请求码
	 */
	public int requestCode;
	/**
	 *  Manifest.permission.***
	 */
	public String permission;
	/**
	 * 是否有权限
	 */
	public boolean hasPermission;

	public Permission(int requestCode, String permission, boolean hasPermission) {
		this.requestCode = requestCode;
		this.permission = permission;
		this.hasPermission = hasPermission;
	}
}
