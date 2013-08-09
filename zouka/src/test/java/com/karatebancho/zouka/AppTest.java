package com.karatebancho.zouka;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.karatebancho.zouka.impl.HiraKataFilter;
import com.karatebancho.zouka.impl.MorphologicalFilter;
import com.karatebancho.zouka.impl.RomanFilter;

/**
 * Unit test for simple App.
 */
public class AppTest {
	protected static List<Filter> filters = new ArrayList<>();
	{
		filters.add(new MorphologicalFilter());
		filters.add(new HiraKataFilter());
		filters.add(new RomanFilter());
	}

	@Test
	public void testApp() {
		assertThat(filter("笑止高齢化"), is(filter("少子高齢化")));
		assertThat(filter("しゃ皆保険"), is(filter("社会保険")));
		assertThat(filter("電車間に合いそう"), is(filter("電車マニア移送")));
		// assertThat(filter("滑稽銀"), is(filter("国会議員")));
		// Expected: is "koxtukaigiinn"
		// but: was "koxtukeiginn"
		// System.out.println(StringUtils.getLevenshteinDistance("koxtukaigiinn",
		// "koxtukeiginn"));
	}

	protected String filter(String original) {
		String tmp = original;
		for (Filter filter : filters) {
			tmp = filter.filter(tmp);
		}
		return tmp;
	}
}
