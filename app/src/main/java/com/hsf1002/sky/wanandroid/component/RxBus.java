package com.hsf1002.sky.wanandroid.component;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by hefeng on 18-4-10.
 */

public class RxBus {
    private final FlowableProcessor<Object> bus;

    /*
    * PublishSubject只会把在订阅发生的时间点之后来自原始Flowable的数据发射给观察者
    * */
    public RxBus() {
        this.bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault()
    {
        return RxBusHolder.INSTANCE;
    }

    private static class RxBusHolder
    {
        private static final RxBus INSTANCE = new RxBus();
    }

    /*
    * 提供了一个新的事件
    * */
    public void post(Object object)
    {
        bus.onNext(object);
    }

    /*
    * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    * */
    public <T> Flowable<T> toFlowable(Class<T> eventType)
    {
        return bus.ofType(eventType);
    }
}
