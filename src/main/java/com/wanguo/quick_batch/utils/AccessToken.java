package com.wanguo.quick_batch.utils;

import com.wanguo.quick_batch.pojo.Customer;
import lombok.Data;

/**
 * 描述：
 *
 * @author Badguy
 */
@Data
public class AccessToken {

    public static Long EXPIRED_TIME = 1440L;
    public static String CUSTOMER_TOKEN_SALT = "Bad guy";

    private String token;
    private Customer customer;

    public AccessToken(String token, Customer customer) {
        //this.token = UUID.randomUUID().toString().replaceAll("-", "");
        this.token = token;
        this.customer = customer;
    }

    public AccessToken() {
    }

}
