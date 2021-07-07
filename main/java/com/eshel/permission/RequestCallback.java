package com.eshel.permission;

/**
 * Created by guoshiwen on 2017/9/5.
 */

public interface RequestCallback {
	void requestSuccess(Permission permission);

	/**
	 * return true 代表终止请求
	 * @param permission
	 * @return
	 */
	boolean requestFailed(Permission permission);
	void havePermissioned(Permission permission);
	/**
	 * return true 代表终止请求
	 * @param permission
	 * @return
	 */
	boolean userSelectNeverAgain(Permission permission, NeverAgainUtil neverAgainUtil);
}
