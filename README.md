#动态申请权限工具类
android 6.0 以上需要动态申请权限, 而动态申请权限一般比较繁琐, 所以该工具类应运而生.
##使用方法:
>
0. 将三个工具类复制到项目中
1. 需要在 Activity 中重新下方法, 作用是判断权限是否请求成功 :
>
		@Override
		public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Permissions.changePermissionState(this,permissions[0],true);
			}
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
3. 在Activity onCreate中(或别的地方) 调用 requestPermissionAll 方法将触发申请权限 :
>
		Permissions.requestPermissionAll(this);
4. 在 工具类 Permissions 中 , 在 initPermissions() 方法中加入所需动态申请的所有权限:
>
		/**
		 * 初始化权限
		 */
		private static void initPermissions() {
			if(need) {
				// TODO: 2017/7/28  在此声明所需 动态添加 权限
				addPermission(Manifest.permission.READ_EXTERNAL_STORAGE);// 添加单个权限
				addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
				addPermissions(//添加多个权限
						Manifest.permission.ACCESS_COARSE_LOCATION,
						Manifest.permission.ACCESS_FINE_LOCATION,
						Manifest.permission.READ_PHONE_STATE
				);
>
			}
		}