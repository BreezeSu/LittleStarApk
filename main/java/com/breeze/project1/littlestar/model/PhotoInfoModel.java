package com.breeze.project1.littlestar.model;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Lesley on 2017/1/9.
 * 照片信息
 */
public class PhotoInfoModel
{
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 拍摄时间
	 */
	private String date;

	/**
	 * ID
	 */
	private long id;

	/**
	 * URL
	 */
	private String url;

	/**
	 * 大小
	 *
	 */
	private long size;

	/**
	 * 分辨率
	 */
	private String resolution;


	/**
	 * 图片
	 */
	private Bitmap pic = null;



	public PhotoInfoModel(String jsonStr)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(jsonStr);
            setName(jsonObject.getString("name"));
			setDate(jsonObject.getString("date"));

		}
		catch (JSONException e)
		{
			Log.e("PhotoInfoModel","PhotoInfoModel build failed.");
		}
	}


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public long getSize()
	{
		return size;
	}

	public void setSize(long size)
	{
		this.size = size;
	}

	public String getResolution()
	{
		return resolution;
	}

	public void setResolution(String resolution)
	{
		this.resolution = resolution;
	}

	public Bitmap getPic()
	{
		return pic;
	}

	public void setPic(Bitmap pic)
	{
		this.pic = pic;
	}



}
