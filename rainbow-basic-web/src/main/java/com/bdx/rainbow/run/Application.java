package com.bdx.rainbow.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.mapp.core.configuration.MappSettings;
import com.bdx.rainbow.mapp.core.listener.MappContextLoaderLisener;

@Configuration
@ComponentScan({"com.bdx.rainbow"})
@EnableAutoConfiguration
@EnableConfigurationProperties({MappSettings.class,VfsSetting.class})
@ImportResource("classpath:applicationContext.xml")
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	@Bean
    public ServletListenerRegistrationBean<MappContextLoaderLisener> servletRegistrationBean() {
        return new ServletListenerRegistrationBean<>(new MappContextLoaderLisener());
    }
}

