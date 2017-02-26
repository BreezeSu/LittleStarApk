package com.breeze.project1.littlestar.model;

import com.breeze.project1.littlestar.common.CommonConstant;
import com.breeze.project1.littlestar.common.CommonUtils;

import java.io.Serializable;

/**
 * Created by Lesley on 2017/2/1.
 */
public class PhotoInfoSO implements Serializable
{



	private String serverIp;

	private int serverPort = 8080;

	private String name;

	private String pageLength="20";

	private String pageBegin="0";

	private String orderBy="name";

	private boolean orderRule;




	public String generateSeachInfoCondition()
	{
        StringBuilder strBuilder = new StringBuilder(CommonConstant.SEARCH_BEGIN+"queryType=info"+CommonConstant.SEARCH_COMBINE+"pageLength="+getPageLength());
		strBuilder.append(CommonConstant.SEARCH_COMBINE).append("pageBegin="+getPageBegin()).append(CommonConstant.SEARCH_COMBINE).append("orderBy="+getOrderBy());

		if(CommonUtils.getInstance().isNotEmptyStr(getName()))
		{
			strBuilder.append(CommonConstant.SEARCH_COMBINE).append("name="+getName());
		}
		return strBuilder.toString();
	}


	public String generateSearchPicCondition()
	{
		StringBuilder strBuilder = new StringBuilder(CommonConstant.SEARCH_BEGIN+"queryType=pic"+CommonConstant.SEARCH_COMBINE);
		strBuilder.append("name="+getName());
		return strBuilder.toString();
	}

	public String generateSearchSnapCondition()
	{
		StringBuilder strBuilder = new StringBuilder(CommonConstant.SEARCH_BEGIN+"queryType=snap"+CommonConstant.SEARCH_COMBINE);
		strBuilder.append("name="+getName());
		return strBuilder.toString();
	}




	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPageLength()
	{
		return pageLength;
	}

	public void setPageLength(String pageLength)
	{
		this.pageLength = pageLength;
	}

	public String getPageBegin()
	{
		return pageBegin;
	}

	public void setPageBegin(String pageBegin)
	{
		this.pageBegin = pageBegin;
	}

	public String getOrderBy()
	{
		return orderBy;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	public boolean isOrderRule()
	{
		return orderRule;
	}

	public void setOrderRule(boolean orderRule)
	{
		this.orderRule = orderRule;
	}

	public String getServerIp()
	{
		return serverIp;
	}

	public void setServerIp(String serverIp)
	{
		this.serverIp = serverIp;
	}

	public int getServerPort()
	{
		return serverPort;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

}
