package com.springboot.exception.resolver;

import com.springboot.exception.config.ExceptionConfiguration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

// 我们将使用它来 “为所有其他的异常” 配置 “视图页面”，
// “所有其他的异常”指 我们未处理的异常
 /**
 * Not used in this application, but an example of how to extend the
 * <tt>SimpleMappingExceptionResolver</tt> to provide extra information in the
 * log messages it generates and in the model for the view. To use, change
 * {@link ExceptionConfiguration#createSimpleMappingExceptionResolver()} to
 * return an instance of this class instead.
 *
 * @author Paul Chapman
 */
public class ExampleSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    /**
     * Also enable logging to this classe's logger by default.
     */
    public ExampleSimpleMappingExceptionResolver() {
        // Turn logging on by default
        setWarnLogCategory(getClass().getName());
    }

    /**
     * Override the default to generate a log message with dynamic content.
     */
    @Override
    public String buildLogMessage(Exception e, HttpServletRequest req) {
        return "ExampleSimpleMappingExceptionResolver MVC exception: " + e.getLocalizedMessage();
    }

    /**
     * This method uses the older API and gets passed the handler (typically the
     * <tt>@Controller</tt>) that generated the exception.
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception exception) {

        // Call super method to get the ModelAndView
        ModelAndView mav = super.doResolveException(request, response, handler, exception);

        // Make more information available to the view - note that
        // SimpleMappingExceptionResolver adds the exception already
        mav.addObject("url", request.getRequestURL());
        mav.addObject("timestamp", new Date());
        mav.addObject("status", 500);
        return mav;
    }
}