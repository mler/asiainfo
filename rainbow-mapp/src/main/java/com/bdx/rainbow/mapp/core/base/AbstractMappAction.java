/**
 * 
 */
package com.bdx.rainbow.mapp.core.base;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.mapp.core.context.ActionBeanDefinition;
import com.bdx.rainbow.mapp.core.context.BeanDefinitionFactory;
import com.bdx.rainbow.mapp.core.context.MappApplicationContext;
import com.bdx.rainbow.mapp.core.context.MappApplicationContextAware;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.core.exception.MappException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.core.model.IBody;
import com.bdx.rainbow.mapp.core.model.IHeader;
import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
import com.bdx.rainbow.mapp.core.serializer.MappSerializer;

/**
 * 抽象Action类，将各个接口统一的处理方法进行实现，doAction方法作为具体业务实现，
 * 由各个Action的具体实现类来实现。
 * @author mler
 * @2016年4月7日
 * @param <Req> 请求包体对象
 * @param <Rsp> 响应包体对象
 */
public abstract class AbstractMappAction<Req extends IBody, Rsp extends IBody> implements MappAction<Req,Rsp>,MappApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(AbstractMappAction.class);

	protected MappApplicationContext context = MappApplicationContext.instance();
	
	protected MappSerializer<IMappDatapackage> serializer;

	public void setSerializer(MappSerializer<IMappDatapackage> serializer) {
		this.serializer = serializer;
	}
	
	/**
	 * 每次请求都是初始化MappContext。即mapp上下文，
	 * @param request 请求字符串
	 * @param context 外部上下文信息
	 * @throws Exception
	 */
	private void _initMappContext(final String bizcode,final String version,final IMappDatapackage request,Map<String, Object> context) throws Exception {
		try {
			MappContext.setRequest(request);
			MappContext.setAttribute(MappContext.MAPPCONTEXT_CREATETIME, System.currentTimeMillis());
			MappContext.addContext(context);
			
			/** 请求报文和返回报文报体结构一致，生成返回报文，并且设置包头与request一致 mler 2013-05-10 **/
			IMappDatapackage response = (IMappDatapackage) request.getClass().newInstance();
			response.setHeader((IHeader)BeanUtils.cloneBean(request.getHeader()));
			ActionBeanDefinition actionBD = ((BeanDefinitionFactory)this.context.getDefaultBeanFactory()).lookupAction(bizcode, version);
			if(actionBD.getResponseClass() != IBody.class)
			response.setBody((IBody)actionBD.getResponseClass().newInstance());
			MappContext.setResponse(response);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MappContext init error {}", e);
			throw new MappException(e);
		}
	}
	
	/**
	 * 传入请求对象，并调用方法获得结果
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void initMappContext(final String bizcode,final String version,final String requestString,final Map<String, Object> context)
			throws Exception {
		IMappDatapackage requestObject = stringToRequest(requestString);
		_initMappContext(bizcode,version,requestObject, context);
	}
	
	/**
	 * 由于在cglib无法拦截内部方法调用，因此在此包一层，将次方法开放给代理对象调用
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void handler() throws Exception
	{
		doAction((Req)MappContext.getRequest().getBody(),(Rsp)MappContext.getResponse().getBody());
	}
	
	/**
	 * Action具体业务逻辑实现方法
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public abstract void doAction(Req request,Rsp response) throws Exception;
	
	public void setMappApplicationContext(MappApplicationContext context){
		this.context = context;
	}
	
	@Override
	public MappSerializer<IMappDatapackage> setSerializer() {
		return this.serializer;
	}
	
	public String responseToString(IMappDatapackage responseObject) throws Exception
	{
		return serializer.responseToString(responseObject);
	}

	public IMappDatapackage stringToRequest(String requestString) throws Exception
	{
		return serializer.stringToRequest(requestString);
	}
	
}
