Spring AOP Method Profiling(方法分析)

此示例展示给你 ”如何配置Spring AOP method profiling“。 
我们可以在任何服务（或其他）类中使用Spring AOP和任何方法，
而无需在任何服务类中编写任何一行性能分析代码(profiling code)。 
面向方面编程（AOP）允许我们将（通常是重复的和样板）分析代码与服务代码分开。


我们只在一个单独的类（SimpleProfiler.java）中编写我们的探查器代码(profiler code)一次，
这就是全部，其余的只是spring.xml中的AOP配置.

因此我们可以对以下内容 进行方法分析(profiler code):

1. 分析任何（服务）类，profiling any (service) classes,
2. 没有触及（服务）类的代码，without touching (service) classes’ code,
3. 通过Spring-AOP方法。through Spring-AOP approach.




