package com.breeze.project1.littlestar.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.breeze.project1.littlestar.R;
import com.breeze.project1.littlestar.communication.HttpService;
import com.breeze.project1.littlestar.communication.HttpStack;
import com.breeze.project1.littlestar.model.PhotoInfoModel;
import com.breeze.project1.littlestar.model.PhotoInfoRestModel;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Lesley on 2017/1/10.
 */
public class PhotoListAdpt extends ArrayAdapter<PhotoInfoRestModel>
{
	private final LayoutInflater inflater;

	private List<PhotoInfoRestModel> photoList = new ArrayList<PhotoInfoRestModel>();



	public PhotoListAdpt(Context context, List<PhotoInfoRestModel> objects)
	{
		super(context, 0, objects);
		inflater = LayoutInflater.from(context);
	}



	@Override
	public int getCount()
	{
		if(photoList == null)
		{
			return 0;
		}
		else
		{
			return photoList.size();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		if(convertView == null )
		{
			convertView = inflater.inflate(R.layout.photolist_item,null);
		}

		PhotoInfoRestModel photoItem = getItem(position);
		TextView name = (TextView) convertView.findViewById(R.id.photo_name);
		name.setText(photoItem.getName());
		TextView date = (TextView) convertView.findViewById(R.id.photo_date);
		date.setText(photoItem.getDate());

		ImageView imageView = (ImageView) convertView.findViewById(R.id.photo_image);
		imageView.setImageBitmap(photoItem.getImageSnapView());
		return convertView;
	}

	@Override
	public PhotoInfoRestModel getItem(int position)
	{
		if(photoList.isEmpty())
	    {
			return null;
	    }
		else
			return photoList.get(position);
	}

	public List<PhotoInfoRestModel> getPhotoList()
	{
		return photoList;
	}

	public void setPhotoList(List<PhotoInfoRestModel> photoList)
	{
		this.photoList = photoList;
	}
}
