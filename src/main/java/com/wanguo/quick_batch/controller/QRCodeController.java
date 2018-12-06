package com.wanguo.quick_batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.service.QRCodeService;
import com.wanguo.quick_batch.utils.ResJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author Badguy
 */
@RestController
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @Autowired
    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @RequestMapping(value = "/api/qrCode/gen", method = RequestMethod.POST)
    public ResJson genQRCode(@RequestBody JSONObject jsonObject) {
        return qrCodeService.genQRCode(jsonObject);
    }

    @RequestMapping(value = "/api/qrCode/scan", method = RequestMethod.POST)
    public ResJson scanQRCode(@RequestBody JSONObject jsonObject) {
        return qrCodeService.scanQRCode(jsonObject);
    }
}
