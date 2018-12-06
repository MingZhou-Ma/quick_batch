package com.wanguo.quick_batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.service.TokenService;
import com.wanguo.quick_batch.utils.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class TokenServiceImpl implements TokenService {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public TokenServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /*@Override
    public void saveAccessToken(String key, String value, Long l, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, l, timeUnit);
    }*/

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        stringRedisTemplate.opsForValue().set(accessToken.getToken(), JSONObject.toJSONString(accessToken), AccessToken.EXPIRED_TIME, TimeUnit.MINUTES);
    }

    @Override
    public AccessToken getAccessToken(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String tokenString = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(tokenString)) {
            return null;
        }
        return JSONObject.parseObject(tokenString, AccessToken.class);
    }

    @Override
    public Customer getCustomerByToken(String key) {
        AccessToken accessToken = getAccessToken(key);
        if (null != accessToken) {
            return accessToken.getCustomer();
        }
        return null;
    }

    @Override
    public void refreshAccessToken(String key) {
        stringRedisTemplate.expire(key, AccessToken.EXPIRED_TIME, TimeUnit.MINUTES);
    }

//    @Override
//    public void refreshAccessToken(String key, Long timeout, TimeUnit timeUnit) {
//        stringRedisTemplate.expire(key, timeout, timeUnit);
//    }
}
