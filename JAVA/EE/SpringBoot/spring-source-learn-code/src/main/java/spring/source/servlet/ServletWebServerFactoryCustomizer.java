package spring.source.servlet;


import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.core.Ordered;

public class ServletWebServerFactoryCustomizer implements
        WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>, Ordered {

    private final ServerProperties serverProperties;

    public ServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * SpringBoot内置了一些WebServerFactoryCustomizer，
     * 比如TomcatServletWebServerFactoryCustomizer等。
     *
     * ServerProperties表示服务端的一些配置，以server为前缀，
     * 比如有server.port、server.contextPath、server.displayName等，
     */
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        // 3种容器工厂都实现了ConfigurableWebServerFactory接口（TomcatServletWebServerFactory），
        // 所以下面的这些设置相当于对"容器工厂"进行设置
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.serverProperties::getPort).to(factory::setPort);
        map.from(this.serverProperties::getAddress).to(factory::setAddress);
        map.from(this.serverProperties.getServlet()::getContextPath)
                .to(factory::setContextPath);
        // 如果配置了displayName
        map.from(this.serverProperties.getServlet()::getApplicationDisplayName)
                .to(factory::setDisplayName);
        // 如果配置了server.session.timeout，session超时时间。
        // 注意：这里的Session指的是ServerProperties的内部静态类Session
        map.from(this.serverProperties.getServlet()::getSession).to(factory::setSession);
        map.from(this.serverProperties::getSsl).to(factory::setSsl);
        map.from(this.serverProperties.getServlet()::getJsp).to(factory::setJsp);
        map.from(this.serverProperties::getCompression).to(factory::setCompression);
        map.from(this.serverProperties::getHttp2).to(factory::setHttp2);
        map.from(this.serverProperties::getServerHeader).to(factory::setServerHeader);
        // 添加initParameters,作用是基于
        // ServerProperties的contextParameters配置设置到ServletContext的init param中,
        // 见AbstractServletWebServerFactory.mergeInitializers()
        map.from(this.serverProperties.getServlet()::getContextParameters)
                .to(factory::setInitParameters);


        // TomcatServletWebServerFactory中的mergeInitializers中有调用:

        // 添加SessionConfiguringInitializer这个Servlet初始化器
        // SessionConfiguringInitializer初始化器的作用是基于ServerProperties的内部静态类Session设置
        // Servlet中session和cookie的配置

    }

}
