package com.ad.index;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 09:13
 */
public interface IndexAware<K, V> {

    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);
}
