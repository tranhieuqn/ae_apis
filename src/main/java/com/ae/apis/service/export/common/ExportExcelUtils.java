package com.ae.apis.service.export.common;

import com.ae.apis.service.export.dto.ExcelField;
import com.ae.apis.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class ExportExcelUtils implements Serializable {
    private static final long serialVersionUID = -9078083261202261084L;

    private static final Logger logger = LoggerFactory.getLogger(ExportExcelUtils.class);

    public static ExportExcelUtils getInstance() {
        return ExportExcelUtilsHelper.INSTANCE;
    }

    private static class ExportExcelUtilsHelper {
        private static final ExportExcelUtils INSTANCE = new ExportExcelUtils();
    }

    public <T> void writeExcel(List<T> dataItems, List<ExcelField> columns, String excelName, HttpServletResponse res) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();

        renderExcel(workbook, dataItems, columns, excelName);

        ExcelUtils.createOutputFile(workbook, excelName, res);
        logger.info("Done!!!");
    }

    public <T> SXSSFWorkbook renderExcel(SXSSFWorkbook workbook, List<T> dataItems, List<ExcelField> columns, String excelName) {
        SXSSFSheet sheet = workbook.createSheet(StringUtils.isEmpty(excelName) ? "Sheet1" : excelName);
        sheet.setAutobreaks(true);
        sheet.setDefaultColumnWidth(30);

        try {
            Integer rowIndex = 0;

            // Write header
            writeHeader(sheet, columns, rowIndex);

            writeBody(sheet, dataItems, columns, rowIndex);

            // Write footer
            writeFooter(sheet, rowIndex);

            // Auto resize column width
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
            ExcelUtils.autoResizeColumn(sheet, numberOfColumn);
        } catch (Exception e) {
            logger.error("renderExcel createSXSSFCell error: " + e.getMessage());
            e.printStackTrace();
        }

        return workbook;
    }

    private <T> void writeBody(SXSSFSheet sheet, List<T> dataList, List<ExcelField> columns, Integer rowIndex) throws Exception {
        for (var data : dataList) {
            SXSSFRow row = sheet.createRow(rowIndex + 1);
            int columnIndex = 0;
            for (var column : columns) {
                SXSSFCell cell = row.createCell(columnIndex++);
                Object cellValue = fetchData(data, column.getFieldName());
                if (ExcelField.isNumber(column.getDataType())) {
                    Double value = NumberUtils.parseDouble(cellValue.toString());
                    if (value == null) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(value);
                    }
                    CellStyle numberStyleDefault = ExcelUtils.numberStyle(sheet);
                    cell.setCellStyle(numberStyleDefault);
                } else if (ExcelField.isDate(column.getDataType())) {
                    if (cellValue == null) {
                        cell.setCellValue("");
                    } else {
                        if (cellValue instanceof Date) {
                            cell.setCellValue((Date) cellValue);
                        } else if (cellValue instanceof OffsetDateTime) {
                            cell.setCellValue(LocalDateTime.from((OffsetDateTime) cellValue));
                        } else {
                            cell.setCellValue(cellValue.toString());
                        }
                    }
                    CellStyle dateStyleDefault = ExcelUtils.dateStyle(sheet, column.getDataFormat());
                    cell.setCellStyle(dateStyleDefault);
                } else {
                    if (cellValue == null) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(cellValue.toString());
                    }
                    CellStyle stringStyleDefault = ExcelUtils.stringStyle(sheet);
                    cell.setCellStyle(stringStyleDefault);
                }

                //TODO: implement other data type
            }
        }

    }

    public <T> Object fetchData(T src, String fieldName) throws Exception {
        Field field = src.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        return field.get(src);
    }

    // Write header
    private void writeHeader(SXSSFSheet sheet, List<ExcelField> columns, Integer rowIndex) {
        // create CellStyle
        CellStyle cellStyle = ExcelUtils.headerStyle(sheet);

        // Create row
        SXSSFRow row = sheet.createRow(rowIndex);

        int columIndex = 0;
        for (var field : columns) {
            // Create cells
            SXSSFCell cell = row.createCell(columIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(field.getHeaderName());
        }
    }

    // Write footer
    private static void writeFooter(SXSSFSheet sheet, int rowIndex) {
    }
}
