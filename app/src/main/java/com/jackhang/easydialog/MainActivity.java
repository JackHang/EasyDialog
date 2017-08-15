package com.jackhang.easydialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jackhang.lib.EasyDialog;
import com.jackhang.lib.IDialogResultListener;

public class MainActivity extends AppCompatActivity
{
	private DialogFragment mDialogFragment;

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
						.setMessage("ConfirmDialog")
						.setNegative("No")
						.setPositive("Yes")
						.setNeutral("Neutral")
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
									default:break;
								}
							}
						})
						.alterBuild();
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
				mDialogFragment = new EasyDialog.Builder()
						.setFragmentManager(getSupportFragmentManager())
						.setMessage("正在加载中")
						.setTitle("Test")
						.setCancelable(true)
						.progressBuild();
				Handler handler=new Handler();
				Runnable runnable=new Runnable() {
					@Override
					public void run() {
						mDialogFragment.dismiss();
					}
				};
				handler.postDelayed(runnable, 2000);
				break;
			case R.id.showTimeDialog:
				break;

			case R.id.showTips:
				new EasyDialog.Builder()
						.setFragmentManager(getSupportFragmentManager())
						.setCancelable(true)
						.setMessage("你进入了无网的异次元中")
						.setTitle("Tips")
						.alterBuild();
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
