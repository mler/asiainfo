/**
 * 
 */
package com.bdx.rainbow.mapp.model;

import java.util.ArrayList;
import java.util.List;

import com.bdx.rainbow.mapp.core.model.IBody;
import com.bdx.rainbow.mapp.core.model.IHeader;
import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
import com.bdx.rainbow.mapp.core.model.M9999Request;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yangqx
 *
 */
public class BDXDatapackage implements IMappDatapackage {
	@JsonProperty
	private IHeader header;
	@JsonProperty
	private IBody body;
	
	public BDXDatapackage() {
		super();
	}

	public BDXDatapackage(BDXHeader header) {
		super();
		this.header = header;
	}

	public BDXDatapackage(IHeader header, IBody body) {
		super();
		this.header = header;
		this.body = body;
	}
	
	public IHeader getHeader() {
		return header;
	}

	public void setHeader(IHeader header) {
		this.header = header;
	}

	public IBody getBody() {
		return body;
	}

	public void setBody(IBody body) {
		this.body = body;
	}

//	public static final void main(String[] args) throws Exception
//	{
//		BDXDatapackage pkg = new BDXDatapackage();
//		
//		M9999Request req = new M9999Request();
//		req.setInterfaceClass("com.ailk.mapp.service.InterfaceServiceImpl");
//		req.setMethod("doLogin");
//		List<String> params = new ArrayList<String>(0);
//		params.add("iyuxl");
//		params.add("123456");
//		params.add("localhost");
//		params.add("Mac");
//		params.add("mler");
//		req.setParams(params);
//		
//		BDXHeader header = new BDXHeader();
//		header.setBizCode("m9999");
//		
//		pkg.setHeader(header);
//		pkg.setBody(req);
//		
//		ObjectMapper mapper = new ObjectMapper();
//		
//		
//		System.out.println(mapper.writeValueAsString(pkg));
//		
//	}
	
}
