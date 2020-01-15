@ConfigurationProperties(prefix = "spring.mvc")
public class WebMvcProperties {


	/**
	 * Path pattern used for static resources.
	 */
	private String staticPathPattern = "/**";

		public String getStaticPathPattern() {
		return this.staticPathPattern;
	}

}