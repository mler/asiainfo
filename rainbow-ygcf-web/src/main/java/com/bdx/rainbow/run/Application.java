package com.bdx.rainbow.run;

import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.interceptor.SessionInterceptor;
import com.bdx.rainbow.mapp.core.configuration.MappSettings;
import com.bdx.rainbow.mapp.core.listener.MappContextLoaderLisener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"com.bdx.rainbow"})
@EnableAutoConfiguration
@EnableConfigurationProperties({MappSettings.class,VfsSetting.class})
@ImportResource("classpath:applicationContext.xml")
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
                .excludePathPatterns("")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/addon/**")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/fonts/**")
                .excludePathPatterns("img/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/rest/")
                .excludePathPatterns("/rest")
                .excludePathPatterns("/transFile")
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    public ServletListenerRegistrationBean<MappContextLoaderLisener> servletRegistrationBean() {
        return new ServletListenerRegistrationBean<>(new MappContextLoaderLisener());
    }
}
