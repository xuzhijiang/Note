package org.java.core.base.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostCommonlyUsedRegExp {
	
	private static final String reg = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

	@Test
	public void resolveValidIp() {
		String epg = "10.206.253.40";
		Pattern pattern = Pattern.compile(reg);
		Matcher m = pattern.matcher(epg);
		System.out.println(m.find());
		System.out.println(m.group());
	}
	
}
