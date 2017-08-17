package com.jackhang.lib;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by JackHang on 2017/8/11.
 */

public class EasyDialog
{
	private CustomDialogFragment Show(final Builder builder)
	{
		CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(new CustomDialogFragment.OnCallDialog()
		{
			@SuppressWarnings("ConstantConditions")
			@Override
			public Dialog getDialog(Context context)
			{
				Dialog dialog = buildDialog(context, builder);
				assert dialog != null;
				if (builder.dialogBackground != 0)
				{
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
					{
						dialog.getWindow().setBackgroundDrawable(context.getDrawable(builder.dialogBackground));
					} else
					{
						dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(builder.dialogBackground));
					}
				}
				return dialog;
			}
		}, builder.cancelable, builder.isTransparent, builder.mCancelListener);
		dialogFragment.show(builder.mFragmentManager, builder.TAG);
		return dialogFragment;
	}

	@SuppressWarnings("unchecked")
	private Dialog buildDialog(Context context, final Builder builder)
	{
		switch (builder.TYPE)
		{
			case Global.PROGRESS:
				ProgressDialog progressDialog;
				if (builder.THEME != 0)
				{
					progressDialog = new ProgressDialog(context, builder.THEME);
				} else
				{
					progressDialog = new ProgressDialog(context);
				}
				progressDialog.setMessage(builder.message);
				return progressDialog;
			case Global.CUSTOM:
				Dialog mDialog = new Dialog(context);
				mDialog.setContentView(builder.contentView);
				return mDialog;
			case Global.ALTER:
			case Global.ALTER_CUSTOM:
				AlertDialog.Builder alter;
				if (builder.THEME != 0)
				{
					alter = new AlertDialog.Builder(context, builder.THEME);
				} else
				{
					alter = new AlertDialog.Builder(context);
				}
				alter.setTitle(builder.title);
				if (builder.TYPE == Global.ALTER)
				{
					alter.setMessage(builder.message);
				} else
				{
					alter.setView(builder.contentView);
				}
				alter.setPositiveButton(builder.positive, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						if (builder.mListener != null)
						{
							builder.mListener.onDataResult(which);
						}
					}
				});
				alter.setNegativeButton(builder.negative, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						if (builder.mListener != null)
						{
							builder.mListener.onDataResult(which);
						}
					}
				});
				alter.setNeutralButton(builder.neutral, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						if (builder.mListener != null)
						{
							builder.mListener.onDataResult(which);
						}
					}
				});
				return alter.create();
			default:
				return null;
		}
	}

	public static class Builder
	{
		private FragmentManager mFragmentManager;
		private boolean cancelable;
		private boolean isTransparent;
		private String message;
		private String positive;
		private String negative;
		private String neutral;
		private String title;
		private int THEME;
		private String TAG;
		private View contentView;
		@DrawableRes
		private int dialogBackground;
		private IDialogResultListener mListener;
		private CustomDialogFragment.OnDialogCancelListener mCancelListener;

		private int TYPE;

		public Builder(FragmentManager mFragmentManager)
		{
			this.cancelable = false;
			this.THEME = 0;
			this.mFragmentManager = mFragmentManager;
			this.TAG = mFragmentManager.getClass().getSimpleName();
		}

		public Builder setListener(IDialogResultListener mListener)
		{
			this.mListener = mListener;
			return this;
		}

		public Builder setMessage(String message)
		{
			this.message = message;
			return this;
		}

		public Builder setCancelable(boolean cancelable)
		{
			this.cancelable = cancelable;
			return this;
		}

		public Builder setTransparent(boolean isTransparent)
		{
			this.isTransparent = isTransparent;
			return this;
		}

		public Builder setDialogBackground(int dialogBackground)
		{
			this.dialogBackground = dialogBackground;
			return this;
		}

		public Builder setTitle(String title)
		{
			this.title = title;
			return this;
		}

		public Builder setTHEME(int THEME)
		{
			this.THEME = THEME;
			return this;
		}

		public Builder setNegative(String negative)
		{
			this.negative = negative;
			return this;
		}

		public Builder setNeutral(String neutral)
		{
			this.neutral = neutral;
			return this;
		}

		public Builder setPositive(String positive)
		{
			this.positive = positive;
			return this;
		}

		public Builder setContentView(Context context, @LayoutRes int layoutRes)
		{
			contentView = LayoutInflater.from(context).inflate(layoutRes, null);
			return this;
		}

		public Builder setContentView(View contentView)
		{
			this.contentView = contentView;
			return this;
		}

		@SuppressWarnings("unchecked")
		public <T extends View> T getView(@IdRes int viewId)
		{
			View view = contentView.findViewById(viewId);
			return (T) view;
		}

		public Builder setText(int viewId, CharSequence value)
		{
			TextView view = this.getView(viewId);
			view.setText(value);
			return this;
		}

		public Builder setText(int viewId, @StringRes int strId)
		{
			TextView view = this.getView(viewId);
			view.setText(strId);
			return this;
		}

		public Builder setOnClickListener(int viewId, View.OnClickListener listener)
		{
			View view = this.getView(viewId);
			view.setOnClickListener(listener);
			return this;
		}

		public void customBuild()
		{
			if (this.mFragmentManager == null && this.contentView == null)
			{
				throw new IllegalStateException("FragmentManager/contentView is null");
			}
			this.TYPE = Global.CUSTOM;
			EasyDialog easyDialog = new EasyDialog();
			easyDialog.Show(this);
		}

		public CustomDialogFragment progressBuild()
		{
			if (this.mFragmentManager == null)
			{
				throw new IllegalStateException("FragmentManager is null");
			}
			this.TYPE = Global.PROGRESS;
			EasyDialog easyDialog = new EasyDialog();
			return easyDialog.Show(this);
		}

		public void alterBuild()
		{
			if (this.mFragmentManager == null)
			{
				throw new IllegalStateException("FragmentManager is null");
			}
			this.TYPE = Global.ALTER;
			EasyDialog easyDialog = new EasyDialog();
			easyDialog.Show(this);
		}

		public void alterCustomBuild()
		{
			if (this.mFragmentManager == null)
			{
				throw new IllegalStateException("FragmentManager is null");
			}
			this.TYPE = Global.ALTER_CUSTOM;
			EasyDialog easyDialog = new EasyDialog();
			easyDialog.Show(this);
		}
	}
}
