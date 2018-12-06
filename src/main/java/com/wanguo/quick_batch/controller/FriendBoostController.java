package com.wanguo.quick_batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.service.FriendBoostService;
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
public class FriendBoostController {

    private final FriendBoostService friendBoostService;

    @Autowired
    public FriendBoostController(FriendBoostService friendBoostService) {
        this.friendBoostService = friendBoostService;
    }

    @RequestMapping(value = "/api/friendBoost/boost", method = RequestMethod.POST)
    public ResJson boost(@RequestBody JSONObject jsonObject) {
        return friendBoostService.boost(jsonObject);
    }

    @RequestMapping(value = "/api/friendBoost/getBoostNum", method = RequestMethod.POST)
    public ResJson getBoostNum(@RequestBody JSONObject jsonObject) {
        return friendBoostService.getBoostNum(jsonObject);
    }

}
