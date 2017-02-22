package com.breeze.project1.littlestar.communication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Lesley on 2017/1/18.
 */
public final class HttpService
{

    private final static String TAG = "HttpService";
	private int maxStrLen = 100000;


	private static HttpService INSTANCE = null;

	private HttpService()
	{

	}

	public static HttpService getInstance()
	{
		if(null == INSTANCE)
		{
			INSTANCE = new HttpService();
		}
		return INSTANCE;
	}



	public  Bitmap getRemotePhoto(final HttpResponse response)
	{

		Bitmap photo = getBitmapContent(response);
		return photo;

	}


	private  Bitmap getBitmapContent(final HttpResponse response)
	{
		Bitmap pic = null;
		if (null != response)
		{
			final HttpEntity httpentity = response.getEntity();
			if (httpentity != null)
			{
				InputStream is = null;
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				try
				{
					is = httpentity.getContent();
					if (is == null){
						throw new RuntimeException("stream is null");
					}
					else
					{
						try {

							byte[] buffer = new byte[1024];
							int len = 0;
							while( (len=is.read(buffer)) != -1){
								outStream.write(buffer, 0, len);
							}

							byte[] data= outStream.toByteArray();


							if(data!=null)
							{
								pic = BitmapFactory.decodeByteArray(data, 0, data.length);
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							is.close();
							outStream.close();
						}

					}
				}
				catch (final IOException e)
				{
					Log.e(TAG, "IOException occur.");
				}


			}
		}
		return pic;
	}








	/**
	 * 获得响应HTTP实体内容
	 *
	 * @param response 原始返回对象
	 * @return json字符串
	 * @throws IOException 异常
	 */
	private String getStringContent(final HttpResponse response)
			throws IOException
	{
		if (null != response)
		{
			final HttpEntity httpentity = response.getEntity();
			if (httpentity != null)
			{
				InputStream is = null;
				BufferedReader reader = null;
				try
				{
					is = httpentity.getContent();
					reader = new BufferedReader(new InputStreamReader(is, HTTP.UTF_8));
					final StringBuilder sbuilder = new StringBuilder();
					int intC;
					int index = 0;
					while ((intC = reader.read()) != -1)
					{
						final char c = (char)intC;
						index++;
						if (c == '\n')
						{
							index = 0;
						}
						if (index >= maxStrLen)
						{
							Log.e(TAG, "input too long.");
							return "";
						}
						sbuilder.append(c);
					}
					return sbuilder.toString();
				}
				catch (final IOException e)
				{
					Log.e(TAG, "IOException occur.");
				}
				finally
				{
					if (null != reader)
					{
						try
						{
							reader.close();
						}
						catch (final IOException e)
						{
							Log.e("getHttpEntityContent", "IOException occur when BufferedReader close.");
						}
					}
					if (null != is)
					{
						try
						{
							is.close();
						}
						catch (final IOException e)
						{
							Log.e("getHttpEntityContent", "IOException occur when InputStream close.");
						}
					}

				}

			}
		}
		return "";
	}
}
