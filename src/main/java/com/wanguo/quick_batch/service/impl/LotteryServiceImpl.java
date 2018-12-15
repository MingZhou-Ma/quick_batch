package com.wanguo.quick_batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.jpa.*;
import com.wanguo.quick_batch.pojo.*;
import com.wanguo.quick_batch.service.LotteryService;
import com.wanguo.quick_batch.service.TokenService;
import com.wanguo.quick_batch.utils.AccessToken;
import com.wanguo.quick_batch.utils.LotteryUtil;
import com.wanguo.quick_batch.utils.RandomUtil;
import com.wanguo.quick_batch.utils.ResJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class LotteryServiceImpl implements LotteryService {

    private final TokenService tokenService;

    private final LotteryOpportunityRecordJpa lotteryOpportunityRecordJpa;

    private final CustomerJpa customerJpa;

    private final CustomerLevelJpa customerLevelJpa;

    private final PrizeJpa prizeJpa;

    private final WinningRecordJpa winningRecordJpa;

    @Value("${lottery.rule1}")
    private String rule1;

    @Value("${lottery.rule2}")
    private String rule2;

    @Value("${lottery.rule3}")
    private String rule3;

    @Autowired
    public LotteryServiceImpl(TokenService tokenService, LotteryOpportunityRecordJpa lotteryOpportunityRecordJpa, CustomerJpa customerJpa, CustomerLevelJpa customerLevelJpa, PrizeJpa prizeJpa, WinningRecordJpa winningRecordJpa) {
        this.tokenService = tokenService;
        this.lotteryOpportunityRecordJpa = lotteryOpportunityRecordJpa;
        this.customerJpa = customerJpa;
        this.customerLevelJpa = customerLevelJpa;
        this.prizeJpa = prizeJpa;
        this.winningRecordJpa = winningRecordJpa;
    }


    @Override
    public ResJson obtainLotteryOpportunity(JSONObject jsonObject) {
        String token = jsonObject.getString("token");
        String boostInterval = jsonObject.getString("boostInterval");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }

        /*LotteryOpportunityRecord lotteryOpportunityRecord = lotteryOpportunityRecordJpa.findByBoostIntervalAndCustomer(boostInterval, customer);
        if (null == lotteryOpportunityRecord) {
            // 每个客户最多只有3次抽奖机会
            if (customer.getLotteryOpportunity() + customer.getUsedLotteryOpportunity() < 3) {
                lotteryOpportunityRecord = new LotteryOpportunityRecord();
                lotteryOpportunityRecord.setBoostInterval(boostInterval);
                lotteryOpportunityRecord.setCustomer(customer);
                lotteryOpportunityRecordJpa.save(lotteryOpportunityRecord);

                customer.setLotteryOpportunity(customer.getLotteryOpportunity() + 1);
                customerJpa.save(customer);

                AccessToken accessToken = tokenService.getAccessToken(token);
                if (null != accessToken) {
                    accessToken.setCustomer(customer);
                    tokenService.saveAccessToken(accessToken);
                }
            }
        }*/

        //LotteryOpportunityRecord lotteryOpportunityRecord = lotteryOpportunityRecordJpa.findByBoostIntervalAndCustomer(boostInterval, customer);
            // 每个客户最多只有3次抽奖机会
            if (customer.getLotteryOpportunity() + customer.getUsedLotteryOpportunity() < 3) {
                LotteryOpportunityRecord lotteryOpportunityRecord = new LotteryOpportunityRecord();
                lotteryOpportunityRecord.setBoostInterval(boostInterval);
                lotteryOpportunityRecord.setCustomer(customer);
                lotteryOpportunityRecordJpa.save(lotteryOpportunityRecord);

                customer.setLotteryOpportunity(customer.getLotteryOpportunity() + 1);
                customerJpa.save(customer);

                AccessToken accessToken = tokenService.getAccessToken(token);
                if (null != accessToken) {
                    accessToken.setCustomer(customer);
                    tokenService.saveAccessToken(accessToken);
                }
            }
        //}

        return ResJson.successJson("obtain lottery opportunity success");
    }

    @Override
    public ResJson lottery(JSONObject jsonObject) {
        String token = jsonObject.getString("token");

        Customer customer = tokenService.getCustomerByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }
        if (customer.getLotteryOpportunity() <= 0) {
            return ResJson.failJson(4000, "无抽奖机会", null);
        }
        // 如果已经中过奖
        if (customer.getWhetherWinning()) {
            customer.setLotteryOpportunity(customer.getLotteryOpportunity() - 1);
            customer.setUsedLotteryOpportunity(customer.getUsedLotteryOpportunity() + 1);
            customerJpa.save(customer);

            AccessToken accessToken = tokenService.getAccessToken(token);
            if (null != accessToken) {
                accessToken.setCustomer(customer);
                tokenService.saveAccessToken(accessToken);
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put("index", 7);
            map.put("prize", prizeJpa.findPrizeById(7));
            return ResJson.successJson("抽奖成功", map);
        }

        String lotteryRule = null;
        if (StringUtils.isEmpty(customer.getPhone())) {
            lotteryRule = rule3;
        } else {
            List<CustomerLevel> customerLevelList = customerLevelJpa.findAll();
            if (null != customerLevelList && !customerLevelList.isEmpty()) {
                for (CustomerLevel customerLevel : customerLevelList) {
                    if (customer.getPhone().equals(customerLevel.getPhone())) {
                        if (customerLevel.getLevel().equals("1")) {
                            lotteryRule = rule1;
                        } else {
                            lotteryRule = rule2;
                        }
                        break;
                    } else {
                        lotteryRule = rule3;
                    }
                }
            } else {
                lotteryRule = rule3;
            }
        }
        if (StringUtils.isEmpty(lotteryRule)) {
            return ResJson.failJson(4000, "未设置抽奖规则", null);
        }
        System.out.println("规则：" + lotteryRule);
        String[] lotteryList = lotteryRule.split(",");
        if (StringUtils.isEmpty(lotteryList)) {
            return ResJson.failJson(4000, "未设置抽奖规则", null);
        }
        List<Integer> prizeList = new ArrayList<>();
        List<String> probList = new ArrayList<>();
        for (String lotteryItem : lotteryList) {
            if (!StringUtils.isEmpty(lotteryItem)) {
                String[] item = lotteryItem.split("/");
                if (!StringUtils.isEmpty(item)) {
                    //if (!item[1].equals("0")) {
                    prizeList.add(Integer.valueOf(item[0]));
                    probList.add(item[1]);
                    //}

                }
            }
        }
        if (prizeList.isEmpty() || probList.isEmpty()) {
            return ResJson.failJson(4000, "未设置抽奖规则", null);
        }

        // 奖品下标
        int index = LotteryUtil.drawGift(probList);
        System.out.println("下标:" + index);
        Integer prizeId = prizeList.get(index);
        Prize prize = prizeJpa.findPrizeById(prizeId);
        if (null == prize) {
            return ResJson.failJson(4000, "未设置中奖奖品", null);
        }
        HashMap<String, Object> map = new HashMap<>();
        // 中奖
        //if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4 || index == 5) {
        if (index == 0 || index == 1 || index == 2 || index == 4 || index == 5 || index == 6) {

            // 如果库存为0
            if (prize.getStock() == 0) {
                customer.setLotteryOpportunity(customer.getLotteryOpportunity() - 1);
                customer.setUsedLotteryOpportunity(customer.getUsedLotteryOpportunity() + 1);
                customerJpa.save(customer);

                AccessToken accessToken = tokenService.getAccessToken(token);
                if (null != accessToken) {
                    accessToken.setCustomer(customer);
                    tokenService.saveAccessToken(accessToken);
                }

                map.put("index", 7);
                map.put("prize", prizeJpa.findPrizeById(7));
                return ResJson.successJson("抽奖成功", map);
            } else {
                WinningRecord winningRecord = new WinningRecord();
                // 如果是券，则生成券码
                //if (index == 4 || index == 5) {
                if (index == 2   || index == 5) {
                    winningRecord.setCode(RandomUtil.generateString(6));
                }
                winningRecord.setCreateTime(new Date());
                winningRecord.setPrize(prize);
                winningRecord.setCustomer(customer);
                winningRecordJpa.save(winningRecord);

                prize.setStock(prize.getStock() - 1);
                prizeJpa.save(prize);

                customer.setLotteryOpportunity(customer.getLotteryOpportunity() - 1);
                customer.setUsedLotteryOpportunity(customer.getUsedLotteryOpportunity() + 1);
                customer.setWhetherWinning(true);
                customerJpa.save(customer);

                AccessToken accessToken = tokenService.getAccessToken(token);
                if (null != accessToken) {
                    accessToken.setCustomer(customer);
                    tokenService.saveAccessToken(accessToken);
                }

                map.put("index", index);
                map.put("prize", prize);
                return ResJson.successJson("抽奖成功", map);
            }
        } else { // 未中奖
            customer.setLotteryOpportunity(customer.getLotteryOpportunity() - 1);
            customer.setUsedLotteryOpportunity(customer.getUsedLotteryOpportunity() + 1);
            customerJpa.save(customer);

            AccessToken accessToken = tokenService.getAccessToken(token);
            if (null != accessToken) {
                accessToken.setCustomer(customer);
                tokenService.saveAccessToken(accessToken);
            }

            map.put("index", index);
            map.put("prize", prize);
            return ResJson.successJson("抽奖成功", map);
        }
    }

}
