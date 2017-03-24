package com.sidney.devlib.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * RxBus使用消息传递
 * Created by Administrator on 2016/12/13.
 * 使用方法：
 * 1、创建你需要发送的事件类
 * public class StudentEvent {
 * private String id;
 * private String name;
 * <p/>
 * public StudentEvent(String id, String name) {
 * this.id = id;
 * this.name = name;
 * }
 * <p/>
 * public String getId() {
 * return id;
 * }
 * <p/>
 * public void setId(String id) {
 * this.id = id;
 * }
 * <p/>
 * public String getName() {
 * return name;
 * }
 * <p/>
 * public void setName(String name) {
 * this.name = name;
 * }
 * }
 * 2、发送事件
 * RxBus.getInstance().post(new StudentEvent("007","小明"));
 * 3、接收事件
 * rxSbscription=RxBus.getInstance().toObserverable(StudentEvent.class)
 * .subscribe(new Action1<StudentEvent>() {
 *
 * @Override public void call(StudentEvent studentEvent) {
 * textView.setText("id:"+ studentEvent.getId()+"  name:"+ studentEvent.getName());
 * }
 * });
 * 4、取消订阅
 * @Override protected void onDestroy() {
 * if (!rxSbscription.isUnsubscribed()){
 * rxSbscription.unsubscribe();
 * }
 * super.onDestroy();
 * }
 */
public class RxBusUtil {
    private static volatile RxBusUtil mInstance;

    private final Subject bus;

    public RxBusUtil() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例模式RxBus
     *
     * @return
     */
    public static RxBusUtil getInstance() {
        RxBusUtil rxBus2 = mInstance;
        if (mInstance == null) {
            synchronized (RxBusUtil.class) {
                rxBus2 = mInstance;
                if (mInstance == null) {
                    rxBus2 = new RxBusUtil();
                    mInstance = rxBus2;
                }
            }
        }
        return rxBus2;
    }

    /**
     * 发送消息
     *
     * @param object
     */
    public void post(Object object) {
        bus.onNext(object);
    }

    /**
     * 接收消息
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
