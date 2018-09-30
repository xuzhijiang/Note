package org.java.core.advanced.SystemClass;

import java.util.Map;
import java.util.Set;

public class JavaSystemEnvVariables {
	public static void main(String[] args) {
		//, the returned Map is unmodifiable
		Map<String, String> envMap = System.getenv();
		Set<String> keySet = envMap.keySet();
		
		for(String key: keySet) {
			System.out.println("Key: " + key + ", value: " + envMap.get(key));
		}
		
		//Get Specific environment variable
		String pathValue = System.getenv("PATH");
		System.out.println("$PATH="+pathValue);
	}
}
