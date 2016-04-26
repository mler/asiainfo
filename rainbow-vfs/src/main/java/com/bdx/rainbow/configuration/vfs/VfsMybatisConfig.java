package com.bdx.rainbow.configuration.vfs;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ClassUtils;

import com.alibaba.druid.pool.DruidDataSource;


/*
 * @see http://mybatis.github.io/spring/mappers.html
 */
@Configuration
@MapperScan(basePackages = {"com.bdx.rainbow.mapper.vfs"},sqlSessionFactoryRef="sqlSessionFactoryVfs")
public class VfsMybatisConfig {

	private static Logger log = LoggerFactory.getLogger(VfsMybatisConfig.class);

	@Autowired
	private VfsConnectionSettings vfsConnectionSettings;


	@Bean(name="dataSourceVfs")
	public DataSource dataSource() {
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(vfsConnectionSettings.getDriver());
		ds.setUsername(vfsConnectionSettings.getUsername());
		ds.setPassword(vfsConnectionSettings.getPassword());
		ds.setUrl(vfsConnectionSettings.getUrl());
		ds.setMaxActive(vfsConnectionSettings.getMaxActive());
		ds.setValidationQuery(vfsConnectionSettings.getValidationQuery());
		ds.setTestOnBorrow(vfsConnectionSettings.getTestOnBorrow());
		ds.setTestOnReturn(vfsConnectionSettings.getTestOnReturn());
		ds.setTestWhileIdle(vfsConnectionSettings.getTestWhileIdle());
		ds.setTimeBetweenEvictionRunsMillis(vfsConnectionSettings.getTimeBetweenEvictionRunsMillis());
		ds.setMinEvictableIdleTimeMillis(vfsConnectionSettings.getMinEvictableIdleTimeMillis());
		return ds;
	}


	public Resource[] getResource( String basePackage, String pattern ) throws IOException {
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage)) + "/" + pattern;
		System.out.println("> packageSearchPath : "+packageSearchPath);
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
		for(Resource r : resources)
		{
			log.debug(r.getFilename());
		}
		return resources;
	}


	@Bean(name="sqlSessionFactoryVfs")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		log.debug("> sqlSessionFactoryVfs");
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setConfigLocation(new ClassPathResource("vfs-mybatis-config.xml"));
		sqlSessionFactory.setFailFast(true);
		sqlSessionFactory.setMapperLocations(getResource("mapper/vfs", "**/*.xml"));
		sqlSessionFactory.setTypeHandlersPackage("com.bdx.rainbow.configuration.typehandler");
		return sqlSessionFactory.getObject();
	}


	@Bean(name="transactionManagerVfs")
	public DataSourceTransactionManager transactionManager() {
		log.debug("> transactionManagerVfs");
		return new DataSourceTransactionManager(dataSource());
	}

	@PostConstruct
	public void postConstruct() {
		log.info("vfsConnection.settings={}", vfsConnectionSettings);
	}
}
