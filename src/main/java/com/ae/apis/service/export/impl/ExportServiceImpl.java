package com.ae.apis.service.export.impl;

import com.ae.apis.service.export.ExportService;
import com.ae.apis.service.export.common.ExportExcelUtils;
import com.ae.apis.service.export.dto.ExcelField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public <T> void exportFile(List<T> dataItems, List<ExcelField> columns, String excelName, HttpServletResponse res) {
        logger.info("Start export file \"" + excelName + "\"");
        ExportExcelUtils.getInstance().writeExcel(dataItems, columns, excelName, res);
        logger.info("Export file \"" + excelName + "\" done!");
    }
}
