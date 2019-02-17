package org.java.core.base.logging;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 *  我们可以向java logger中添加多个handlers，
 *  每当我们记录任何消息时，每个处理程序(handler）都会相应地处理它
 *  Java Logging API提供了两个默认处理程序(handler）。
 * 
 * ConsoleHandler： 此处理程序将所有日志消息写入控制台
 * FileHandler：此处理程序以XML格式将所有日志记录消息写入文件。
 *
 *	我们也可以创建自己的自定义处理程序来执行特定任务。 
 * 	要创建我们自己的Handler类，我们需要扩展java.util.logging.Handler类或其任何子类，
 * 	如StreamHandler，SocketHandler等。
 */
public class MyHandler extends StreamHandler{
	
	@Override
	public void publish(LogRecord record){
		//add own logic to publish
		super.publish(record);
	}
	
	@Override
	public void flush(){
		super.flush();
	}
	
	@Override
	public void close(){
		super.close();
	}
}
