package com.breeze.project1.littlestar.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breeze.project1.littlestar.R;

/**
 * Created by Lesley on 2017/2/2.
 */
public class RecyclerFragment extends Fragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View recyclerFragment = inflater.inflate(R.layout.recycler_layout,container,false);
		SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)recyclerFragment.findViewById(R.id.swipeRefresh_layout);

		swipeRefreshLayout.setOnRefreshListener(new RefreshListener(swipeRefreshLayout));

//		RecyclerView recyclerView  = (RecyclerView)swipeRefreshLayout.findViewById(R.id.recycler_view);





		return recyclerFragment;
	}




	private class  RefreshListener  implements SwipeRefreshLayout.OnRefreshListener
	{

		private SwipeRefreshLayout layout = null;


		public RefreshListener(SwipeRefreshLayout swipeRefreshLayout)
		{
			super();
			layout=swipeRefreshLayout;
		}

		@Override
		public void onRefresh()
		{
			Log.d("RecyclerFragment","Refresh");
		}
	}
}
