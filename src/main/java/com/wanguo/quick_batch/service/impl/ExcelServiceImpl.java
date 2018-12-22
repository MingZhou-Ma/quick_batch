package com.wanguo.quick_batch.service.impl;

import com.wanguo.quick_batch.jpa.CustomerLevelJpa;
import com.wanguo.quick_batch.jpa.WinningRecordJpa;
import com.wanguo.quick_batch.pojo.CustomerLevel;
import com.wanguo.quick_batch.pojo.WinningRecord;
import com.wanguo.quick_batch.service.ExcelService;
import com.wanguo.quick_batch.utils.ExcelUtil;
import com.wanguo.quick_batch.utils.ResJson;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    private final CustomerLevelJpa customerLevelJpa;

    private final WinningRecordJpa winningRecordJpa;

    public ExcelServiceImpl(CustomerLevelJpa customerLevelJpa, WinningRecordJpa winningRecordJpa) {
        this.customerLevelJpa = customerLevelJpa;
        this.winningRecordJpa = winningRecordJpa;
    }

    @Override
    public ResJson exportXlsx(HttpServletResponse response) {
        List<WinningRecord> list = winningRecordJpa.findAll();
        String[] headTitle = {"姓名", "电话号码", "收货地址", "中奖物品", "券码", "中奖时间"};
        String[][] data = new String[list.size()][headTitle.length];
        for (int i = 0; i < list.size(); i++) {
            WinningRecord winningRecord = list.get(i);
            data[i][0] = winningRecord.getCustomer().getReceiver();
            data[i][1] = winningRecord.getCustomer().getContactNumber();
            data[i][2] = winningRecord.getCustomer().getShippingAddress();
            data[i][3] = winningRecord.getPrize().getName();
            data[i][4] = winningRecord.getCode();
            data[i][5] = winningRecord.getCreateTime().toString();
        }
        try {
            ExcelUtil.exportXlsx("中奖记录", "中奖列表", headTitle, data, response);
            return ResJson.successJson("export success");
        } catch (IOException e) {
            e.printStackTrace();
            return ResJson.failJson(4000, "下载excel失败", null);
        }
    }

    @Override
    public ResJson readExcel() {
        try {
            System.out.println(111);
            List<List<String>> lists = ExcelUtil.readExcel1("customer_level.xlsx");
            System.out.println(222);
            if (null == lists || lists.isEmpty()) {
                return ResJson.failJson(4000, "读取excel失败", null);
            }
            customerLevelJpa.deleteAllInBatch();
            CustomerLevel customerLevel;
            System.out.println("数组大小：" + lists.size());
            for (List<String> list : lists) {
                if (null != list && !list.isEmpty()) {
                    /*if (list.size()>= 2) {*/
                    customerLevel = new CustomerLevel();
                    customerLevel.setPhone(list.get(0));
                    customerLevel.setLevel(list.get(1));
                    customerLevelJpa.save(customerLevel);
//                    }
                }
            }
            return ResJson.successJson("读取excel成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResJson.failJson(4000, "读取excel失败", null);
    }
}
