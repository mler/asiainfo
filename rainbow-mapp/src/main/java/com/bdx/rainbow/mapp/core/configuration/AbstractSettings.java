package com.bdx.rainbow.mapp.core.configuration;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bdx.rainbow.mapp.core.util.JacksonUtils;

public abstract class AbstractSettings {

	@Override
	public String toString() {
		try {
			return JacksonUtils.toJson(this);
		} catch ( Exception e ) {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}
