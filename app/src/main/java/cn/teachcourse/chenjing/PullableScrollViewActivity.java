package cn.teachcourse.chenjing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.teachcourse.R;

public class PullableScrollViewActivity extends Activity
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
	}
}
