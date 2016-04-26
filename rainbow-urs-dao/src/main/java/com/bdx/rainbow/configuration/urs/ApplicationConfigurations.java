package com.bdx.rainbow.configuration.urs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ JdbcConnectionSettings.class })
public class ApplicationConfigurations {

}
