package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.JsonMapper;
import com.bdx.rainbow.mapp.core.base.BaseActionHandler;
import com.bdx.rainbow.mapp.core.model.IBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.bdx.rainbow.mapp.util.BeanValidators;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Map;

/**
 * action的抽象类
 * Created by fusj on 16/1/15.
 */
public abstract class AbstractBaseActionHandler<Request extends IBody, Response extends IBody>
		extends BaseActionHandler<Request, Response> {
	@Autowired
	protected Validator validator;
	
	protected static JsonMapper binder = JsonMapper.nonDefaultMapper();

	protected static Logger log = LoggerFactory.getLogger("com.bdx.mapp.interface");
	
	/**
	 * 注解验证：如为空，长度等
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected void doCheckModel() throws Exception {
		try {
			BeanValidators.validateWithException((Validator) validator,
					this.request);
		} catch (ConstraintViolationException e) {
			Map mapResult = BeanValidators.extractPropertyAndMessage(e);
			throw new Exception(binder.toJson(mapResult));
		}
	}
}
