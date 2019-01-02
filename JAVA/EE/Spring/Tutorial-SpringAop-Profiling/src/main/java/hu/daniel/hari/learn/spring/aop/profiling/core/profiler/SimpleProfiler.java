package hu.daniel.hari.learn.spring.aop.profiling.core.profiler;


import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

// Spring AOP Profiler class

// 我们的探查器类可以简单地测量方法执行的处理时间。

// 1. 如您所见，我们的配置文件方法使用秒表(StopWatch)来测量方法执行。

// 2. 该方法从Spring-AOP接收ProceedingJoinPoint对象，
// 该对象在执行该方法之前表示此情况下的方法执行关节点( method execution joint point)。
// 该对象还有关于我们可以获得的要执行的方法的信息。

// 3. 我们负责通过调用它的proceed（）来执行该方法。

// 4. 我们也知道在执行的日志记录方法中抛出的异常，
// 但是我们将其推向透明，我们不希望处理此级别的异常，只记录它。

//（“profile”方法签名受限于将调用它的Spring AOP，
// 但方法名称可以是将在spring.xml中设置的任何其他内容。）

/**
 * Our profiler that
 * logs process time, and remark if process had exception.
 * 
 * @author Daniel Hari
 */
public class SimpleProfiler {
	
	/**
	 * Spring AOP 'around' reference method signature is bounded like this, the
	 * method name "profile" should be same as defined in spring.xml aop:around
	 * section.
	 **/
	public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(proceedingJoinPoint.toShortString());
		boolean isExceptionThrown = false;
		try {
			// execute the profiled method
			return proceedingJoinPoint.proceed();
		} catch (RuntimeException e) {
			isExceptionThrown = true;
			throw e;
		} finally {
			stopWatch.stop();
			TaskInfo taskInfo = stopWatch.getLastTaskInfo();
			// Log the method's profiling result
			String profileMessage = taskInfo.getTaskName() + ": " + taskInfo.getTimeMillis() + " ms" +
					(isExceptionThrown ? " (thrown Exception)" : "");
			System.out.println(profileMessage);
		}
	}
	
}
