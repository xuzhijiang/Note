package springboot源码.WebServer;

import org.springframework.boot.web.server.WebServerException;

// 目前有5个实现类.分别是JettyWebServer、TomcatWebServer和UndertowWebServer，NettyWebServer,
// UndertowServeltWebServer, 分别对应Jetty、Tomcat和Undertow,NettyServlet容器。
public interface WebServer {

    // 启动内置的Servlet容器，如果容器已经启动，则不影响
    void start() throws WebServerException;

    /**
     * 关闭内置的Servlet容器，如果容器已经关闭，则不影响
     */
    void stop() throws WebServerException;

    /**
     * 内置的Servlet容器监听的端口
     */
    int getPort();
}
