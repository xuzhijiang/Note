package org.java.core.base.logging;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MyFilter implements Filter{

	/**
	 * true if the log record should be published.
	 */
	@Override
	public boolean isLoggable(LogRecord record) {
		// don't log CONFIG logs in file
		// 如果级别为Level.CONFIG,那么就不发布
		if (record.getLevel() == Level.CONFIG) return false;
		return true;
	}

}
