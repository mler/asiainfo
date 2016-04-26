package com.bdx.rainbow.mapp.core.serializer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
import com.bdx.rainbow.mapp.core.util.JacksonUtils;

@Component
public class JsonSerializer implements MappSerializer<IMappDatapackage> {

	private Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

	@Override
	public IMappDatapackage stringToRequest(String requestString) throws Exception {
		
		logger.debug("Json request：" + requestString);

		if (StringUtils.isBlank(requestString))
			return null;
		
		try {
			
			return (IMappDatapackage) JacksonUtils.toBean(requestString, IMappDatapackage.class);
		
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String responseToString(IMappDatapackage responseObject)
			throws Exception {

		String json = null;

		try {

			json = JacksonUtils.toJson(responseObject);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		logger.debug("Json response ：" + json);

		return json;
	}

	@Override
	public String getName() {
		return "json";
	}
	
}
