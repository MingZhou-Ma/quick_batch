package com.wanguo.quick_batch.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.jpa.CustomerJpa;
import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.service.CustomerService;
import com.wanguo.quick_batch.service.TokenService;
import com.wanguo.quick_batch.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final TokenService tokenService;

    private final CustomerJpa customerJpa;

    @Value("${weChat.appId}")
    private String appId;

    @Value("${weChat.appSecret}")
    private String appSecret;

    @Autowired
    public CustomerServiceImpl(TokenService tokenService, CustomerJpa customerJpa) {
        this.tokenService = tokenService;
        this.customerJpa = customerJpa;
    }

    @Override
    public ResJson login(JSONObject jsonObject) {
        /*System.out.println(appId);
        System.out.println(appSecret);*/
        String code = jsonObject.getString("code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&grant_type=authorization_code&js_code=" + code;
        String result = OkHttpUtil.get(url);
        if (StringUtils.isEmpty(result)) {
            return ResJson.failJson(4000, "请求获取OpenId失败", null);
        }
        JSONObject obj = JSON.parseObject(result);
        String openid = obj.getString("openid");
        String sessionKey = obj.getString("session_key");
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(sessionKey)) {
            return ResJson.failJson(4000, "请求获取OpenId失败", null);
        }
        // 从缓存中取token
        AccessToken accessToken = tokenService.getAccessToken(MD5Util.md5(AccessToken.CUSTOMER_TOKEN_SALT + ":" + openid));
        if (null == accessToken) {
            Customer customer = customerJpa.getByOpenid(openid);
            if (null == customer) {
                customer = new Customer();
                customer.setOpenid(openid);
                customer.setSessionKey(sessionKey);
                customer.setLotteryOpportunity(0);
                customer.setUsedLotteryOpportunity(0);
                customer.setWhetherWinning(false);
                customer.setWhetherAuthInfo(false);
                customer.setWhetherFillShippingAddress(false);
                customer.setCreateTime(new Date());
            } else {
                customer.setSessionKey(sessionKey);
            }
            customerJpa.save(customer);
            accessToken = new AccessToken(MD5Util.md5(AccessToken.CUSTOMER_TOKEN_SALT + ":" + openid), customer);
            tokenService.saveAccessToken(accessToken);
            return ResJson.successJson("登录成功", accessToken);
        } else {
            Customer customer = accessToken.getCustomer();
            customer.setSessionKey(sessionKey);
            customerJpa.save(customer);
            // 刷新缓存
            //tokenService.refreshAccessToken(MD5Util.md5(AccessToken.CUSTOMER_TOKEN_SALT + ":" + openid));
            accessToken.setCustomer(customer);
            tokenService.saveAccessToken(accessToken);
            return ResJson.successJson("登录成功", accessToken);
        }
    }

    @Override
    public ResJson getCustomerInfo(JSONObject jsonObject) {
        String token = jsonObject.getString("token");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        return ResJson.successJson("get customer info success", customer);
    }

    @Override
    public ResJson saveCustomerAuthInfo(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        String nickname = jsonObject.getString("nickname");
        String avatar = jsonObject.getString("avatar");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        customer.setNickname(nickname);
        customer.setAvatar(avatar);
        customer.setWhetherAuthInfo(true);
        customerJpa.save(customer);

        AccessToken accessToken = tokenService.getAccessToken(token);
        if (null != accessToken) {
            accessToken.setCustomer(customer);
            tokenService.saveAccessToken(accessToken);
        }

        return ResJson.successJson("保存用户授权信息成功");
    }

    @Override
    public ResJson whetherAuthInfo(JSONObject jsonObject) {
        String token = jsonObject.getString("token");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }

        if (customer.getWhetherAuthInfo()) {
            return ResJson.successJson("已经授权过", 1);
        }
        return ResJson.successJson("还未授权过", 0);
    }

    @Override
    public ResJson saveCustomerAuthPhone(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        String encryptedData = jsonObject.getString("encryptedData");
        String iv = jsonObject.getString("iv");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        System.out.println("encryptedData:" + encryptedData);
        System.out.println("iv:" + iv);
        System.out.println("sessionKey:" + customer.getSessionKey());

        String decrypt = AES.wxDecrypt(encryptedData, customer.getSessionKey(), iv);
        System.out.println(decrypt);
        if (StringUtils.isEmpty(decrypt)) {
            return ResJson.failJson(4000, "手机号码解密失败", null);
        }
        JSONObject decryptObj = JSON.parseObject(decrypt);
        String phoneNumber = decryptObj.getString("phoneNumber");
        customer.setPhone(phoneNumber);
        customerJpa.save(customer);

        AccessToken accessToken = tokenService.getAccessToken(token);
        if (null != accessToken) {
            accessToken.setCustomer(customer);
            tokenService.saveAccessToken(accessToken);
        }

        return ResJson.successJson("保存用户手机号码成功");
    }

    @Override
    public ResJson saveCustomerPhone(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        String phone = jsonObject.getString("phone");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }

        customer.setPhone(phone);
        customerJpa.save(customer);

        AccessToken accessToken = tokenService.getAccessToken(token);
        if (null != accessToken) {
            accessToken.setCustomer(customer);
            tokenService.saveAccessToken(accessToken);
        }

        return ResJson.successJson("保存用户手机号码成功");
    }

    @Override
    public ResJson saveCustomerDeliveryInfo(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        String receiver = jsonObject.getString("receiver");
        String contactNumber = jsonObject.getString("contactNumber");
        String shippingAddress = jsonObject.getString("shippingAddress");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        if (customer.getWhetherFillShippingAddress()) {
            return ResJson.failJson(4000, "已经保存过收货地址", customer);
        }

        customer.setReceiver(receiver);
        customer.setContactNumber(contactNumber);
        customer.setShippingAddress(shippingAddress);
        customer.setWhetherFillShippingAddress(true);
        customerJpa.save(customer);

        AccessToken accessToken = tokenService.getAccessToken(token);
        if (null != accessToken) {
            accessToken.setCustomer(customer);
            tokenService.saveAccessToken(accessToken);
        }

        return ResJson.successJson("保存用户收货信息成功", customer);
    }

}
