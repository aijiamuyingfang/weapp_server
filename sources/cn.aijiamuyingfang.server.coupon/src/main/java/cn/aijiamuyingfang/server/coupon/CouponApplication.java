package cn.aijiamuyingfang.server.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * [描述]:
 * <p>
 * Coupon模块的启动程序
 * </p>
 * 
 * @version 1.0.0
 * @author ShiWei
 * @email shiweideyouxiang@sina.cn
 * @date 2018-06-28 19:06:46
 */
@SpringBootApplication(scanBasePackages = { "cn.aijiamuyingfang.server" })
@EnableJpaRepositories(basePackages = { "cn.aijiamuyingfang.server" })
@EntityScan(basePackages = { "cn.aijiamuyingfang.server" })
public class CouponApplication {

  public static void main(String[] args) {
    SpringApplication.run(CouponApplication.class, args);
  }
}
