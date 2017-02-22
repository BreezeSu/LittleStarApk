package com.breeze.project1.littlestar.common;

/**
 * Created by Lesley on 2017/1/23.
 */
public class CommonUtils
{
	private static CommonUtils INSTANCE;

	private CommonUtils()
	{

	}

	public static CommonUtils getInstance()
	{
		if(INSTANCE == null )
		{
			INSTANCE = new CommonUtils();
		}
		return INSTANCE;
	}


	public boolean isEmptyStr(String str)
	{
		return str == null ||"".equals(str);
	}

	public boolean isNotEmptyStr(String str)
	{
		return !isEmptyStr(str);
	}
}
