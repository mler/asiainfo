package com.bdx.rainbow.crawler.analyzer;

import java.util.Collection;
import java.util.Map;

import com.bdx.rainbow.crawler.seed.Seed;

public interface Analyzer {
	public Map<Class<? extends Seed>,Collection<Seed>> analyze(Seed seed) throws Exception;
}
