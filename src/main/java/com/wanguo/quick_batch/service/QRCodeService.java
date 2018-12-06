package com.wanguo.quick_batch.service;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.utils.ResJson;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface QRCodeService {

    String genQRCode(String accessToken, String scene, String page);

    ResJson genQRCode(JSONObject jsonObject);

    ResJson scanQRCode(JSONObject jsonObject);

}
