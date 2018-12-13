package com.wanguo.quick_batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.service.LotteryService;
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
public class LotteryController {

    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @RequestMapping(value = "/api/lottery", method = RequestMethod.POST)
    public ResJson lottery(@RequestBody JSONObject jsonObject) {
        return lotteryService.lottery(jsonObject);
    }

    @RequestMapping(value = "/api/lottery/opportunity/obtain", method = RequestMethod.POST)
    public ResJson obtainLotteryOpportunity(@RequestBody JSONObject jsonObject) {
        return lotteryService.obtainLotteryOpportunity(jsonObject);
    }

}
