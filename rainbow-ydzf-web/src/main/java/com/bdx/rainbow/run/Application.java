package com.bdx.rainbow.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.bdx.rainbow.base.interceptor.UserSecurityInterceptor;
import com.bdx.rainbow.core.common.YDZFVfsSetting;
import com.bdx.rainbow.mapp.core.configuration.MappSettings;

@Configuration
@ComponentScan({ "com.bdx.rainbow" })
@EnableAutoConfiguration
@EnableConfigurationProperties({ MappSettings.class, YDZFVfsSetting.class })
@ImportResource("classpath:applicationContext.xml")
public class Application extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		// SpringApplication springApplication= new
		// SpringApplication(Application.class);
		// List<ApplicationListener> list = new
		// ArrayList<ApplicationListener>();
		// springApplication.addListeners(list);
		SpringApplication.run(Application.class, args);
	}

	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"classpath:/static/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns(
				"/**");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/login");
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
	}
}
