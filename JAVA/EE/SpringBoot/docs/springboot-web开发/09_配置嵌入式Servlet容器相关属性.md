# 嵌入式Servlet容器相关属性配置

```java
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
public class ServerProperties {
	private Integer port; // server.port=8080
    // 这里的Servlet/Tomcat/Jetty/Undertow都是ServerProperties的内部类
	private final Servlet servlet = new Servlet();
	private final Tomcat tomcat = new Tomcat(); // server.tomcat.xx
	private final Jetty jetty = new Jetty();
	private final Undertow undertow = new Undertow();
	
    /**
	 * Tomcat properties.
	 */
	public static class Tomcat {
		private Charset uriEncoding = StandardCharsets.UTF_8; // server.tomcat.uri-encoding(uriEncoding和uri-encoding松散匹配)
    }
}
```