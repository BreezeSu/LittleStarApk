package com.breeze.project1.littlestar.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import com.breeze.project1.littlestar.BaseActivity;
import com.breeze.project1.littlestar.R;
import com.breeze.project1.littlestar.communication.HttpService;
import com.breeze.project1.littlestar.communication.HttpStack;
import com.breeze.project1.littlestar.fragment.PhotoListFragment;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by Lesley on 2017/1/10.
 */
public class PhotoListActivity extends BaseActivity
{

	@Override
	protected void doOnCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.activity_main);
		FragmentManager fmgr = getFragmentManager();
		Fragment fragment = new PhotoListFragment();
		fmgr.beginTransaction().add(R.id.fragment_contain,fragment).commit();


	}



}
