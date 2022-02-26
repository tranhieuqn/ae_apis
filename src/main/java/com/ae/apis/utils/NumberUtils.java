package com.ae.apis.utils;

import org.apache.commons.lang3.StringUtils;

public class NumberUtils {

	public static Double parseDouble(String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				return Double.parseDouble(str.trim());
			} catch (Exception nfe) {
				nfe.printStackTrace();
			}
		}

		return null;
	}
}
