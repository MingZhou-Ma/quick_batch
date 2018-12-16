package com.wanguo.quick_batch.controller;

import com.wanguo.quick_batch.utils.QiNiuUploadUtil;
import com.wanguo.quick_batch.utils.ResJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author Badguy
 */
@RestController
public class QiNiuController {

    @RequestMapping(value = "/api/qiNiu/upToken/get")
    public ResJson getUpToken() {
        return ResJson.successJson("success", QiNiuUploadUtil.getUpToken());
    }


}
