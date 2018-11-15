package org.java.core.base.RegularExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostCommonlyUsedRegExp {
	
	private static final String reg = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
	
	public static void main(String[] args) {
		String epg = "10.206.253.40";
		Pattern pattern = Pattern.compile(reg);
		Matcher m = pattern.matcher(epg);
		System.out.println(m.find());
	}
	
}
