package spring.source.servlet;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.web.servlet.DynamicRegistrationBean;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * ServletRegistrationBean 继承了DynamicRegistrationBean，又继承了RegistrationBean
 * ，里面的onStartup方法会调用到ServletRegistrationBean的addRegistration,这个onStartup方法里面
 *         // 把servlet添加到Servlet容器中，Servlet容器启动的时候会加载这个Servlet
 *   // 进行Servlet的一些配置，比如urlMapping，loadOnStartup等
 *
 *   类似ServletRegistrationBean的还有ServletListenerRegistrationBean和FilterRegistrationBean，
 *   它们都是Servlet初始化器，分别都是在Servlet容器中添加Listener和Filter。
 */
public class ServletRegistrationBean<T extends Servlet>
        extends DynamicRegistrationBean<ServletRegistration.Dynamic> {

    @Override
    protected ServletRegistration.Dynamic addRegistration(String description,
                                                          ServletContext servletContext) {
        String name = getServletName();
        logger.info("Servlet " + name + " mapped to " + this.urlMappings);
        // 把servlet添加到Servlet容器中，Servlet容器启动的时候会加载这个Servlet
        return servletContext.addServlet(name, this.servlet);
    }


    private static final Log logger = LogFactory.getLog(ServletRegistrationBean.class);

    private static final String[] DEFAULT_MAPPINGS = { "/*" };

    private T servlet;

    private Set<String> urlMappings = new LinkedHashSet<>();

    private boolean alwaysMapUrl = true;

    private int loadOnStartup = -1;

    private MultipartConfigElement multipartConfig;

    /**
     * Create a new {@link ServletRegistrationBean} instance.
     */
    public ServletRegistrationBean() {
    }

    /**
     * Create a new {@link ServletRegistrationBean} instance with the specified
     * {@link Servlet} and URL mappings.
     * @param servlet the servlet being mapped
     * @param urlMappings the URLs being mapped
     */
    public ServletRegistrationBean(T servlet, String... urlMappings) {
        this(servlet, true, urlMappings);
    }

    /**
     * Create a new {@link ServletRegistrationBean} instance with the specified
     * {@link Servlet} and URL mappings.
     * @param servlet the servlet being mapped
     * @param alwaysMapUrl if omitted URL mappings should be replaced with '/*'
     * @param urlMappings the URLs being mapped
     */
    public ServletRegistrationBean(T servlet, boolean alwaysMapUrl,
                                   String... urlMappings) {
        Assert.notNull(servlet, "Servlet must not be null");
        Assert.notNull(urlMappings, "UrlMappings must not be null");
        this.servlet = servlet;
        this.alwaysMapUrl = alwaysMapUrl;
        this.urlMappings.addAll(Arrays.asList(urlMappings));
    }

    /**
     * Sets the servlet to be registered.
     * @param servlet the servlet
     */
    public void setServlet(T servlet) {
        Assert.notNull(servlet, "Servlet must not be null");
        this.servlet = servlet;
    }

    /**
     * Return the servlet being registered.
     * @return the servlet
     * @since 2.0.4
     */
    public T getServlet() {
        return this.servlet;
    }

    /**
     * Set the URL mappings for the servlet. If not specified the mapping will default to
     * '/'. This will replace any previously specified mappings.
     * @param urlMappings the mappings to set
     * @see #addUrlMappings(String...)
     */
    public void setUrlMappings(Collection<String> urlMappings) {
        Assert.notNull(urlMappings, "UrlMappings must not be null");
        this.urlMappings = new LinkedHashSet<>(urlMappings);
    }

    /**
     * Return a mutable collection of the URL mappings, as defined in the Servlet
     * specification, for the servlet.
     * @return the urlMappings
     */
    public Collection<String> getUrlMappings() {
        return this.urlMappings;
    }

    /**
     * Add URL mappings, as defined in the Servlet specification, for the servlet.
     * @param urlMappings the mappings to add
     * @see #setUrlMappings(Collection)
     */
    public void addUrlMappings(String... urlMappings) {
        Assert.notNull(urlMappings, "UrlMappings must not be null");
        this.urlMappings.addAll(Arrays.asList(urlMappings));
    }

    /**
     * Sets the {@code loadOnStartup} priority. See
     * {@link ServletRegistration.Dynamic#setLoadOnStartup} for details.
     * @param loadOnStartup if load on startup is enabled
     */
    public void setLoadOnStartup(int loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /**
     * Set the {@link MultipartConfigElement multi-part configuration}.
     * @param multipartConfig the multi-part configuration to set or {@code null}
     */
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {
        this.multipartConfig = multipartConfig;
    }

    /**
     * Returns the {@link MultipartConfigElement multi-part configuration} to be applied
     * or {@code null}.
     * @return the multipart config
     */
    public MultipartConfigElement getMultipartConfig() {
        return this.multipartConfig;
    }

    @Override
    protected String getDescription() {
        Assert.notNull(this.servlet, "Servlet must not be null");
        return "servlet " + getServletName();
    }


    /**
     * Configure registration settings. Subclasses can override this method to perform
     * additional configuration if required.
     * @param registration the registration
     */
    @Override
    protected void configure(ServletRegistration.Dynamic registration) {
        super.configure(registration);
        String[] urlMapping = StringUtils.toStringArray(this.urlMappings);
        if (urlMapping.length == 0 && this.alwaysMapUrl) {
            urlMapping = DEFAULT_MAPPINGS;
        }
        if (!ObjectUtils.isEmpty(urlMapping)) {
            registration.addMapping(urlMapping);
        }
        registration.setLoadOnStartup(this.loadOnStartup);
        if (this.multipartConfig != null) {
            registration.setMultipartConfig(this.multipartConfig);
        }
    }

    /**
     * Returns the servlet name that will be registered.
     * @return the servlet name
     */
    public String getServletName() {
        return getOrDeduceName(this.servlet);
    }

}

