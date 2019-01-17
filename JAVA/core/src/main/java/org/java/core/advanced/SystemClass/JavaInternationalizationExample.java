package org.java.core.advanced.SystemClass;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 为了使您的应用程序支持不同的语言环境，我们需要创建特定于语言环境的属性文件。
 * ,文件名遵循包名称的模式，包括语言代码和国家/地区代码，
 * 例如ApplicationMessages_en_US.properties。
 * 一旦特定语言环境的属性文件准备就绪，您需要做的就是使用正确的Locale初始化资源包。
 *  Java提供了两个用于此目的的java.util.ResourceBundle和java.util.Locale类。
 *   ResourceBundle读取特定于语言环境的属性文件，您可以获取任何键的特定于语言环境的值。
 *   这对于使您的Web应用程序文本特定于区域设置非常有用，您可以从HTTP请求获取区域设置信息并生
 *   成包含该区域设置资源包文件的动态页面。
 * there are some locales already defined but 
 * we can always create new locale by passing language code 
 * and country code to it’s constructor.
 */
public class JavaInternationalizationExample {
	public static void main(String[] args) {
		try{
			// default locale
			ResourceBundle bundle = ResourceBundle.getBundle("ApplicationMessages");
			// Get ResourceBundle with Locale that are already defined
			ResourceBundle bundleFR = ResourceBundle.getBundle("ApplicationMessages", Locale.FRANCE);
			// Get resource bundle when Locale needs to be created
			ResourceBundle bundleSWE = ResourceBundle.getBundle("ApplicationMessages", new Locale("sv", "SE"));

			// Note: 配置文件要放到src下面，不要放到和当前java文件同级目录
			
			// lets print some messages
			printMessages(bundle);
			printMessages(bundleFR);
			printMessages(bundleSWE);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void printMessages(ResourceBundle bundle) {
		System.out.println(bundle.getString("CountryName"));
		System.out.println(bundle.getString("CurrencyCode"));
	}
}
