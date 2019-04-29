package com.java.algorithm.search.StringSearch;

import java.io.FileReader;
import com.google.gson.Gson;

public class StringSearch {
	
	public static int search(String source, String target) {
		// DO NOT use JDK String.indexOf();
		char[] src = source.toCharArray();
		char[] sub = target.toCharArray();
		return source.indexOf(target);
	}

	public static void main(String[] args) {
		try {
			ClassLoader classLoader = StringSearch.class.getClassLoader();
			String filePath = classLoader.getResource("string-search/input.json").getFile();
			System.out.println("filePath: " + filePath);
			StringSearchInput[] inputs = new Gson().fromJson(new FileReader(filePath), 
					StringSearchInput[].class);
			for (StringSearchInput input : inputs) {
				int r = search(input.source, input.target);
				System.out.println("search(\"" + input.source + "\", \"" + input.target + "\") => " + r);
				if (input.result != r) {
					throw new Exception("failed. expected = " + input.result + ", actual = " + r);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class StringSearchInput {
	String source;
	String target;
	int result;
}