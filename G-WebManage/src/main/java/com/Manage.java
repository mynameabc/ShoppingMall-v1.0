package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@ComponentScans({
		@ComponentScan(basePackages = "module"),
		@ComponentScan(basePackages = "auxiliary"),
		@ComponentScan(basePackages = "BaseFacilities")
})
@MapperScan({"com.mapper"})
public class Manage extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Manage.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(Manage.class);
	}
}
