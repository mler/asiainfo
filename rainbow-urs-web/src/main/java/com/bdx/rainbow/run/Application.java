package com.bdx.rainbow.run;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.bdx.rainbow.mapp.core.configuration.MappSettings;
import com.bdx.rainbow.urs.dubbo.IDubDicService;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.util.SessionInterceptor;

@Configuration
@ComponentScan({"com.bdx.rainbow"})
@EnableAutoConfiguration
@EnableConfigurationProperties({MappSettings.class})
@ImportResource("classpath:applicationContext.xml")
public class Application extends WebMvcConfigurerAdapter {
    public static void main(String[] args) throws Exception {
        final ApplicationContext applicationContext =  SpringApplication.run(Application.class, args);
//        CachedDict.setApplicationContext(applicationContext);
        IDubDicService service = applicationContext.getBean("dubDicService", IDubDicService.class);
        
        List<ParamDetail> paramDetail =  service.findCacheByKey("CYJJXZ");
        
        if (paramDetail != null && paramDetail.size() > 0) {
            for (ParamDetail p : paramDetail) {
            	System.out.println(p.getpDesc()+":"+p.getpValue());
            }

        } else {
            throw new Exception("没有找到对应的参数列表:+[]");
        }
    }
    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
    	// ServletName默认值为首字母小写，即myServlet
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/sys/**");
        super.addInterceptors(registry);
    }
}
