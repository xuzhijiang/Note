package org.java.core.base.file.ReadCSVFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * 如果您查看Scanner类构造函数，您会注意到它接受File或InputStream作为输入。 
 * 它还包含实用程序方法hasNextLine（）和nextLine（），
 * 我们可以使用它来仅使用扫描程序解析CSV文件。
 * 
 * 如果您只需要解析一个简单的CSV文件，扫描仪类就是一个不错的选择。
 */
public class CSVScannerExample {
	
	static String fileName = "C:\\Users\\a\\Desktop\\test\\employees.csv";

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(new File(fileName));
		Scanner dataScanner = null;
		int index = 0;
		List<Employee> empList = new ArrayList<>();
		
		while (scanner.hasNextLine()) {
			dataScanner = new Scanner(scanner.nextLine());
			dataScanner.useDelimiter(",");
			Employee emp = new Employee();

			while (dataScanner.hasNext()) {
				String data = dataScanner.next();
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

		scanner.close();

		System.out.println(empList);

	}

}
