package org.java.core.base.logging;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * 我们可以向java logger中添加多个handlers，
 * 每当我们记录任何消息时，每个处理程序（handler）都会相应地处理它
 *  Java Logging API提供了两个默认处理程序（handler）。
 * 
 * @author a
 *
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
