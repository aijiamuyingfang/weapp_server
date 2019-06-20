package cn.aijiamuyingfang.server.monitor.properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

public class CustomEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    event.getEnvironment().getPropertySources().addLast(new RandomServerPortPropertySource());
  }
}