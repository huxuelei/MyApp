package com.dev.retrofitlib.net.strategy;

import com.dev.retrofitlib.net.core.ApiCache;
import com.dev.retrofitlib.net.mode.CacheResult;

import rx.Observable;

/**
 * @Description: 缓存策略接口
 */
public interface ICacheStrategy<T> {
    <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Class<T> clazz);
}
