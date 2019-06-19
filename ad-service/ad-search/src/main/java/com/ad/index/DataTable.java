package com.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/16 11:06
 * 索引服务类缓存的实现
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class, Object> Data_Table_Map = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        //保证最先加载
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz) {
        T instance = (T) Data_Table_Map.get(clazz);
        if (instance != null) {
            return instance;
        }
        Data_Table_Map.put(clazz, bean(clazz));
        return (T) Data_Table_Map.get(clazz);
    }

    @SuppressWarnings("all")
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

}
