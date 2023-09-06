package com.lawencon.jobportalcandidate.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static LocalDateTime parseStringToLocalDateTime(String date) {
		final LocalDateTime dateInput = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
		return dateInput;
	}
	
	public static LocalDate parseStringToLocalDate(String date) {
		final LocalDate dateInput = LocalDate.parse(date);
		return dateInput;
	}
	
	public static String localDateToString(LocalDate date) {
		LocalDate localDate = date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		String formattedString = localDate.format(formatter);
		
		return formattedString;
	}
	
	public static String localDateTimeStampToString(LocalDateTime date) {
		LocalDateTime localDateTime = date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss a");
		String formattedString = localDateTime.format(formatter);
		
		return formattedString;
	}
	

}
