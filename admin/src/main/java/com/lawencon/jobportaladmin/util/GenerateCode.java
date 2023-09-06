package com.lawencon.jobportaladmin.util;

import java.util.Random;

public class GenerateCode {
	
		public static String generateCode() {

		 final String alphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890123456789";
		 String randomizeStr = "";
		 final Random random = new Random();
		 for (int i = 0; i < 5; i++) {
			 final int indexCharacter = random.nextInt(alphaNumericStr.length());
			 randomizeStr += alphaNumericStr.charAt(indexCharacter);
		 }
		 return randomizeStr;
		 }
}
