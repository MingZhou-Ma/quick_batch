package com.wanguo.quick_batch.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 描述：微信接口调用凭证
 *
 * @author Badguy
 */
@Component
public class WeChatInterfaceCallCredentials {

    private static String appId;
    private static String appSecret;

    @Value("${weChat.appId}")
    public void setAppId(String appId) {
        WeChatInterfaceCallCredentials.appId = appId;
    }

    @Value("${weChat.appSecret}")
    public void setAppSecret(String appSecret) {
        WeChatInterfaceCallCredentials.appSecret = appSecret;
    }

    public static String getWeChatAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        String result = OkHttpUtil.get(url);
        if (!StringUtils.isEmpty(result)) {
            if (result.contains("access_token")) {
                JSONObject obj = JSON.parseObject(result);
                return obj.getString("access_token");
            }
        }
        return null;
    }
}
