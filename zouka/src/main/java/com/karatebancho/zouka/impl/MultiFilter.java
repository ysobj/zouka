package com.karatebancho.zouka.impl;

import java.util.ArrayList;
import java.util.List;

import com.karatebancho.zouka.Filter;

public class MultiFilter implements Filter {
	protected List<Filter> filters = new ArrayList<>();

	public void addFilter(Filter f) {
		filters.add(f);
	}

	@Override
	public String filter(String original) {
		String val = original;
		for (Filter filter : filters) {
			val = filter.filter(val);
		}
		return val;
	}

}
