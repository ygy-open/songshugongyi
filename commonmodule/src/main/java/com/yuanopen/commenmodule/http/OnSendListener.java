package com.yuanopen.commenmodule.http;

/**
 * Created by yuanopen on 2018/7/20/020.
 */

public interface OnSendListener<T> {
    abstract void onFail(String msdg);
    abstract  void onSuccess(T modle);
}
