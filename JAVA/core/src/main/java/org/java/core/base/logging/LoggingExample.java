package org.java.core.base.logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingExample {
	static Logger logger = Logger.getLogger(LoggingExample.class.getName());
	
	public static void main(String[] args){
		// java.util.logging.LogManager是读取日志记录配置
		// 创建和维护logger实例的类
		//我们可以使用此类来设置我们自己的应用程序特定配置。
		// 如果我们不指定任何配置，则从JRE Home lib / logging.properties文件中读取。
		
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.setLevel(Level.FINE);
		logger.addHandler(new ConsoleHandler());
		//adding custom handler
		logger.addHandler(new MyHandler());
		
		try {
            //FileHandler file name with max size and number of log files limit
			// 2000: 写入任何一个文件的最大字节数为2000
			// 使用的文件的个数为5个
            Handler fileHandler = new FileHandler("C:\\Users\\a\\Desktop\\test\\logger.log", 2000, 5);
            fileHandler.setFormatter(new MyFormatter());
            //setting custom filter for FileHandler
            fileHandler.setFilter(new MyFilter());
            logger.addHandler(fileHandler);
            
            for(int i=0; i<1000; i++){
                //logging messages
                logger.log(Level.INFO, "Msg"+i);//注意区分控制台日志和文件日志不同格式
            }
            logger.log(Level.CONFIG, "Config data");//不会被打印
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
	}
}
