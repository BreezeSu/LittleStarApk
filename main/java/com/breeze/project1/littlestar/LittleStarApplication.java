package com.breeze.project1.littlestar;

import android.app.Application;

/**
 * Created by Lesley on 2017/1/22.
 */
public class LittleStarApplication extends Application
{


	private static LittleStarApplication INSTANCE;

	/**
	 * 服务器IP
	 */
	private  String serverIp;


	/**
	 * 获取Application单例
	 * @return 单例
	 */
	public static LittleStarApplication getInstance() {
		return INSTANCE;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		INSTANCE = this;
	}


	 public  String getServerIp()
	 {
		 return serverIp;
	 }

	 public  void setServerIp(String serverIp)
	 {
		 serverIp = serverIp;
	 }
}
