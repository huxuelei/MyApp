package com.dev.retrofitlib.net.strategy;

import com.dev.retrofitlib.net.core.ApiCache;
import com.dev.retrofitlib.net.mode.CacheResult;

import rx.Observable;

/**
 * @Description: 缓存策略--只取网络
 */
public class OnlyRemoteStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Class<T> clazz) {
        return loadRemote(apiCache, cacheKey, source);
    }
}
