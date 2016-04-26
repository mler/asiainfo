/**
 * 
 */
package com.bdx.rainbow.mapp.core.base;

import java.util.Map;

import com.bdx.rainbow.mapp.core.model.IBody;
import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
import com.bdx.rainbow.mapp.core.serializer.SerializerAware;



/**
 * Action的业务接口类，一个接口对应一个MappAction实现类
 * @author mler
 * @since 2016-04-07
 */
public interface MappAction<Req extends IBody,Rsp extends IBody> extends SerializerAware{

//	/**
//	 * action对外业务处理接口
//	 * @param bizcode
//	 * @param version
//	 * @param requestString
//	 * @param context
//	 * @return
//	 * @throws Exception
//	 * @{@link Deprecated}
//	 */
//	public String doHandlePackage(final String bizcode,final String version,final String requestString,final Map<String, Object> context)
//			throws Exception;
	
	/**
	 * 对传入的参数进行处理，在调用具体业务逻辑方法之前，初始化Mapp上下文
	 * @param bizcode
	 * @param version
	 * @param requestString
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public void initMappContext(final String bizcode,final String version,final String requestString,final Map<String, Object> context)
	throws Exception;
	
	/**
	 * 业务处理
	 * @throws Exception
	 */
	public void handler() throws Exception;
	
	/**
	 * 将response对象模型转化为响应string
	 * @param responseObject
	 * @return
	 * @throws Exception
	 */
	public String responseToString(IMappDatapackage responseObject) throws Exception;
	
	/**
	 * 将请求的request字符串转化为request对象模型
	 * @param requestString
	 * @return
	 * @throws Exception
	 */
	public IMappDatapackage stringToRequest(String requestString) throws Exception;
}
