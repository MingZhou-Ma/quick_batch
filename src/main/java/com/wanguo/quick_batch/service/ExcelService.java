package com.wanguo.quick_batch.service;

import com.alibaba.fastjson.JSONObject;
import com.wanguo.quick_batch.utils.ResJson;

import javax.servlet.http.HttpServletResponse;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface ExcelService {

    ResJson exportXlsx(HttpServletResponse response);

    ResJson readExcel();

}
