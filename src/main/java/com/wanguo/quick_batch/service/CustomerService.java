package com.wanguo.quick_batch.service;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.utils.ResJson;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface CustomerService {

    ResJson login(JSONObject jsonObject);

    ResJson checkAccessToken(JSONObject jsonObject);

    ResJson getCustomerInfo(JSONObject jsonObject);

    ResJson getOtherCustomerInfo(JSONObject jsonObject);

    ResJson saveCustomerAuthInfo(JSONObject jsonObject);

    ResJson whetherAuthInfo(JSONObject jsonObject);

    ResJson saveCustomerAuthPhone(JSONObject jsonObject);

    ResJson saveCustomerPhone(JSONObject jsonObject);

    ResJson saveCustomerDeliveryInfo(JSONObject jsonObject);

    ResJson whetherFillDeliveryInfo(JSONObject jsonObject);

    ResJson getWeChatInterfaceCallCredentials(JSONObject jsonObject);

    ResJson serviceNotice(JSONObject jsonObject);

    ResJson saveFormId(JSONObject jsonObject);

}
