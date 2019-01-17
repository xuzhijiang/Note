Java Logging API是在Java 1.4中引入的

我们将学习Java Logger的基本功能。我们还将研究不同日志记录级别的Java Logger示例,
Logging Handlers, Formatters, Filters, Log Manager and logging configurations.

java.util.logging.Level defines the different levels of java logging. 
There are seven levels of logging in java.
一共7中Java日志级别:

SEVERE (highest)
WARNING
INFO
CONFIG
FINE
FINER
FINEST

There are two other logging levels, OFF that will turn off all logging 
and ALL that will log all the messages.
还有另外两个日志记录级别，OFF将关闭所有日志记录和ALL将记录所有消息。

将为所有等于或大于记录器级别的级别生成日志。
 例如，如果将记录器级别设置为INFO，则将为INFO，WARNING和SEVERE日志记录消息生成日志。
 
