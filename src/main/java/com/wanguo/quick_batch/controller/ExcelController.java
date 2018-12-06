package com.wanguo.quick_batch.controller;

import com.wanguo.quick_batch.service.ExcelService;
import com.wanguo.quick_batch.service.QRCodeService;
import com.wanguo.quick_batch.utils.ExcelUtil;
import com.wanguo.quick_batch.utils.ResJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述：
 *
 * @author Badguy
 */
@RestController
public class ExcelController {

    private final ExcelService excelService;

    @Autowired
    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @RequestMapping(value = "/api/excel/export")
    public ResJson exportXlsx(HttpServletResponse response) {
        return excelService.exportXlsx(response);
    }

    @RequestMapping(value = "/api/excel/read")
    public ResJson exportXlsx() {

            ExcelUtil.read(0,1);

        return null;
    }


}
