package com.example.tulib.util.base;

import rx.Subscription;

/**
 * 作者： Hokas
 * 时间： 2017/1/5
 * 类别：
 */

public interface presenter {
    void onStart();
    void onDestroy();
    void unSubscribe(Subscription subscription);
}
