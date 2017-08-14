package com.jackhang.easydialog;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jackhang.lib.EasyDialog;
import com.jackhang.lib.IDialogResultListener;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.menu_dialog, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){

			case R.id.showConfirmDialog:
				new EasyDialog.Builder()
						.setFragmentManager(getSupportFragmentManager())
						.setMessage("")
						.setNegative("No")
						.setPositive("Yes")
						.setListener(new IDialogResultListener<Integer>()
						{
							@Override
							public void onDataResult(int which, Integer result)
							{
								switch (which)
								{
									case DialogInterface.BUTTON_NEGATIVE:
										Toast.makeText(MainActivity.this, "click NEGATIVE", Toast.LENGTH_SHORT).show();
										break;
									case DialogInterface.BUTTON_POSITIVE:
										Toast.makeText(MainActivity.this, "click POSITIVE", Toast.LENGTH_SHORT).show();
										break;
									case DialogInterface.BUTTON_NEUTRAL:
										Toast.makeText(MainActivity.this, "click NEUTRAL", Toast.LENGTH_SHORT).show();
										break;
								}
							}
						})
						.baseBuild();
				break;

			case R.id.showDateDialog:
				break;

			case R.id.showInsertDialog:
				break;

			case R.id.showIntervalInsertDialog:
				break;

			case R.id.showListDialog:
				break;

			case R.id.showPasswordInsertDialog:
				break;

			case R.id.showProgress:
				new EasyDialog.Builder()
						.setFragmentManager(getSupportFragmentManager())
						.setMessage("正在加载中")
						.setTitle("Test")
						.setCancelable(true)
						.progressBuild();
				break;

			case R.id.showTimeDialog:
				break;

			case R.id.showTips:
				TextView text = new TextView(this);
				text.setText("你进入了无网的异次元中");
				new EasyDialog.Builder()
						.setFragmentManager(getSupportFragmentManager())
						.setCancelable(true)
						.setContentView(text)
						.customBuild();
				break;

			case R.id.customDialog:
				EasyDialog.Builder builder = new EasyDialog.Builder()
						.setFragmentManager(getSupportFragmentManager())
						.setCancelable(true)
						.setContentView(this,R.layout.image);

				ImageView view = builder.getView(R.id.image);
				view.setImageResource(R.mipmap.ic_launcher);
				view.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Toast.makeText(MainActivity.this, "Click Image", Toast.LENGTH_SHORT).show();
					}
				});

				builder.customBuild();
				break;

			default:break;
		}
		return true;
	}
}
