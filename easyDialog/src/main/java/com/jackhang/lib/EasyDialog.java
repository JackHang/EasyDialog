package com.jackhang.lib;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by JackHang on 2017/8/11.
 */

public class EasyDialog
{
	private EasyDialog(){

	}

	private void customShow(final Builder builder)
	{
		CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(new CustomDialogFragment.OnCallDialog()
		{
			@Override
			public Dialog getDialog(Context context)
			{
				Dialog mDialog = new Dialog(context);
				mDialog.setContentView(builder.contentView);
				return mDialog;
			}
		}, builder.cancelable, builder.mCancelListener);
		dialogFragment.show(builder.mFragmentManager,builder.TAG);
	}

	private void progressShow(final Builder builder)
	{
		CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(new CustomDialogFragment.OnCallDialog()
		{
			@Override
			public Dialog getDialog(Context context)
			{
				ProgressDialog progressDialog;
				if(builder.THEME != 0)
					progressDialog = new ProgressDialog(context,builder.THEME);
				else
					progressDialog = new ProgressDialog(context);
				progressDialog.setMessage(builder.message);
				return progressDialog;
			}
		}, builder.cancelable,builder.mCancelListener);
		dialogFragment.show(builder.mFragmentManager,builder.TAG);
	}

	@SuppressWarnings("unchecked")
	private void baseShow(final Builder builder)
	{
		CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(new CustomDialogFragment.OnCallDialog()
		{
			@Override
			public Dialog getDialog(Context context)
			{
				AlertDialog.Builder dialog;
				if(builder.THEME != 0)
					dialog = new AlertDialog.Builder(context, builder.THEME);
				else
					dialog = new AlertDialog.Builder(context, builder.THEME);
				dialog.setMessage(builder.message);
				dialog.setPositiveButton(builder.positive, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(builder.mListener != null){
							builder.mListener.onDataResult(which,which);
						}
					}
				});
				dialog.setNegativeButton(builder.negative, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(builder.mListener != null){
							builder.mListener.onDataResult(which,which);
						}
					}
				});
				dialog.setNeutralButton(builder.neutral, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						if(builder.mListener != null){
							builder.mListener.onDataResult(which,which);
						}
					}
				});
				return dialog.create();
			}
		}, builder.cancelable, builder.mCancelListener);
		dialogFragment.show(builder.mFragmentManager,builder.TAG);
	}

	public static class Builder
	{
		private FragmentManager mFragmentManager;
		private boolean cancelable;
		private String message;
		private String positive;
		private String negative;
		private String neutral;
		private String title;
		private int THEME;
		private String TAG;
		private View contentView;
		private IDialogResultListener mListener;
		private CustomDialogFragment.OnDialogCancelListener mCancelListener;

		public Builder()
		{
			this.cancelable = false;
			this.TAG = this.getClass().getSimpleName();
			this.THEME = 0;
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

		public Builder setFragmentManager(FragmentManager mFragmentManager)
		{
			this.mFragmentManager = mFragmentManager;
			return this;
		}

		public Builder setCancelable(boolean cancelable)
		{
			this.cancelable = cancelable;
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
			contentView = LayoutInflater.from(context).inflate(layoutRes,null);
			return this;
		}

		public Builder setContentView(View contentView)
		{
			this.contentView = contentView;
			return this;
		}

		@SuppressWarnings("unchecked")
		public <T extends View> T getView(@IdRes int viewId) {
			View view = contentView.findViewById(viewId);
			return (T) view;
		}

		public void customBuild()
		{
			if(this.mFragmentManager == null && this.contentView == null)
			{
				throw new IllegalStateException("FragmentManager/contentView is null");
			}
			EasyDialog easyDialog = new EasyDialog();
			easyDialog.customShow(this);
		}

		public void progressBuild()
		{
			if(this.mFragmentManager == null)
			{
				throw new IllegalStateException("FragmentManager is null");
			}
			EasyDialog easyDialog = new EasyDialog();
			easyDialog.progressShow(this);
		}

		public void baseBuild()
		{
			if(this.mFragmentManager == null)
			{
				throw new IllegalStateException("FragmentManager is null");
			}
			EasyDialog easyDialog = new EasyDialog();
			easyDialog.baseShow(this);
		}
	}
}
