package com.devotion.netty.chapter1;

/**
 * Created by lenovo on 2017/6/1.
 */
public interface FetchCallBack {
    void onData(String data);
    void onError(Throwable cause);
}
