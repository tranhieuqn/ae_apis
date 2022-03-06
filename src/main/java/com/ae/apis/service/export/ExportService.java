package com.ae.apis.service.export;

import com.ae.apis.service.export.dto.ExcelField;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExportService {
    <T> void exportFile(List<T> dataItems, List<ExcelField> columns, String excelName, HttpServletResponse res);
}
