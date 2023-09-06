package com.lawencon.config;

public class JwtConfig {

	private static final ThreadLocal<String> JWT = new ThreadLocal<>();

	public static String get() {
		final String jwtValue = JWT.get();
		return jwtValue;
	}
	
	public static void set(String factory) {
		final String jwtNew = factory;
		JWT.set(jwtNew);
	}

}
