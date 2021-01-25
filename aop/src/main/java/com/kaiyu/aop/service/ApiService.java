package com.kaiyu.aop.service;

/**
 * @author: ysbian
 * @date: 2019/11/5
 */

import java.util.Objects;

@FunctionalInterface
public interface ApiService<T, R> {


    R handle(T t);


    default <V> ApiService<V, R> compose(ApiService<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> handle(before.handle(v));
    }


    default <V> ApiService<T, V> andThen(ApiService<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.handle(handle(t));
    }

}
