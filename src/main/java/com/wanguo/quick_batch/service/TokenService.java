package com.wanguo.quick_batch.service;

import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.utils.AccessToken;

import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface TokenService {

    //void saveAccessToken(String key, String value, Long timeout, TimeUnit timeUnit);
    void saveAccessToken(AccessToken accessToken);

    AccessToken getAccessToken(String key);

    Customer getCustomerByToken(String key);

    //void refreshAccessToken(String key, Long timeout, TimeUnit timeUnit);
    void refreshAccessToken(String key);
}
