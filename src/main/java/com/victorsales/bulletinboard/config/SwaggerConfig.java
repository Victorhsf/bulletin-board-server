package com.victorsales.bulletinboard.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		List<ResponseMessage> list = new java.util.ArrayList<>();
        list.add(new ResponseMessageBuilder().code(401).message("Access denied!").build());
        list.add(new ResponseMessageBuilder().code(403).message("Forbbiden!").build());
        list.add(new ResponseMessageBuilder().code(412).message("Precondition Failed!").build());
        
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.victorsales.bulletinboard"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
				.apiInfo(getApiInfo())
				.globalResponseMessage(RequestMethod.POST, list)
				.globalResponseMessage(RequestMethod.GET, list)
				.globalResponseMessage(RequestMethod.PUT, list)
				.globalResponseMessage(RequestMethod.PATCH, list)
				.globalResponseMessage(RequestMethod.DELETE, list);
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Bulletin-board", "Documentation of api to bulletin-board CRUD", "1.0", "",
				new Contact("Victor Sales", "https://github.com/victorhsf",
						"vhfonseca@gmail.com"),
				"", "",
				Collections.emptyList());
	}
}
