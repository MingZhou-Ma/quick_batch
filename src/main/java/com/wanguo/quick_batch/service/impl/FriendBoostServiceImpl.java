package com.wanguo.quick_batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.jpa.CustomerJpa;
import com.wanguo.quick_batch.jpa.FriendBoostJpa;
import com.wanguo.quick_batch.jpa.PrizeJpa;
import com.wanguo.quick_batch.jpa.WinningRecordJpa;
import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.pojo.FriendBoost;
import com.wanguo.quick_batch.pojo.Prize;
import com.wanguo.quick_batch.pojo.WinningRecord;
import com.wanguo.quick_batch.service.FriendBoostService;
import com.wanguo.quick_batch.service.TokenService;
import com.wanguo.quick_batch.utils.RandomUtil;
import com.wanguo.quick_batch.utils.ResJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class FriendBoostServiceImpl implements FriendBoostService {

    private final TokenService tokenService;

    private final CustomerJpa customerJpa;

    private final FriendBoostJpa friendBoostJpa;

    private final PrizeJpa prizeJpa;

    private final WinningRecordJpa winningRecordJpa;

    @Autowired
    public FriendBoostServiceImpl(TokenService tokenService, CustomerJpa customerJpa, FriendBoostJpa friendBoostJpa, PrizeJpa prizeJpa, WinningRecordJpa winningRecordJpa) {
        this.tokenService = tokenService;
        this.customerJpa = customerJpa;
        this.friendBoostJpa = friendBoostJpa;
        this.prizeJpa = prizeJpa;
        this.winningRecordJpa = winningRecordJpa;
    }

    @Override
    public ResJson boost(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        Integer id = jsonObject.getInteger("id");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        Customer initiator = customerJpa.getOne(id);
        if (null == initiator) {
            return ResJson.failJson(4000, "活动发起者不存在", null);
        }
        if (customer == initiator) {
            return ResJson.failJson(4000, "不能给自己助力", null);
        }

        FriendBoost friendBoost = friendBoostJpa.findByInitiatorAndBooster(initiator, customer);
        if (null != friendBoost) {
            return ResJson.successJson("已经助力过了");
        }
        friendBoost = new FriendBoost();
        friendBoost.setCreateTime(new Date());
        friendBoost.setInitiator(initiator);
        friendBoost.setBooster(customer);
        friendBoostJpa.save(friendBoost);

        // 助力完获取代金券
        Prize prize = prizeJpa.getOne(9);

        WinningRecord winningRecord = new WinningRecord();
        winningRecord.setCode(RandomUtil.generateString(6));
        winningRecord.setCreateTime(new Date());
        winningRecord.setPrize(prize);
        winningRecord.setCustomer(customer);
        winningRecordJpa.save(winningRecord);

        return ResJson.successJson("助力成功", winningRecord);
    }

    @Override
    public ResJson getBoostNum(JSONObject jsonObject) {
        String token = jsonObject.getString("token");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        List<FriendBoost> list = friendBoostJpa.findAllByInitiator(customer);
        if (null != list && !list.isEmpty()) {
            return ResJson.successJson("get boost num success", list.size());
        }
        return ResJson.successJson("get boost num success", 0);
    }
}
