package com.bdx.rainbow.mapp.core.model;

public class MappPkg implements IMappDatapackage {

	private IHeader header;
	
	private IBody body;

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

}
