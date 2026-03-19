package com.javaweb.utils;

public class NumberUtils {
	public static boolean isNumber(String s) {
		try {
			Long number = Long.parseLong(s);
		}catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
