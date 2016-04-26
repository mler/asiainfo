package com.bdx.rainbow.base.container;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;


@Configuration
public class RainbowContainerCustomizer {      
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
      return new EmbeddedServletContainerCustomizer() {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
          container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
         container.addErrorPages(new ErrorPage(com.bdx.rainbow.common.exception.SystemException.class,"/sys_error.html"));
          container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html"));
        }
      };
    }
  
}  