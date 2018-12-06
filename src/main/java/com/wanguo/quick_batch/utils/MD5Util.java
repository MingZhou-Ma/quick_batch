package com.wanguo.quick_batch.utils;

import org.springframework.util.DigestUtils;

/**
 * 描述：
 *
 * @author Badguy
 */
public class MD5Util {

    public static String md5(String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }
}
