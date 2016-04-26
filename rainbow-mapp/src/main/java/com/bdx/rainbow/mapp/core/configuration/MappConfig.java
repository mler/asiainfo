package com.bdx.rainbow.mapp.core.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bdx.rainbow.mapp.core.filter.FilterChain;
import com.bdx.rainbow.mapp.core.filter.IFilter;
import com.bdx.rainbow.mapp.core.filter.IpCheckFilter;
import com.bdx.rainbow.mapp.core.filter.RightCheckFilter;
import com.bdx.rainbow.mapp.core.filter.UserCheckFilter;
import com.bdx.rainbow.mapp.core.filter.ValidatePackageFilter;

/*
 * @see http://mybatis.github.io/spring/mappers.html
 */
@Configuration
public class MappConfig {

	private static Logger log = LoggerFactory.getLogger(MappConfig.class);

	@Autowired
	private MappSettings mappSettings;
	

	@PostConstruct
	public void postConstruct() {
		log.info("mapp.settings={%s}", mappSettings);
	}
}
