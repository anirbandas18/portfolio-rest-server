package com.teenthofabud.portfolio.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {
	
	@Bean
    public Docket freelancerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.teenthofabud.portfolio.controller"))
                .paths(regex("/freelancer.*"))
                .build().apiInfo(metaData());
    }
	
	private ApiInfo metaData() {
		Collection<VendorExtension> vendors = new ArrayList<>();
		Contact contact = new Contact("Anirban Das", "http://github.com/anirbandas18", "anirbandas18@live.com");
        ApiInfo apiInfo = new ApiInfo("Portfolio API", "REST to CRUD mapping over HTTP on various portfolio models", "1.0", "Terms of service", contact, "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", vendors);
        return apiInfo;
    }

}
