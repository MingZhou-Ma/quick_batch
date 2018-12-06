package com.wanguo.quick_batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.jpa.QRCodeJpa;
import com.wanguo.quick_batch.jpa.WinningRecordJpa;
import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.pojo.QRCode;
import com.wanguo.quick_batch.pojo.WinningRecord;
import com.wanguo.quick_batch.service.ExcelService;
import com.wanguo.quick_batch.service.QRCodeService;
import com.wanguo.quick_batch.service.TokenService;
import com.wanguo.quick_batch.utils.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    private final TokenService tokenService;

    private final WinningRecordJpa winningRecordJpa;


    public ExcelServiceImpl(TokenService tokenService, WinningRecordJpa winningRecordJpa) {
        this.winningRecordJpa = winningRecordJpa;
        this.tokenService = tokenService;
    }


    @Override
    public ResJson exportXlsx(HttpServletResponse response) {
        List<WinningRecord> list = winningRecordJpa.findAll();
        String[] headTitle = {"券码"};
        String[][] data = new String[list.size()][headTitle.length];
        for (int i = 0; i < list.size(); i++) {
            WinningRecord winningRecord = list.get(i);
            data[i][0] = winningRecord.getCode();
        }
        try {
            ExcelUtil.exportXlsx("券码表格", "券码列表", headTitle, data, response);
            return ResJson.successJson("export success");
        } catch (IOException e) {
            e.printStackTrace();
            return ResJson.failJson(4000, "下载excel失败", null);
        }
    }
}
