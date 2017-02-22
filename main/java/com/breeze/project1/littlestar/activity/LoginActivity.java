package com.breeze.project1.littlestar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.breeze.project1.littlestar.BaseActivity;
import com.breeze.project1.littlestar.LittleStarApplication;
import com.breeze.project1.littlestar.R;
import com.breeze.project1.littlestar.common.CommonConstant;
import com.breeze.project1.littlestar.common.CommonUtils;

/**
 * Created by Lesley on 2017/1/22.
 */
public class LoginActivity extends BaseActivity
{
	@Override
	protected void doOnCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.login_layout);
		Button loginButton = (Button)findViewById(R.id.login_button);
		loginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				EditText editTextView = (EditText) findViewById(R.id.serverip);
				final String serverIp = editTextView.getText().toString();
				LittleStarApplication.getInstance().setServerIp(serverIp);
				if(CommonUtils.getInstance().isEmptyStr(serverIp))
				{
					Toast.makeText(LoginActivity.this, R.string.login_input_error, Toast.LENGTH_SHORT).show();
					return;
				}
				Bundle bundle = new Bundle();
				bundle.putString(CommonConstant.SERVER_IP,serverIp);
				Intent intent = new Intent(LoginActivity.this,PhotoListActivity.class);
				intent.putExtra(CommonConstant.SERVER_IP,bundle);
				Log.d("LoginActivity","click");
				startActivity(intent);
			}
		});
	}
}
