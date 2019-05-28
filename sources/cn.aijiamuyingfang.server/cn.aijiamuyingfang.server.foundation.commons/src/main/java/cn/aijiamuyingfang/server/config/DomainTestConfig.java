package cn.aijiamuyingfang.server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.aijiamuyingfang.server.domain.EnumConvertFactory;

/**
 * [描述]:
 * <p>
 * domain层相关的配置
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-07-07 01:16:13
 */
@Configuration
@EnableJpaRepositories(basePackages = { "cn.aijiamuyingfang.server" })
@EntityScan(basePackages = { "cn.aijiamuyingfang.server" })
@PropertySource("classpath:domain-test.properties")
@Profile("test")
public class DomainTestConfig extends WebMvcConfigurerAdapter {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    super.addFormatters(registry);
    registry.addConverterFactory(new EnumConvertFactory());
  }

}