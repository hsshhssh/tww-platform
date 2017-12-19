package com.xqh.tww;

import com.xqh.tww.utils.common.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = {"com.xqh.tww.tkmybatis.mapper"})
@EnableScheduling
@EnableSwagger2
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}



	@Bean
	public DozerBeanMapper dozerBean() {
		List<String> mappingFiles = Arrays.asList(
				"dozer/dozer-mapping.xml"
		);

		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.setMappingFiles(mappingFiles);
		return dozerBean;
	}

	@Bean
	public DozerUtils dozerUtils() {
		return new DozerUtils();
	}

	@Bean
	public Docket createRestApi() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("讨娃娃平台接口文档")
				.contact("hssh")
				.version("1.0")
				.build();

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.xqh.tww.controller.impl"))
				.paths(PathSelectors.any())
				.build();
	}
}
