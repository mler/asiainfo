package com.bdx.rainbow.urs.service.dubbo;

import org.springframework.stereotype.Service;

import com.bdx.rainbow.urs.dubbo.IDubboService;

//@Service(value="dubboService")
public class DubboService implements IDubboService {

	@Override
	public String dubboTest(String content) throws Exception {
		return "dubbo test successfully,content:"+content;
	}

}
