package com.lawencon.jobportalcandidate.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MoneyUtil {

	public static String parseToRupiah(BigDecimal money) {
		DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return "Rp " + kursIndonesia.format(money);
	}
	
	
}
