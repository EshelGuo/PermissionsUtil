#动态申请权限工具类
android 6.0 以上需要动态申请权限, 而动态申请权限一般比较繁琐, 所以该工具类应运而生.
## 重大更新
###请注意: permission 文件夹中的 三个文件 Permission Permissions RequestPermissionUtil 三个类已经弃用 , 请使用根目录下的文件
### 1. 对之前的 API 的 影响 (重要):
	使用 initPermissions() 方式初始化的权限 每次 app 被开启 只能 请求一次, 因为请求完成
	后会将集合清空 
	现在请求 不建议使用 requestPermissionAll() 方法进行请求 , 请使用提供的新的 API 进行请求
### 2. 添加了新的方法 , 支持 单个或多个 权限 同时请求
	requestPermission(Activity activity,Permission ... permissions)//使用提供的 Permission 枚举类一次请求多个权限
	requestPermission(Activity activity,String ... permissions)//使用权限字符串一次请求多个权限 权限字符串可以参考 Manifest 类
	requestPermission(Activity activity,Permission permission,RequestCallback callback)// 使用有回调的请求 请求一个权限
	requestPermission(Activity activity,String permission,RequestCallback callback)// 使用字符串(有回调) 请求一个权限
### 3. 重构了部分代码结构 , 使用了 两个枚举类 将权限分组
### 4. (重要) 工具类中 有一部分需要维护 :
	 Group 和 Permission 两个枚举类 Manifest 类中的所有权限进行了分类 , 由于未来可能添
	加新的权限 , 所以需要 使用者 维护 Group 和 Permission 两个类 , 当有新的权限添加时 
	及时更新 Group 和 Permission 中的权限 , 当然到时候我也会更新 git 仓库
	在 Permissions 类 addPermission() 方法中有一个 TODO , 当 改行执行时 有两种情况 , 
	第一种是 权限字符串 传入错误 , 还有就是 可能Google更新了权限 , 但是没有及时在 枚举类
	中更新 
### 5. 请求接口回调使用示例(可以在同时请求多个权限时使用接口回调 , 也可以使用同一个接口回调 当然也可以不用):
	Permissions.requestPermission(this, Permission.WRITE_EXTERNAL_STORAGE.setCallback(new RequestCallback() {
			@Override
			public void requestSuccess(Permission permission) {
				Toast.makeText(MainActivity.this,"请求成功",0).show();
			}

			@Override
			public boolean requestFailed(Permission permission) {
				Toast.makeText(MainActivity.this,"请求失败",0).show();
				return false;
			}

			@Override
			public void havePermissioned(Permission permission) {
				Toast.makeText(MainActivity.this,"之前请求过",0).show();
			}

			@Override
			public boolean userSelectNeverAgain(Permission permission, NeverAgainUtil neverAgainUtil) {
				neverAgainUtil.gotoPermissionSettingUI(MainActivity.this,0);
				return false;
			}
		}),Permission.CAMERA);
### 6. 用户勾选绝不提示 后的回调 :
	public void gotoPermissionSettingUI(Activity activity,int requestCode)
	//将跳转到该应用的应用管理界面 可以在 onActivityResult 回调中得到 结果 (用户是否开启权限) 
	使用: NeverAgainUtil.newInstance().gotoPermissionSettingUI(activity,requestCode)
## 使用方法:
>
0. 将工具类复制到项目中
1. 在需要请求权限的 Activity 中重新下方法, 作用是判断权限是否请求成功 :
>
		@Override
		public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
			RequestPermissionUtil.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
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