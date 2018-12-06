package com.wanguo.quick_batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.jpa.QRCodeJpa;
import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.pojo.QRCode;
import com.wanguo.quick_batch.service.QRCodeService;
import com.wanguo.quick_batch.service.TokenService;
import com.wanguo.quick_batch.utils.OkHttpUtil;
import com.wanguo.quick_batch.utils.QiNiuUploadUtil;
import com.wanguo.quick_batch.utils.ResJson;
import com.wanguo.quick_batch.utils.WeChatInterfaceCallCredentials;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Date;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {

    private final TokenService tokenService;

    private final QRCodeJpa qrCodeJpa;

    public QRCodeServiceImpl(QRCodeJpa qrCodeJpa, TokenService tokenService) {
        this.tokenService = tokenService;
        this.qrCodeJpa = qrCodeJpa;
    }

    @Override
    public String genQRCode(String accessToken, String scene, String page) {
        QRCode qrCode = qrCodeJpa.findBySceneAndPage(scene, page);
        if (null != qrCode) {
            return qrCode.getPath();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scene", scene);
        jsonObject.put("page", page);
        String jsonParam = jsonObject.toJSONString();
        InputStream result = OkHttpUtil.post("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken, jsonParam);
        String path = QiNiuUploadUtil.upload(result);
        if (!StringUtils.isEmpty(path)) {
            return path;
        }
        return null;
    }

    @Override
    public ResJson genQRCode(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        String scene = jsonObject.getString("scene");
        String page = jsonObject.getString("page");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        String accessToken = WeChatInterfaceCallCredentials.getWeChatAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return ResJson.failJson(4000, "获取微信接口调用凭证失败", null);
        }
        String path = genQRCode(accessToken, scene, page);
        if (StringUtils.isEmpty(path)) {
            return ResJson.failJson(4000, "生成微信二维码失败", null);
        }

        QRCode qrCode = new QRCode();
        qrCode.setScene(scene);
        qrCode.setPage(page);
        qrCode.setPath(path);
        qrCode.setCreateTime(new Date());
        qrCode.setCustomer(customer);
        qrCodeJpa.save(qrCode);

        return ResJson.successJson("生成微信二维码成功", path);
    }

    @Override
    public ResJson scanQRCode(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        String scene = jsonObject.getString("scene");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }

        QRCode qrCode = qrCodeJpa.findByScene(scene);
        if (null == qrCode) {
            return ResJson.failJson(4000, "海报二维码不存在", null);
        }

        return ResJson.successJson("scan qrCode success", qrCode.getScene());
    }
}
