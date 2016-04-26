package com.bdx.rainbow.etl.analyze;

import java.util.Collection;
import java.util.Map;

import com.bdx.rainbow.etl.entity.seed.Seed;

/**
 * 页面解析器，根据不同的页面生成不同的解析实现类
 * @author mler
 *
 */
public interface Analyze<SeedModel extends Seed,E,V>{

	public V findResult(Collection<E> objects) throws Exception;
	
	public Collection<SeedModel> findSeed(Collection<E> objects) throws Exception;
	
	public Collection<SeedModel> findPageSeed(Collection<E> objects) throws Exception;
	
	public void error(Map<String,Collection<E>> objects) throws Exception;

}
