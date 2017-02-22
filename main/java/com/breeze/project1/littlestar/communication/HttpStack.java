package com.breeze.project1.littlestar.communication;

import android.graphics.Bitmap;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by Lesley on 2017/1/11.
 */
public class HttpStack
{
	/**
	 * TAG
	 */
	private static final String TAG = "HttpClientStack";

	/**
	 * HTTP 请求实例
	 */
	private HttpClient httpClient;



	/**
	 * 目的服务器
	 */
	private HttpHost httpHost;


	private int maxStrLen = 100000;

	/**
	 * 关闭连接
	 */
	public void closeConnection()
	{
		if (null != httpClient)
		{
			synchronized (HttpClient.class)
			{
				if (null != httpClient)
				{
					httpClient.getConnectionManager().shutdown();
					httpHost = null;
					httpClient = null;
				}
			}
		}
	}

	/**
	 * 创建一个HTTPS连接
	 *
	 * @param ip 服务器IP
	 * @param port 服务器端口
	 */
	public void createConnection(final String ip, final int port)
	{

		final HttpParams param = new BasicHttpParams();
		HttpProtocolParams.setVersion(param, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(param, HTTP.UTF_8);
		param.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		param.setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		final SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), port));
		final ClientConnectionManager ccm = new ThreadSafeClientConnManager(param, registry);
		if (null == httpClient)
		{
			synchronized (HttpClient.class)
			{
				if (null == httpClient)
				{
					httpClient = new DefaultHttpClient(ccm, param);
				}
			}
		}
		httpHost = new HttpHost(ip, port, "http");
	}

	/**
	 * 远程调用服务POST方法
	 *
	 * @param url rest地址
	 * @param entity 传递参数
	 * @param connectTimeOut 超时时间
	 * @return 服务器返回点JSON字符串
	 * @throws IOException 异常
	 */
	public HttpResponse remotePost(final String url, final String entity, final int connectTimeOut)
			throws IOException
	{
		final StringEntity input = new StringEntity(entity);
		input.setContentType("application/json; charset=UTF-8");
		final HttpPost httppost = new HttpPost(url);
		httppost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, connectTimeOut);
		httppost.setEntity(input);
		if (null == httpClient)
		{
			return null;
		}
		final HttpResponse response = httpClient.execute(httpHost, httppost);
		httppost.abort();
		return response;
	}

	/**
	 * HTTP DELETE方法进行删除操作
	 *
	 * @param url rest地址
	 * @param connectTimeOut 超时时间
	 * @return 服务器返回点JSON字符串
	 * @throws IOException 异常
	 */
	public HttpResponse remoteDelete(final String url, final int connectTimeOut)
			throws IOException
	{
		final HttpDelete httpdelete = new HttpDelete();
		httpdelete.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, connectTimeOut);
		httpdelete.setHeader("Content-Type", "application/json; charset=UTF-8");
		httpdelete.setURI(URI.create(url));
		if (null == httpClient)
		{
			return null;
		}
		final HttpResponse response = httpClient.execute(httpHost, httpdelete);
		httpdelete.abort();
		return response;
	}

	/**
	 * HTTP PUT方法进行修改操作
	 *
	 * @param url rest地址
	 * @param entity 传递参数
	 * @param connectTimeOut 超时时间
	 * @return 服务器返回点JSON字符串
	 * @throws IOException 异常
	 */
	public HttpResponse remotePut(final String url, final String entity, final int connectTimeOut)
			throws IOException
	{
		final StringEntity input = new StringEntity(entity);
		input.setContentType("application/json; charset=UTF-8");
		final HttpPut httpput = new HttpPut(url);
		httpput.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, connectTimeOut);
		if (input != null)
		{
			httpput.setEntity(input);
		}
		final HttpResponse response = httpClient.execute(httpHost, httpput);
		httpput.abort();

		return response;
	}

	/**
	 * 远程调用服务GET方法
	 *
	 * @param url rest地址
	 * @param entity 传递参数
	 * @param connectTimeOut 超时时间
	 * @return 服务器返回点JSON字符串
	 * @throws IOException 异常
	 */
	public Bitmap remoteGetBitmap(final String url, final String entity, final int connectTimeOut)
			throws IOException
	{
		final HttpGet httpget = new HttpGet();
		httpget.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, connectTimeOut);
		httpget.setHeader("Content-Type", "application/json; charset=UTF-8");
		httpget.setHeader("Connection", "Keep-Alive");
		if (entity != null)
		{
			httpget.setURI(URI.create(url + entity));
		}
		else
		{
			httpget.setURI(URI.create(url));
		}
		if (null == httpClient)
		{
			return null;
		}
		// 测试log
        /*
         * LogUtil.debug(TAG, URLDecoder.decode(MAPPER.writeValueAsString(httpHost.toHostString() + httpget.getURI()),
         * HTTP.UTF_8));
         */
		final HttpResponse response = httpClient.execute(httpHost, httpget);
		Bitmap image = HttpService.getInstance().getRemotePhoto(response);
		httpget.abort();
		return image;

	}



	public String remoteGetStr(final String url, final String entity, final int connectTimeOut)
			throws IOException
	{
		final HttpGet httpget = new HttpGet();
		httpget.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, connectTimeOut);
		httpget.setHeader("Content-Type", "application/json; charset=UTF-8");
		httpget.setHeader("Connection", "Keep-Alive");
		if (entity != null)
		{
			httpget.setURI(URI.create(url + entity));
		}
		else
		{
			httpget.setURI(URI.create(url));
		}
		if (null == httpClient)
		{
			return null;
		}
		// 测试log
        /*
         * LogUtil.debug(TAG, URLDecoder.decode(MAPPER.writeValueAsString(httpHost.toHostString() + httpget.getURI()),
         * HTTP.UTF_8));
         */
		final HttpResponse response = httpClient.execute(httpHost, httpget);
		String result = getHttpEntityContent(response);
		httpget.abort();
		return result;

	}



	/**
	 * 获得响应HTTP实体内容
	 *
	 * @param response 原始返回对象
	 * @return json字符串
	 * @throws IOException 异常
	 */
	private String getHttpEntityContent(final HttpResponse response)
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
					Log.e(TAG,e.toString());
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
							Log.e("getHttpEntityContent", e.toString());
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
							Log.e("getHttpEntityContent",e.toString());
						}
					}

				}

			}
		}
		return "";
	}
}



