package com.dev.retrofitlib.net.strategy;

import android.util.Log;

import com.dev.retrofitlib.common.utils.GSONUtil;
import com.dev.retrofitlib.net.core.ApiCache;
import com.dev.retrofitlib.net.mode.CacheResult;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 缓存策略
 */
public abstract class CacheStrategy<T> implements ICacheStrategy<T> {
    <T> Observable<CacheResult<T>> loadCache(final ApiCache apiCache, final String key, final Class<T>
            clazz) {
        return apiCache.<T>get(key).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s != null;
            }
        }).map(new Func1<String, CacheResult<T>>() {
            @Override
            public CacheResult<T> call(String s) {
                T t = GSONUtil.gson().fromJson(s, clazz);
                Log.i("======", "loadCache result=" + t);
                return new CacheResult<T>(true, t);
            }
        });
    }

    <T> Observable<CacheResult<T>> loadRemote(final ApiCache apiCache, final String key, Observable<T> source) {
        return source.map(new Func1<T, CacheResult<T>>() {
            @Override
            public CacheResult<T> call(T t) {
                Log.i("====", "loadRemote result=" + t);
                apiCache.put(key, t).subscribeOn(Schedulers.io()).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean status) {
                        Log.i("=======", "save status => " + status);
                    }
                });
                return new CacheResult<T>(false, t);
            }
        });
    }
}
