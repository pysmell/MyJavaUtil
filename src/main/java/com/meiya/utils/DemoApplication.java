package com.meiya.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
//	用同一名字调用LoggerFactory.getLogger 方法所得到的永远都是同一个logger对象的引用。
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		System.out.println(logger.hashCode());
		logTest();
		SpringApplication.run(DemoApplication.class, args);

	}
	public static void logTest() {
		logger.debug("日志输出测试Debug");
		/*logger.trace("日志输出测试Trace");*/
		logger.info("日志输出测试Info");
	}
}
