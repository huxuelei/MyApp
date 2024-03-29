package com.dev.retrofitlib.net.api;

import android.content.Context;
import android.util.Log;

import com.dev.retrofitlib.common.HttpConfig;
import com.dev.retrofitlib.common.cache.DiskCache;
import com.dev.retrofitlib.common.utils.ClassUtil;
import com.dev.retrofitlib.common.utils.SSLUtil;
import com.dev.retrofitlib.net.callback.ApiCallback;
import com.dev.retrofitlib.net.convert.GsonConverterFactory;
import com.dev.retrofitlib.net.core.ApiCache;
import com.dev.retrofitlib.net.core.ApiCookie;
import com.dev.retrofitlib.net.exception.ApiException;
import com.dev.retrofitlib.net.func.ApiDataFunc;
import com.dev.retrofitlib.net.func.ApiErrFunc;
import com.dev.retrofitlib.net.func.ApiFunc;
import com.dev.retrofitlib.net.func.ApiResultFunc;
import com.dev.retrofitlib.net.interceptor.GzipRequestInterceptor;
import com.dev.retrofitlib.net.interceptor.HeadersInterceptor;
import com.dev.retrofitlib.net.interceptor.OfflineCacheInterceptor;
import com.dev.retrofitlib.net.interceptor.OnlineCacheInterceptor;
import com.dev.retrofitlib.net.mode.ApiCode;
import com.dev.retrofitlib.net.mode.ApiHost;
import com.dev.retrofitlib.net.mode.ApiResult;
import com.dev.retrofitlib.net.mode.CacheMode;
import com.dev.retrofitlib.net.mode.CacheResult;
import com.dev.retrofitlib.net.subscriber.ApiCallbackSubscriber;

import java.io.File;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.FieldMap;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 网络操作入口
 */
public class HttpApi {
    private static Context context;
    private static ApiService apiService;
    private static Retrofit retrofit;
    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient okHttpClient;
    private static OkHttpClient.Builder okHttpBuilder;
    private static ApiCache apiCache;
    private static ApiCache.Builder apiCacheBuilder;
    private static CacheMode cacheMode = CacheMode.ONLY_REMOTE;

    private HttpApi() {
    }

