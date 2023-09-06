package com.lawencon.jobportalcandidate.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

	public static BigDecimal parseToBigDecimal(String number) {
		return BigDecimal.valueOf(Long.valueOf(number));
	}
	
	
}
