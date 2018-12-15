package com.wanguo.quick_batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.service.CustomerService;
import com.wanguo.quick_batch.service.WinningRecordService;
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
public class CustomerController {

    private final CustomerService customerService;

    private final WinningRecordService winningRecordService;

    @Autowired
    public CustomerController(CustomerService customerService, WinningRecordService winningRecordService) {
        this.customerService = customerService;
        this.winningRecordService = winningRecordService;
    }

    @RequestMapping(value = "/api/customer/login", method = RequestMethod.POST)
    public ResJson login(@RequestBody JSONObject jsonObject) {
        return customerService.login(jsonObject);
    }

    @RequestMapping(value = "/api/customer/info/get", method = RequestMethod.POST)
    public ResJson getCustomerInfo(@RequestBody JSONObject jsonObject) {
        return customerService.getCustomerInfo(jsonObject);
    }

    @RequestMapping(value = "/api/customer/authInfo/save", method = RequestMethod.POST)
    public ResJson saveCustomerAuthInfo(@RequestBody JSONObject jsonObject) {
        return customerService.saveCustomerAuthInfo(jsonObject);
    }

    @RequestMapping(value = "/api/customer/whether/authInfo", method = RequestMethod.POST)
    public ResJson whetherAuthInfo(@RequestBody JSONObject jsonObject) {
        return customerService.whetherAuthInfo(jsonObject);
    }

    @RequestMapping(value = "/api/customer/authPhone/save", method = RequestMethod.POST)
    public ResJson saveCustomerAuthPhone(@RequestBody JSONObject jsonObject) {
        return customerService.saveCustomerAuthPhone(jsonObject);
    }

    @RequestMapping(value = "/api/customer/phone/save", method = RequestMethod.POST)
    public ResJson saveCustomerPhone(@RequestBody JSONObject jsonObject) {
        return customerService.saveCustomerPhone(jsonObject);
    }

    @RequestMapping(value = "/api/customer/deliveryInfo/save", method = RequestMethod.POST)
    public ResJson saveCustomerDeliveryInfo(@RequestBody JSONObject jsonObject) {
        return customerService.saveCustomerDeliveryInfo(jsonObject);
    }

    @RequestMapping(value = "/api/customer/whether/deliveryInfo/fill", method = RequestMethod.POST)
    public ResJson whetherFillDeliveryInfo(@RequestBody JSONObject jsonObject) {
        return customerService.whetherFillDeliveryInfo(jsonObject);
    }

    @RequestMapping(value = "/api/customer/winningRecord/get", method = RequestMethod.POST)
    public ResJson getMyWinningRecord(@RequestBody JSONObject jsonObject) {
        return winningRecordService.getMyWinningRecord(jsonObject);
    }

    @RequestMapping(value = "/api/customer/weChatInterfaceCallCredentials/get", method = RequestMethod.POST)
    public ResJson getWeChatInterfaceCallCredentials(@RequestBody JSONObject jsonObject) {
        return customerService.getWeChatInterfaceCallCredentials(jsonObject);
    }

}
