package com.didispace;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 我们可以通过运行单元测试，
 * 然后看my.log文件中输出的日志内容。通过修改默认的application-dev.properties配置
 * 的日志级别为INFO，
 *
 * 再运行单元测试的DEBUG内容是否被输出到了my.log中验证参数是否被正确引用了。
 *
 * 对于不同环境的使用人员也不需要改变代码或打包文件，
 * 只需要通过执行命令中参加参数即可，比如我想采用生产环境的级别，那么我可以这样运行应用：
 *
 * java -jar xxx.jar --spring.profiles.active=prod
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {

	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 日志级别 — ERROR, WARN, INFO, DEBUG or TRACE
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		logger.info("输出info");

		//如果日志级别是INFO，这行就不会被写入my.log
		logger.debug("输出debug");
		// 如果是INFO级别，error会写入my.log
		logger.error("输出error");
	}

}
