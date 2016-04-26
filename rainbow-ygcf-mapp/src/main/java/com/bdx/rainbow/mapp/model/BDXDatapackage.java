package com.bdx.rainbow.mapp.model;

import com.bdx.rainbow.mapp.core.model.IBody;
import com.bdx.rainbow.mapp.core.model.IHeader;
import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
import com.bdx.rainbow.mapp.model.BDXHeader;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Created by fusj on 16/1/15.
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
}
