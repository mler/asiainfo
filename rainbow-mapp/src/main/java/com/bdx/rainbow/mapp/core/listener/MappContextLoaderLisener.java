package com.bdx.rainbow.mapp.core.listener;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdx.rainbow.mapp.core.context.MappBeanFactory;
import com.bdx.rainbow.mapp.core.context.MappContextLoader;
import com.bdx.rainbow.mapp.core.context.SpringBeanFactory;

public class MappContextLoaderLisener extends ContextLoader implements ServletContextListener {

	private Logger logger = LoggerFactory.getLogger(MappContextLoaderLisener.class);
	
	private MappContextLoader contextLoader;
	
	private String creator = "spring";
	
	private String[] packages ={"com.bdx.rainbow"};
	
	public MappContextLoaderLisener()
	{
		
	}
	
	public MappContextLoaderLisener(WebApplicationContext context) {
		super(context);
	}


	/**
	 * Initialize the root web application context.
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		try{
			/**创建mapp上下文加载器**/
			contextLoader = MappContextLoader.instance();
			
			/**
			 * 
			 * 如果creator=spring，则先初始化spring上下文，首先初始化BeanFactory，
			 * 如果spring方式则使用SpringBeanFactory，
			 * 否则通过mapp自己的MappBeanFacory，
			 * 然后初始化mapp上下文
			 */
			if(MappContextLoader.CREATE_TYPE_SPRING.equals(creator))
			{
//				initWebApplicationContext(event.getServletContext());
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
				SpringBeanFactory springBeanFactory = SpringBeanFactory.instance();
				springBeanFactory.setApplicationContext(context);
				contextLoader.setBeanFactory(springBeanFactory);
			}
			else
				contextLoader.setBeanFactory(MappBeanFactory.instance());
			
			
			
			contextLoader.initializeMappApplicationContext(packages,creator);
			
			logger.info("contextLoader initialized.............");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("MappApplicationContext init failed,cause:"+e.getMessage());
		}
	}


	/**
	 * Close the root web application context.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		closeWebApplicationContext(event.getServletContext());
		Enumeration<String> attrNames = event.getServletContext().getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = attrNames.nextElement();
			if (attrName.startsWith("org.springframework.")) {
				Object attrValue = event.getServletContext().getAttribute(attrName);
				if (attrValue instanceof DisposableBean) {
					try {
						((DisposableBean) attrValue).destroy();
					}
					catch (Throwable ex) {
						logger.error("Couldn't invoke destroy method of attribute with name '" + attrName + "'", ex);
					}
				}
			}
		}
	}

}
