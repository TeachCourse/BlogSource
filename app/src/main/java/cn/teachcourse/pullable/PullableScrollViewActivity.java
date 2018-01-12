package cn.teachcourse.pullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teahcourse.pullable.PullToRefreshLayout;

public class PullableScrollViewActivity extends BaseActivity
{
	public static void start(Context context)
	{
		start(context, null);
	}

	public static void start(Context context, Intent extras)
	{
		Intent intent = new Intent();
		intent.setClass(context, PullableScrollViewActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		if (extras != null) {
			intent.putExtras(extras);
		}
		context.startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
		.setOnRefreshListener(new MyListener());
		initButton(getWindow().getDecorView());
	}

	@Override
	public String getUrl() {
		return null;
	}

	public static class MyListener implements PullToRefreshLayout.OnRefreshListener
	{

		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
		{
			// 下拉刷新操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					// 千万别忘了告诉控件刷新完毕了哦！
					pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 1500);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
		{
			// 加载操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					// 千万别忘了告诉控件加载完毕了哦！
					pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 1500);
		}

	}
}
