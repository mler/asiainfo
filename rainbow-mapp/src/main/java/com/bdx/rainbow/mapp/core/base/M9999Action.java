package com.bdx.rainbow.mapp.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.model.IBody;
import com.bdx.rainbow.mapp.core.model.M9999Request;

@Service("m9999")
@Action(bizcode="m9999",version="1.0",usercheck=false)
public class M9999Action extends AbstractMappAction<M9999Request, IBody> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Test test;
	
	@Override
	public void doAction(M9999Request request, IBody response) throws Exception {
		logger.info("M9999 interface doAction................");
		test.hello2("M9999Action");
		test.helloName("M9999Action");
	}

}
