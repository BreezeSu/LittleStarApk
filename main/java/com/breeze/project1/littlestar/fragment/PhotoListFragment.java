package com.breeze.project1.littlestar.fragment;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.breeze.project1.littlestar.R;
import com.breeze.project1.littlestar.activity.PhotoDetailActivity;
import com.breeze.project1.littlestar.adapter.PhotoListAdpt;
import com.breeze.project1.littlestar.common.CommonConstant;
import com.breeze.project1.littlestar.communication.HttpService;
import com.breeze.project1.littlestar.communication.HttpStack;
import com.breeze.project1.littlestar.communication.QueryService;
import com.breeze.project1.littlestar.model.PhotoInfoModel;
import com.breeze.project1.littlestar.model.PhotoInfoRestModel;
import com.breeze.project1.littlestar.model.PhotoInfoSO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Lesley on 2017/1/9.
 */
public class PhotoListFragment extends Fragment
{
	private PhotoListAdpt photoListAdpt = null;
	private String serverIp ;

	private SwipeRefreshLayout swipeRefreshLayout;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intentFromLoginActivity = getActivity().getIntent();
		serverIp = intentFromLoginActivity.getBundleExtra(CommonConstant.SERVER_IP).getString(CommonConstant.SERVER_IP);

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{


		View photoListFragment = inflater.inflate(R.layout.photolist_layout,container,false);

		List<PhotoInfoRestModel> photoList = new ArrayList<PhotoInfoRestModel>();
		photoListAdpt = new PhotoListAdpt(getActivity(),photoList);
		final ListView photoListView = (ListView)photoListFragment.findViewById(R.id.photolist_view);
		photoListView.setAdapter(photoListAdpt);
		final PhotoInfoSO so = new PhotoInfoSO();
		so.setServerIp(serverIp);
		/**
		 *  上拉更多
		 */
		photoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
					case SCROLL_STATE_IDLE:

						Log.d("PhotoListFragment","load more");
						if(photoListView.getLastVisiblePosition() == photoListAdpt.getPhotoList().size()-1)
						{
							//photoListAdpt.getPhotoList().addAll(mokeDate(2));
							photoListAdpt.notifyDataSetChanged();
						}
						break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});




		photoListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				PhotoInfoRestModel photoModel =photoListAdpt.getPhotoList().get(position);

				so.setName(photoModel.getName());
				Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
				intent.putExtra("PhotoInfoSO",so);
				startActivity(intent);
			}
		});


		swipeRefreshLayout = (SwipeRefreshLayout)photoListFragment.findViewById(R.id.swipe_container);

		configSwipeRefreshLayout(swipeRefreshLayout);


		new Thread(new QueryPhotoInfoRunnable(so,handler)).start();
		return photoListFragment;
	}




	private void configSwipeRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout)
	{
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				Log.d("RefreshList","Hello");
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						swipeRefreshLayout.setRefreshing(false);
					}
				}, 6000);
				}
		});
	}







	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			List < PhotoInfoRestModel > photoRestList = (List<PhotoInfoRestModel>) data.getSerializable("photoInfoList");
			photoListAdpt.setPhotoList(photoRestList);
			photoListAdpt.notifyDataSetChanged();
			 PhotoInfoSO so = new PhotoInfoSO();
			so.setServerIp(serverIp);
			 for(int i=0;i<photoRestList.size();i++)
			 {
				 so.setName(photoRestList.get(i).getName());
				 new Thread(new GetSnapViewRunnable(i,snapHandler,so)).start();
			 }
		}
	};




	Handler snapHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			Bitmap snapView =  data.getParcelable("photoSnap");
			int index = data.getInt("index");
			photoListAdpt.getItem(index).setImageSnapView(snapView);
			photoListAdpt.notifyDataSetChanged();
		}
	};




	private class QueryPhotoInfoRunnable implements Runnable
	{
		private PhotoInfoSO searchObject = null;
		private Handler innerHandler = null;

		public QueryPhotoInfoRunnable(PhotoInfoSO so, Handler handler)
		{
			searchObject = so;
			innerHandler = handler;
		}

		@Override
		public void run() {

			Message msg = new Message();
			Bundle data = new Bundle();
			data.putSerializable("photoInfoList",(Serializable)QueryService.getInstance().getRemotePhotoInfo(searchObject));
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}


private class GetSnapViewRunnable implements  Runnable
{
	private int index = 0;
	private Handler getSnapHandler = null;
	private PhotoInfoSO searchObject = null;

	public GetSnapViewRunnable(int idx,Handler snapHandler,PhotoInfoSO so)
	{
		index = idx;
		getSnapHandler = snapHandler;
		searchObject = so;
	}

	@Override
	public void run()
	{
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putParcelable("photoSnap",QueryService.getInstance().getRemotePhotoSnap(searchObject));
		data.putInt("index",index);
		msg.setData(data);
		snapHandler.sendMessage(msg);
	}
}


}
