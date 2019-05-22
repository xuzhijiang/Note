package org.java.core.base.logging;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 格式化程序用于格式化日志消息。 java logging API中有两个可用的格式化程序
 * 
 * SimpleFormatter：此格式化程序生成包含基本信息的文本消息。 
 * ConsoleHandler使用此formatter类将日志消息打印到控制台。
 * 
 * XMLFormatter：此格式化程序为日志生成XML消息，
 * FileHandler使用XMLFormatter作为默认格式化程序。
 * 
 * 我们可以通过扩展java.util.logging.Formatter类来创建我们自己的自定义Formatter类，
 * 并将其附加到任何处理程序(handler)
 *
 */
public class MyFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		return record.getThreadID() + "::" + record.getSourceClassName() + "::"
				+ record.getSourceMethodName() + "::" + new Date(record.getMillis()) + "::"
				+ record.getMessage() + "\n";
	}

	
}
