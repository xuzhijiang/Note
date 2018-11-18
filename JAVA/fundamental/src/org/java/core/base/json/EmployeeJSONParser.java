package org.java.core.base.json;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * Java JsonParser是一个pull解析器，我们使用返回Event对象的next（）
 * 方法读取下一个元素。 javax.json.stream.JsonParser.Event是一
 * 个Enum，它使它类型安全且易于使用。 我们可以在switch case中使用来
 * 设置我们的java bean属性。
 *
 *主要的复杂性出现在我们需要编写逻辑来解析数据时，有时它会变得复杂。
 */
public class EmployeeJSONParser {

	public static final String FILE_NAME = "employee.txt";

	public static void main(String[] args) throws IOException {
		InputStream fis = new FileInputStream(FILE_NAME);

		JsonParser jsonParser = Json.createParser(fis);

		/**
		 * We can create JsonParser from JsonParserFactory also with below code
		 * JsonParserFactory factory = Json.createParserFactory(null);
		 * jsonParser = factory.createParser(fis);
		 */

		Employee emp = new Employee();
		Address address = new Address();
		String keyName = null;
		List<Long> phoneNums = new ArrayList<Long>();
		
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			switch (event) {
			case KEY_NAME:
				keyName = jsonParser.getString();
				break;
			case VALUE_STRING:
				setStringValues(emp, address, keyName, jsonParser.getString());
				break;
			case VALUE_NUMBER:
				setNumberValues(emp, address, keyName, jsonParser.getLong(), phoneNums);
				break;
			case VALUE_FALSE:
				setBooleanValues(emp, address, keyName, false);
				break;
			case VALUE_TRUE:
				setBooleanValues(emp, address, keyName, true);
				break;
			case VALUE_NULL:
				// don't set anything
				break;
			default:
				// we are not looking for other events
			}
		}
		emp.setAddress(address);
		long[] nums = new long[phoneNums.size()];
		int index = 0;
		for(Long l :phoneNums){
			nums[index++] = l;
		}
		emp.setPhoneNumbers(nums);
		
		System.out.println(emp);
		
		//close resources
		fis.close();
		jsonParser.close();
	}

	private static void setNumberValues(Employee emp, Address address,
			String keyName, long value, List<Long> phoneNums) {
		switch(keyName){
		case "zipcode":
			address.setZipcode((int)value);
			break;
		case "id":
			emp.setId((int) value);
			break;
		case "phoneNumbers":
			phoneNums.add(value);
			break;
		default:
			System.out.println("Unknown element with key="+keyName);	
		}
	}

	private static void setBooleanValues(Employee emp, Address address,
			String key, boolean value) {
		if("permanent".equals(key)){
			emp.setPermanent(value);
		}else{
			System.out.println("Unknown element with key="+key);
		}
	}

	private static void setStringValues(Employee emp, Address address,
			String key, String value) {
		switch(key){
		case "name":
			emp.setName(value);
			break;
		case "role":
			emp.setRole(value);
			break;
		case "city":
			address.setCity(value);
			break;
		case "street":
			address.setStreet(value);
			break;
		default:
			System.out.println("Unknown Key="+key);
				
		}
	}

}
