package cn.aijiamuyingfang.server.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("监控中心swagger接口文档")
        .apiInfo(new ApiInfoBuilder().title("监控中心swagger接口文档")
            .contact(new Contact("ShiWei", "", "shiweideyouxiang@sina.cn")).version("1.0").build())
        .select().paths(PathSelectors.any()).build();
  }
}