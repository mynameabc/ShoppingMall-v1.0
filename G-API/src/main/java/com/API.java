package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@ComponentScans({
		@ComponentScan(basePackages = "module"),
		@ComponentScan(basePackages = "auxiliary"),
		@ComponentScan(basePackages = "BaseFacilities")
})
@MapperScan(basePackages = "com.mapper")
public class API {

	public static void main(String[] args) {
		SpringApplication.run(API.class, args);
	}
}