    /**
     * 可传入自定义的接口服务
     */
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * 由外部设置被观察者
     */
    public <T> Observable<T> call(Observable<T> observable) {
        return observable.compose(new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new ApiErrFunc<T>());
            }
        });
    }

    /**
     * 普通Get方式请求，需传入实体类
     */
    public <T> Observable<T> get(String url, Map<String, String> maps, Class<T> clazz) {
        return apiService.get(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 普通Get方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription get(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.get(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 带缓存Get方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，需传入实体类
     */
    public <T> Observable<CacheResult<T>> cacheGet(final String url, final Map<String, String> maps, Class<T> clazz) {
        return this.get(url, maps, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 带缓存Get方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，无需订阅，只需配置Callback回调
     */
    public <T> Subscription cacheGet(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.cacheGet(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 普通POST方式请求，需传入实体类
     */
    public <T> Observable<T> post(final String url, final Map<String, String> parameters, Class<T> clazz) {
        return apiService.post(url, parameters).compose(this.norTransformer(clazz));
    }

    /**
     * sidney
     * 普通POST方式请求，需传入实体类  请求参数为json
     */
    public <T> Observable<T> postJson(final String url, final RequestBody jsonBody, Class<T> clazz) {
        return apiService.postJson(url, jsonBody).compose(this.norTransformer(clazz));
    }

    /**
     * sidney
     * 普通POST方式请求，无需订阅，只需传入Callback回调  请求参数为json
     */
    public <T> Subscription postJson(String url, RequestBody jsonBody, ApiCallback<T> callback) {
        return this.postJson(url, jsonBody, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 普通POST方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription post(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.post(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 带缓存POST方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，需传入实体类
     */
    public <T> Observable<CacheResult<T>> cachePost(final String url, final Map<String, String> maps, Class<T> clazz) {
        return this.post(url, maps, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 带缓存POST方式请求，请求前需配置缓存key，缓存时间默认永久，还可以配置缓存策略，无需订阅，只需配置Callback回调
     */
    public <T> Subscription cachePost(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.cachePost(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 提交表单方式请求，需传入实体类
     */
    public <T> Observable<T> form(final String url, final @FieldMap(encoded = true) Map<String, Object> fields, Class<T> clazz) {
        return apiService.postForm(url, fields).compose(this.norTransformer(clazz));
    }

    /**
     * 提交表单方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription form(final String url, final @FieldMap(encoded = true) Map<String, Object> fields, ApiCallback<T> callback) {
        return this.form(url, fields, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 提交Body方式请求，需传入实体类
     */
    public <T> Observable<T> body(final String url, final Object body, Class<T> clazz) {
        return apiService.postBody(url, body).compose(this.norTransformer(clazz));
    }

    /**
     * 提交Body方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription body(final String url, final Object body, ApiCallback<T> callback) {
        return this.body(url, body, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 删除信息请求，需传入实体类
     */
    public <T> Observable<T> delete(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.delete(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 删除信息请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription delete(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.delete(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 修改信息请求，需传入实体类
     */
    public <T> Observable<T> put(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.put(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 修改信息请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription put(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.put(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 上传图片，需传入请求body和实体类
     */
    public <T> Observable<T> uploadImage(String url, RequestBody requestBody, Class<T> clazz) {
        return apiService.uploadImage(url, requestBody).compose(this.norTransformer(clazz));
    }

    /**
     * 上传图片，需传入图片文件和实体类
     */
    public <T> Observable<T> uploadImage(String url, File file, Class<T> clazz) {
        return apiService.uploadImage(url, RequestBody.create(okhttp3.MediaType.parse("image/jpg; " + "charset=utf-8"), file)).compose
                (this.norTransformer(clazz));
    }

    /**
     * 上传文件
     */
    public <T> Observable<T> uploadFile(String url, RequestBody requestBody, MultipartBody.Part file, Class<T> clazz) {
        return apiService.uploadFile(url, requestBody, file).compose(this.norTransformer(clazz));
    }

    /**
     * 上传多文件
     */
    public <T> Observable<T> uploadFlies(String url, Map<String, RequestBody> files, Class<T> clazz) {
        return apiService.uploadFiles(url, files).compose(this.norTransformer(clazz));
    }

    /*******=============================以下处理服务器返回对象为ApiResult<T>形式的请求================***********/

    /**
     * 由外部设置被观察者
     */
    public <T> Observable<T> apiCall(Observable<T> observable) {
        return observable.map(new Func1<T, T>() {
            @Override
            public T call(T result) {
                if (result instanceof ApiResult) {
                    ApiResult value = (ApiResult) result;
                    return (T) value.getData();
                } else {
                    Throwable throwable = new Throwable("Please call(Observable<T> observable) , < T > is not " + "ApiResult object");
                    new ApiException(throwable, ApiCode.Request.INVOKE_ERROR);
                    return (T) result;
                }
            }
        }).compose(new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new ApiErrFunc<T>());
            }
        });
    }

    /**
     * 返回ApiResult<T>的Get方式请求，需传入实体类
     */
    public <T> Observable<T> apiGet(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.get(url, maps).map(new ApiResultFunc<T>(clazz)).compose(this.<T>apiTransformer());
    }

    /**
     * 返回ApiResult<T>的Get方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription apiGet(final String url, final Map<String, String> maps, ApiCallback<T> callback) {
        return this.apiGet(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 返回ApiResult<T>并带缓存的Get方式请求，需传入实体类
     */
    public <T> Observable<CacheResult<T>> apiCacheGet(final String url, final Map<String, String> maps, Class<T> clazz) {
        return this.apiGet(url, maps, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 返回ApiResult<T>并带缓存的Get方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription apiCacheGet(final String url, final Map<String, String> maps, ApiCallback<T> callback) {
        return this.apiCacheGet(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 返回ApiResult<T>的POST方式请求，需传入实体类
     */
    public <T> Observable<T> apiPost(final String url, final Map<String, String> parameters, Class<T> clazz) {
        return apiService.post(url, parameters).map(new ApiResultFunc<T>(clazz)).compose(this.<T>apiTransformer());
    }

    /**
     * sindey
     * 返回ApiResult<T>的POST方式请求，需传入实体类 传json
     */
    public <T> Observable<T> apiPostJson(final String url, final RequestBody jsonBody, Class<T> clazz) {
        return apiService.postJson(url, jsonBody).map(new ApiResultFunc<T>(clazz)).compose(this.<T>apiTransformer());
    }

    /**
     * sindey
     * 返回ApiResult<T>的POST方式请求，无需订阅，只需传入Callback回调 传json
     */
    public <T> Subscription apiPostJson(final String url, final RequestBody jsonBody, ApiCallback<T> callback) {
        return this.apiPostJson(url, jsonBody, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 返回ApiResult<T>的POST方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription apiPost(final String url, final Map<String, String> parameters, ApiCallback<T> callback) {
        return this.apiPost(url, parameters, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 返回ApiResult<T>并带缓存的POST方式请求，需传入实体类
     */
    public <T> Observable<CacheResult<T>> apiCachePost(final String url, final Map<String, String> parameters, Class<T> clazz) {
        return this.apiPost(url, parameters, clazz).compose(apiCache.transformer(cacheMode, clazz));
    }

    /**
     * 返回ApiResult<T>并带缓存的POST方式请求，无需订阅，只需传入Callback回调
     */
    public <T> Subscription apiCachePost(final String url, final Map<String, String> parameters, ApiCallback<T> callback) {
        return this.apiCachePost(url, parameters, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 清楚所有缓存
     */
    public Subscription clearCache() {
        return apiCache.clear();
    }

    /**
     * 清除对应Key的缓存
     */
    public void removeCache(String key) {
        apiCache.remove(key);
    }

    /**
     * 创建ViseApi.Builder
     */
    public HttpApi.Builder newBuilder(Context context) {
        return new HttpApi.Builder(context);
    }

    private <T> Observable.Transformer<ResponseBody, T> norTransformer(final Class<T> clazz) {
        return new Observable.Transformer<ResponseBody, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                        .mainThread()).map(new ApiFunc<T>(clazz)).onErrorResumeNext(new ApiErrFunc<T>());
            }
        };
    }

    private <T> Observable.Transformer<ApiResult<T>, T> apiTransformer() {
        return new Observable.Transformer<ApiResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                        .mainThread()).map(new ApiDataFunc<T>()).onErrorResumeNext(new ApiErrFunc<T>());
            }
        };
    }

    private static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

    /**
     * HttpApi的所有配置都通过建造者方式创建
     */
    public static final class Builder {
        private okhttp3.Call.Factory callFactory;
        private Converter.Factory converterFactory;
        private CallAdapter.Factory callAdapterFactory;
        private HostnameVerifier hostnameVerifier;
        private SSLSocketFactory sslSocketFactory;
        private ConnectionPool connectionPool;
        private File httpCacheDirectory;
        private ApiCookie apiCookie;
        private Cache cache;
        private String baseUrl;
        private Boolean isCookie = false;
        private Boolean isCache = false;

        public Builder(Context mContext) {
            context = mContext;
            okHttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
            apiCacheBuilder = new ApiCache.Builder(mContext);
        }

        /**
         * 设置自定义OkHttpClient
         */
        public HttpApi.Builder client(OkHttpClient client) {
            retrofitBuilder.client(checkNotNull(client, "client == null"));
            return this;
        }

        /**
         * 设置Call的工厂
         */
        public HttpApi.Builder callFactory(okhttp3.Call.Factory factory) {
            this.callFactory = checkNotNull(factory, "factory == null");
            return this;
        }

        /**
         * 设置连接超时时间（秒）
         */
        public HttpApi.Builder connectTimeout(int timeout) {
            return connectTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * 设置读取超时时间（秒）
         */
        public HttpApi.Builder readTimeout(int timeout) {
            return readTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * 设置写入超时时间（秒）
         */
        public HttpApi.Builder writeTimeout(int timeout) {
            return writeTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * 设置是否添加Cookie
         */
        public HttpApi.Builder cookie(boolean isCookie) {
            this.isCookie = isCookie;
            return this;
        }

        /**
         * 设置是否添加缓存
         */
        public HttpApi.Builder cache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        /**
         * 设置代理
         */
        public HttpApi.Builder proxy(Proxy proxy) {
            okHttpBuilder.proxy(checkNotNull(proxy, "proxy == null"));
            return this;
        }

        /**
         * 设置连接池
         */
        public HttpApi.Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) throw new NullPointerException("connectionPool == null");
            this.connectionPool = connectionPool;
            return this;
        }

        /**
         * 设置连接超时时间
         */
        public HttpApi.Builder connectTimeout(int timeout, TimeUnit unit) {
            if (timeout > -1) {
                Log.i("====timeout====", String.valueOf(timeout));
                okHttpBuilder.connectTimeout(timeout, unit);
            } else {
                Log.i("====timeout====", String.valueOf(timeout) + "====" + HttpConfig.DEFAULT_TIMEOUT);
                okHttpBuilder.connectTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        /**
         * 设置写入超时时间
         */
        public HttpApi.Builder writeTimeout(int timeout, TimeUnit unit) {
            if (timeout > -1) {
                okHttpBuilder.writeTimeout(timeout, unit);
            } else {
                okHttpBuilder.writeTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        /**
         * 设置读取超时时间
         */
        public HttpApi.Builder readTimeout(int timeout, TimeUnit unit) {
            if (timeout > -1) {
                okHttpBuilder.readTimeout(timeout, unit);
            } else {
                okHttpBuilder.readTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        /**
         * 设置请求BaseURL
         */
        public HttpApi.Builder baseUrl(String baseUrl) {
            this.baseUrl = checkNotNull(baseUrl, "baseUrl == null");
            Log.i("===设置请求BaseURL====", baseUrl);
            return this;
        }

        /**
         * 设置转换工厂
         */
        public HttpApi.Builder converterFactory(Converter.Factory factory) {
            this.converterFactory = factory;
            return this;
        }

        /**
         * 设置CallAdapter工厂
         */
        public HttpApi.Builder callAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterFactory = factory;
            return this;
        }

        /**
         * 设置请求头部
         */
        public HttpApi.Builder headers(Map<String, String> headers) {
            okHttpBuilder.addInterceptor(new HeadersInterceptor(headers));
            return this;
        }

        /**
         * 设置请求参数
         */
        public HttpApi.Builder parameters(Map<String, String> parameters) {
            okHttpBuilder.addInterceptor(new HeadersInterceptor(parameters));
            return this;
        }

        /**
         * 设置拦截器
         */
        public HttpApi.Builder interceptor(Interceptor interceptor) {
            okHttpBuilder.addInterceptor(checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

        /**
         * 设置网络拦截器
         */
        public HttpApi.Builder networkInterceptor(Interceptor interceptor) {
            okHttpBuilder.addNetworkInterceptor(checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

        /**
         * 设置Cookie管理
         */
        public HttpApi.Builder cookieManager(ApiCookie cookie) {
            if (cookie == null) throw new NullPointerException("cookieManager == null");
            this.apiCookie = cookie;
            return this;
        }

        /**
         * 设置SSL工厂
         */
        public HttpApi.Builder SSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        /**
         * 设置主机验证机制
         */
        public HttpApi.Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * 使用POST方式是否需要进行GZIP压缩，服务器不支持则不设置
         */
        public HttpApi.Builder postGzipInterceptor() {
            interceptor(new GzipRequestInterceptor());
            return this;
        }

        /**
         * 设置缓存Key，主要针对网路请求结果进行缓存
         */
        public HttpApi.Builder cacheKey(String cacheKey) {
            apiCacheBuilder.cacheKey(checkNotNull(cacheKey, "cacheKey == null"));
            return this;
        }

        /**
         * 设置缓存时间，默认永久，主要针对网路请求结果进行缓存
         */
        public HttpApi.Builder cacheTime(long cacheTime) {
            apiCacheBuilder.cacheTime(Math.max(DiskCache.CACHE_NEVER_EXPIRE, cacheTime));
            return this;
        }

        /**
         * 设置缓存类型，可根据类型自动配置缓存策略，主要针对网络请求结果进行缓存
         */
        public HttpApi.Builder cacheMode(CacheMode mCacheMode) {
            cacheMode = mCacheMode;
            return this;
        }

        /**
         * 设置在线缓存，主要针对网路请求过程进行缓存
         */
        public HttpApi.Builder cacheOnline(Cache cache) {
            networkInterceptor(new OnlineCacheInterceptor());
            this.cache = cache;
            return this;
        }

        /**
         * 设置在线缓存，主要针对网路请求过程进行缓存
         */
        public HttpApi.Builder cacheOnline(Cache cache, final int cacheControlValue) {
            networkInterceptor(new OnlineCacheInterceptor(cacheControlValue));
            this.cache = cache;
            return this;
        }

        /**
         * 设置离线缓存，主要针对网路请求过程进行缓存
         */
        public HttpApi.Builder cacheOffline(Cache cache) {
            networkInterceptor(new OfflineCacheInterceptor(context));
            interceptor(new OfflineCacheInterceptor(context));
            this.cache = cache;
            return this;
        }

        /**
         * 设置离线缓存，主要针对网路请求过程进行缓存
         */
        public HttpApi.Builder cacheOffline(Cache cache, final int cacheControlValue) {
            networkInterceptor(new OfflineCacheInterceptor(context, cacheControlValue));
            interceptor(new OfflineCacheInterceptor(context, cacheControlValue));
            this.cache = cache;
            return this;
        }

        public HttpApi build() {
            if (okHttpBuilder == null) {
                throw new IllegalStateException("okHttpBuilder required.");
            }

            if (retrofitBuilder == null) {
                throw new IllegalStateException("retrofitBuilder required.");
            }

            if (apiCacheBuilder == null) {
                throw new IllegalStateException("apiCacheBuilder required.");
            }

            if (baseUrl == null) {
                this.baseUrl = ApiHost.getHost();
            }
            Log.i("===baseUrl====", baseUrl);
            retrofitBuilder.baseUrl(baseUrl);


            if (converterFactory == null) {
                converterFactory = GsonConverterFactory.create();
            }
            retrofitBuilder.addConverterFactory(converterFactory);

            if (callAdapterFactory == null) {
                callAdapterFactory = RxJavaCallAdapterFactory.create();
            }
            retrofitBuilder.addCallAdapterFactory(callAdapterFactory);

            if (callFactory != null) {
                retrofitBuilder.callFactory(callFactory);
            }

            if (hostnameVerifier == null) {
                hostnameVerifier = new SSLUtil.UnSafeHostnameVerifier(baseUrl);
            }
            okHttpBuilder.hostnameVerifier(hostnameVerifier);

            if (sslSocketFactory == null) {
                sslSocketFactory = SSLUtil.getSslSocketFactory(null, null, null);
            }
            okHttpBuilder.sslSocketFactory(sslSocketFactory);

            //超时时间的设置
            okHttpBuilder.connectTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okHttpBuilder.writeTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            if (connectionPool == null) {
                connectionPool = new ConnectionPool(HttpConfig.DEFAULT_MAX_IDLE_CONNECTIONS, HttpConfig.DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS);
            }
            okHttpBuilder.connectionPool(connectionPool);

            if (isCookie && apiCookie == null) {
                apiCookie = new ApiCookie(context);
            }
            if (isCookie) {
                okHttpBuilder.cookieJar(apiCookie);
            }

            if (httpCacheDirectory == null) {
                httpCacheDirectory = new File(context.getCacheDir(), HttpConfig.CACHE_HTTP_DIR);
            }
            if (isCache) {
                try {
                    if (cache == null) {
                        cache = new Cache(httpCacheDirectory, HttpConfig.CACHE_MAX_SIZE);
                    }
                    cacheOnline(cache);
                    cacheOffline(cache);
                } catch (Exception e) {
                    Log.e("======", "Could not create http cache" + e);
                }
            }
            if (cache != null) {
                okHttpBuilder.cache(cache);
            }

            okHttpClient = okHttpBuilder.build();
            retrofitBuilder.client(okHttpClient);
            retrofit = retrofitBuilder.build();
            apiCache = apiCacheBuilder.build();
            apiService = retrofit.create(ApiService.class);

            return new HttpApi();
        }
    }
}
