package com.wanguo.quick_batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.jpa.WinningRecordJpa;
import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.pojo.WinningRecord;
import com.wanguo.quick_batch.service.TokenService;
import com.wanguo.quick_batch.service.WinningRecordService;
import com.wanguo.quick_batch.utils.ResJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class WinningRecordServiceImpl implements WinningRecordService {

    private final TokenService tokenService;

    private final WinningRecordJpa winningRecordJpa;

    @Autowired
    public WinningRecordServiceImpl(TokenService tokenService, WinningRecordJpa winningRecordJpa) {
        this.tokenService = tokenService;
        this.winningRecordJpa = winningRecordJpa;
    }

    @Override
    public ResJson getMyWinningRecord(JSONObject jsonObject) {
        String token = jsonObject.getString("token");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }

        List<WinningRecord> list = winningRecordJpa.findAllByCustomerOrderByCreateTimeDesc(customer);
        return ResJson.successJson("get my winning record success", list);
    }
}
