package com.bdx.rainbow.etl.rule;

import java.util.Collection;

public interface IGroup<V> {
	
	Collection<Collection<V>> group(Collection<V> objects,Rule rule) throws Exception;

}
