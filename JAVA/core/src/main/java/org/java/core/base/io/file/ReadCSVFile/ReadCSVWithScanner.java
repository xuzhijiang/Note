package org.java.core.base.io.file.ReadCSVFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 我们可以使用Java Scanner Class在java中读取CSV文件。
 * 
 * 我们可以使用Java Scanner类来读取CSV文件并转换为java bean的集合。
 * 
 * 
 * 请注意，我们将Scanner分隔符设置为逗号（，）。 如果输入文件使用了一些其他分隔符，
 * 例如pipe（|）或hash（＃），那么我们需要做的就是更改上面程序中的分隔符.
 * 
 */
public class ReadCSVWithScanner {
	
	static String fileName = "C:\\Users\\a\\Desktop\\test\\employees.csv";

	public static void main(String[] args) throws IOException {
		// open file input stream
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		// read file line by line
		String line = null;
		Scanner scanner = null;
		int index = 0;
		List<Employee> empList = new ArrayList<>();

		while ((line = reader.readLine()) != null) {
			Employee emp = new Employee();
			scanner = new Scanner(line);
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				String data = scanner.next();
				if (index == 0)
					emp.setId(Integer.parseInt(data));
				else if (index == 1)
					emp.setName(data);
				else if (index == 2)
					emp.setRole(data);
				else if (index == 3)
					emp.setSalary(data);
				else
					System.out.println("invalid data::" + data);
				index++;
			}
			index = 0;
			empList.add(emp);
		}
		
		//close reader
		reader.close();
		
		System.out.println(empList);
		
	}

}
