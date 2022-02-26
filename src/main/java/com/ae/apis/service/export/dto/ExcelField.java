package com.ae.apis.service.export.dto;


import lombok.Data;

@Data
public class ExcelField {
	private String headerName;
	private String fieldName;
	private String dataFormat;
	private DataType dataType;

	public enum DataType {
		STRING, DATE, INTEGER, DOUBLE, LONG, ARRAY, PICTURE
	}
	
	public static boolean isNumber(DataType dataType){
		return  dataType == ExcelField.DataType.INTEGER || dataType == ExcelField.DataType.DOUBLE || dataType == ExcelField.DataType.LONG;
	}

	public static boolean isString(DataType dataType){
		return  dataType == DataType.STRING;
	}

	public static boolean isDate(DataType dataType){
		return  dataType == DataType.DATE;
	}
}
