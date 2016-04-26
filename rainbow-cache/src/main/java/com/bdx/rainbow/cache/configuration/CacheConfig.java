package com.bdx.rainbow.cache.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


/**
 * 缓存配置
 * @author mler
 *<!-- 启用缓存注解 --> <cache:annotation-driven cache-manager="cacheManager" />
 */
//@Configuration
//@EnableCaching
public class CacheConfig 
{
	private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);  
	
	/*
	   * ehcache 主要的管理器
	   */
	  @Bean(name = "appEhCacheCacheManager")
	  public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
	    return new EhCacheCacheManager (bean.getObject ());
	  }

	  /*
	   * 据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
	   */
	  @Bean
	  public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
	    EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
	    cacheManagerFactoryBean.setConfigLocation (new ClassPathResource ("cache/ehcache.xml"));
	    cacheManagerFactoryBean.setShared (true);
	    return cacheManagerFactoryBean;
	  }
}
