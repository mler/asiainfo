package com.bdx.rainbow.configuration.vfs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ VfsSetting.class ,VfsConnectionSettings.class, OpenOfficeSetting.class})
public class VfsConfigurations {

}
