package com.breeze.project1.littlestar.communication;

import android.graphics.Bitmap;

import com.breeze.project1.littlestar.common.CommonConstant;
import com.breeze.project1.littlestar.model.PhotoInfoRestModel;
import com.breeze.project1.littlestar.model.PhotoInfoSO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lesley on 2017/2/16.
 */
public class QueryService
{

	public List<PhotoInfoRestModel> getRemotePhotoInfo(PhotoInfoSO so)
	{
		List<PhotoInfoRestModel> result = new ArrayList<PhotoInfoRestModel>();


		String searchCondition = so.generateSeachInfoCondition();
		HttpStack httpMgr = new HttpStack();
		httpMgr.createConnection(so.getServerIp(),so.getServerPort());
		String photoInfoStr = null;
		String queryRemoteUrl = CommonConstant.HTTP_URL_HEAD+so.getServerIp()+CommonConstant.LITTLESTAR_URL+searchCondition;
		try
		{
			photoInfoStr =httpMgr.remoteGetStr(queryRemoteUrl,null,1000);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		Gson json = new Gson();
		List < PhotoInfoRestModel > photoRestList = json.fromJson(photoInfoStr,new TypeToken<List<PhotoInfoRestModel>>(){}.getType());


		return photoRestList;
	}




	public Bitmap getRemotePhotoPic(PhotoInfoSO so)
	{
		HttpStack httpMgr = new HttpStack();
		httpMgr.createConnection(so.getServerIp(),so.getServerPort());
		Bitmap photo = null;
		String searchCondition = so.generateSearchPicCondition();
		String queryRemoteUrl = CommonConstant.HTTP_URL_HEAD+so.getServerIp()+CommonConstant.LITTLESTAR_URL+searchCondition;
		try
		{
			photo =httpMgr.remoteGetBitmap(queryRemoteUrl,null,1000);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return photo;
	}



}
