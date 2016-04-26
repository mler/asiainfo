package com.bdx.rainbow.etl.dispatcher;

import java.util.Collection;

import com.bdx.rainbow.etl.entity.seed.Seed;

public interface Dispatcher<S extends Seed,V> {

	public V dispatch(Collection<S> success,Collection<S> fail) throws Exception;
	
}
