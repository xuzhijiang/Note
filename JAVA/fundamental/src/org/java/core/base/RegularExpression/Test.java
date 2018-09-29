package org.java.core.base.RegularExpression;

public class Test {
	
	private static final String reg = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\:(\\d{1,5})";
	
	public static void main(String[] args) {
		String epg = "http://10.206.253.40:8080/iptvepg/function/index.jsp";
		String template = "";
		String[] split = epg.split(reg);
		if(split.length > 1){
			template = split[1];
		}
		System.out.println("EPG template: " + template);
	}
	
}
