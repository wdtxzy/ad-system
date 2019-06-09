package com.ad.dao;

import com.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/9 21:39
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * <h2>根据用户名查找用户记录</h2>
     *
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
