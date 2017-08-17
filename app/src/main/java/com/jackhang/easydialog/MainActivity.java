package com.jackhang.easydialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jackhang.lib.EasyDialog;
import com.jackhang.lib.IDialogResultListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements IDialogResultListener
{
	private DialogFragment mDialogFragment;
	private int THEME = 0;
	private Calendar calendar = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (THEME != 0)
				{
					THEME = 0;
				} else
				{
					THEME = R.style.Base_AlertDialog;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.menu_dialog, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.showConfirmDialog:
				showConfirmDialog();
				break;

			case R.id.showDateDialog:
				final DatePickerDialog datePickerDialog = new DatePickerDialog(this, THEME, new DatePickerDialog
						.OnDateSetListener()
				{
					@Override
					public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
					{
						Toast.makeText(MainActivity.this, "Time : " + year + "/" + (month + 1) + "/" + dayOfMonth, Toast
								.LENGTH_SHORT)
								.show();
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

				datePickerDialog.setTitle("data");
				datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener()
				{
					@Override
					public void onShow(DialogInterface dialog)
					{
						datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("Yes");
						datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText("No");
					}
				});
				new EasyDialog.Builder(getSupportFragmentManager())
						.setCoustomDialog(datePickerDialog)
						.customDialogBuild();
				break;

			case R.id.showInsertDialog:
				showInsertDialog();
				break;

			case R.id.showIntervalInsertDialog:
				showIntervalInsertDialog();
				break;

			case R.id.showListDialog:
				showListDialog();
				break;

			case R.id.showPasswordInsertDialog:
				showPasswordInsertDialog();
				break;

			case R.id.showProgress:
				showProgress();
				break;
			case R.id.showTimeDialog:
				final TimePickerDialog dateDialog = new TimePickerDialog(this, THEME, new TimePickerDialog.OnTimeSetListener()
				{
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute)
					{
						Toast.makeText(MainActivity.this, "Time : " + hourOfDay + "：" + minute, Toast.LENGTH_SHORT).show();
					}
				}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

				dateDialog.setTitle("Time");
				dateDialog.setOnShowListener(new DialogInterface.OnShowListener()
				{
					@Override
					public void onShow(DialogInterface dialog)
					{
						dateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("Yes");
						dateDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText("No");
					}
				});
				new EasyDialog.Builder(getSupportFragmentManager())
						.setCoustomDialog(dateDialog)
						.customDialogBuild();
				break;

			case R.id.showTips:
				showTips();
				break;

			case R.id.customDialog:
				customDialog();
				break;

			default:
				break;
		}
		return true;
	}

	private void showListDialog()
	{
		ArrayList<String> strings = new ArrayList<>();
		strings.add("Android");
		strings.add("Ios");
		strings.add("前端");
		strings.add("福利");
		new EasyDialog.Builder(getSupportFragmentManager())
				.setTHEME(THEME)
				.setCancelable(true)
				.Items(strings)
				.setListener(this)
				.setTitle("Tips")
				.alterListBuild();
	}

	private void customDialog()
	{
		EasyDialog.Builder builder = new EasyDialog.Builder(getSupportFragmentManager())
				.setTHEME(THEME)
				.setCancelable(true)
				.setContentView(this, R.layout.image);

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
	}

	private void showTips()
	{
		new EasyDialog.Builder(getSupportFragmentManager())
				.setTHEME(THEME)
				.setCancelable(true)
				.setMessage("你进入了无网的异次元中")
				.setTitle("Tips")
				.alterBuild();
	}

	private void showProgress()
	{
		mDialogFragment = new EasyDialog.Builder(getSupportFragmentManager())
				.setTHEME(THEME)
				.setDialogBackground(R.drawable.loading_dialog_corner)
				.setMessage("正在加载中")
				.setTitle("Test")
				.setCancelable(true)
				.progressBuild();
		Handler handler = new Handler();
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				mDialogFragment.dismiss();
			}
		};
		handler.postDelayed(runnable, 5000);
	}

	private void showPasswordInsertDialog()
	{
		final EditText passwordEdit = new EditText(this);
		passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		passwordEdit.setEnabled(true);
		new EasyDialog.Builder(getSupportFragmentManager())
				.setContentView(passwordEdit)
				.setTHEME(THEME)
				.setNegative("No")
				.setPositive("Yes")
				.setListener(new IDialogResultListener()
				{
					@Override
					public void onDataResult(int which)
					{
						switch (which)
						{
							case DialogInterface.BUTTON_NEGATIVE:
								Toast.makeText(MainActivity.this, "click NEGATIVE", Toast.LENGTH_SHORT).show();
								break;
							case DialogInterface.BUTTON_POSITIVE:
								Toast.makeText(MainActivity.this, passwordEdit.getText().toString(), Toast.LENGTH_SHORT)
										.show();
								break;
							default:
								break;
						}
					}

					@Override
					public void onDataResult(String what)
					{

					}
				})
				.alterCustomBuild();
	}

	private void showIntervalInsertDialog()
	{
		EasyDialog.Builder insertBuilder = new EasyDialog.Builder(getSupportFragmentManager())
				.setTHEME(THEME)
				.setCancelable(true)
				.setContentView(this, R.layout.dialog_interval_insert);

		final EditText minEditText = insertBuilder.getView(R.id.interval_insert_et_min);
		final EditText maxEditText = insertBuilder.getView(R.id.interval_insert_et_max);

		insertBuilder.setNegative("No")
				.setPositive("Yes")
				.setListener(new IDialogResultListener()
				{
					@Override
					public void onDataResult(int which)
					{
						switch (which)
						{
							case DialogInterface.BUTTON_NEGATIVE:
								Toast.makeText(MainActivity.this, "click NEGATIVE", Toast.LENGTH_SHORT).show();
								break;
							case DialogInterface.BUTTON_POSITIVE:
								Toast.makeText(MainActivity.this, minEditText.getText().toString() + " ~ " +
										maxEditText.getText().toString(), Toast.LENGTH_SHORT)
										.show();
								break;
							default:
								break;
						}
					}

					@Override
					public void onDataResult(String what)
					{

					}
				})
				.alterCustomBuild();
	}

	private void showInsertDialog()
	{
		final EditText editText = new EditText(this);
		editText.setBackground(null);
		editText.setPadding(60, 40, 0, 0);
		new EasyDialog.Builder(getSupportFragmentManager())
				.setContentView(editText)
				.setTHEME(THEME)
				.setNegative("No")
				.setPositive("Yes")
				.setListener(new IDialogResultListener()
				{
					@Override
					public void onDataResult(int which)
					{
						switch (which)
						{
							case DialogInterface.BUTTON_NEGATIVE:
								Toast.makeText(MainActivity.this, "click NEGATIVE", Toast.LENGTH_SHORT).show();
								break;
							case DialogInterface.BUTTON_POSITIVE:
								Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT)
										.show();
								break;
							default:
								break;
						}
					}

					@Override
					public void onDataResult(String what)
					{

					}
				})
				.alterCustomBuild();
	}

	private void showConfirmDialog()
	{
		new EasyDialog.Builder(getSupportFragmentManager())
				.setTHEME(THEME)
				.setMessage("ConfirmDialog")
				.setNegative("No")
				.setPositive("Yes")
				.setNeutral("Neutral")
				.setListener(this)
				.alterBuild();
	}

	@Override
	public void onDataResult(int which)
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
			default:
				break;
		}
	}

	@Override
	public void onDataResult(String what)
	{
		Toast.makeText(MainActivity.this, "click " + what, Toast.LENGTH_SHORT).show();
	}
}
