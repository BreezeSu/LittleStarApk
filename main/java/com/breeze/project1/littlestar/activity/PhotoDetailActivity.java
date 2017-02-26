package com.breeze.project1.littlestar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.breeze.project1.littlestar.BaseActivity;
import com.breeze.project1.littlestar.R;
import com.breeze.project1.littlestar.communication.HttpService;
import com.breeze.project1.littlestar.communication.QueryService;
import com.breeze.project1.littlestar.model.PhotoInfoRestModel;
import com.breeze.project1.littlestar.model.PhotoInfoSO;

import java.io.Serializable;
import java.util.List;


public class PhotoDetailActivity extends BaseActivity
{

	private ImageView picView;
	@Override
	protected void doOnCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.photodetail_layout);
		Intent intentFromList = getIntent();
		PhotoInfoSO so =  (PhotoInfoSO) intentFromList.getSerializableExtra("PhotoInfoSO");
		picView = (ImageView) findViewById(R.id.photo_detail_imageview);
		new Thread(new InnerRunnable(so,handler)).start();
	}




	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			Bitmap pic =data.getParcelable("photoPic");

			picView.setImageBitmap(pic);
		}
	};


	private class InnerRunnable implements Runnable
	{
		private PhotoInfoSO searchConditon;
		private Handler picHandler;
		public  InnerRunnable(PhotoInfoSO so,Handler handler)
		{
			searchConditon=so;
			picHandler=handler;
		}
		@Override
		public void run()
		{
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putParcelable("photoPic",QueryService.getInstance().getRemotePhotoPic(searchConditon));
			msg.setData(data);
			picHandler.sendMessage(msg);
		}
	};



}
