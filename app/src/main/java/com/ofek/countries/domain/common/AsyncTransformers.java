package com.ofek.countries.domain.common;


import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.Schedulers;

/**
 * provides transformers which subscribes on background thread
 */
public class AsyncTransformers {

    public static  <T> SingleTransformer<T,T>  getSingleTransformer(){
        return upstream -> upstream.subscribeOn(Schedulers.io());
    }
}
