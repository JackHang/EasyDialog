package com.jackhang.lib;

/**
 * Created by Haoz on 2017/4/6 0006.
 */

public interface IDialogResultListener<T> {
    void onDataResult(int which, T result);
}
