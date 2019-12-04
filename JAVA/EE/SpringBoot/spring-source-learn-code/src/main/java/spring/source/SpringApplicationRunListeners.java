package spring.source;

import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ReflectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分析SpringBoot的run方法之前，先看一下SpringApplication中的一些事件和监听器概念。
 *
 * 首先是SpringApplicationRunListeners类和SpringApplicationRunListener类的介绍。
 *
 * SpringApplicationRunListeners内部持有SpringApplicationRunListener的集合和
 * 1个Log日志类。用于SpringApplicationRunListener监听器的批量执行。
 *
 * SpringApplicationRunListener看名字也知道用于监听SpringApplication的run方法的执行。
 *
 * 它定义了5个步骤：
 *
 * 1. started(run方法执行的时候立马执行；对应事件的类型是ApplicationStartedEvent)
 * 2. environmentPrepared(ApplicationContext创建之前并且环境信息准备好的时候调用；对应事件的类型是ApplicationEnvironmentPreparedEvent)
 * 3. contextPrepared(ApplicationContext创建好并且在source加载之前调用一次；没有具体的对应事件)
 * 4. contextLoaded(ApplicationContext创建并加载之后并在refresh之前调用；对应事件的类型是ApplicationPreparedEvent)
 * 5. finished(run方法结束之前调用；对应事件的类型是ApplicationReadyEvent或ApplicationFailedEvent)-注意最新的源码这个方法不是finished了.
 *
 * SpringApplicationRunListener目前只有一个实现类EventPublishingRunListener，
 * EventPublishingRunListener把监听的过程封装成了SpringApplicationEvent事件并让
 * 内部属性(属性名为initialMulticaster)ApplicationEventMulticaster接口的实现类
 * SimpleApplicationEventMulticaster的multicastEvent方法广播出去，此方法的第一个参数为
 * SpringApplicationEvent类(此类继承于ApplicationEvent)的子类，即把事件封装成SpringApplicationEvent广播出去.
 * 广播出去的事件对象<E extends ApplicationEvent>会被SpringApplication中的listeners属性进行处理。
 *
 * 所以说SpringApplicationRunListener和ApplicationListener之间的关系是
 * 通过ApplicationEventMulticaster广播出去的SpringApplicationEvent所联系起来的。
 */
class SpringApplicationRunListeners {

    private final Log log;

    private final List<SpringApplicationRunListener> listeners;

    SpringApplicationRunListeners(Log log,
                                  Collection<? extends SpringApplicationRunListener> listeners) {
        this.log = log;
        this.listeners = new ArrayList<>(listeners);
    }

    public void starting() {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.starting();
        }
    }

    public void environmentPrepared(ConfigurableEnvironment environment) {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.environmentPrepared(environment);
        }
    }

    public void contextPrepared(ConfigurableApplicationContext context) {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.contextPrepared(context);
        }
    }

    public void contextLoaded(ConfigurableApplicationContext context) {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.contextLoaded(context);
        }
    }

    public void started(ConfigurableApplicationContext context) {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.started(context);
        }
    }

    public void running(ConfigurableApplicationContext context) {
        for (SpringApplicationRunListener listener : this.listeners) {
            listener.running(context);
        }
    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        for (SpringApplicationRunListener listener : this.listeners) {
            callFailedListener(listener, context, exception);
        }
    }

    private void callFailedListener(SpringApplicationRunListener listener,
                                    ConfigurableApplicationContext context, Throwable exception) {
        try {
            listener.failed(context, exception);
        }
        catch (Throwable ex) {
            if (exception == null) {
                ReflectionUtils.rethrowRuntimeException(ex);
            }
            if (this.log.isDebugEnabled()) {
                this.log.error("Error handling failed", ex);
            }
            else {
                String message = ex.getMessage();
                message = (message != null) ? message : "no error message";
                this.log.warn("Error handling failed (" + message + ")");
            }
        }
    }

}
