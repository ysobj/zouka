package com.karatebancho.zouka;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.karatebancho.zouka.impl.RomanFilter;

public class RomanFilterTest {
	private RomanFilter filter = new RomanFilter();

	@Test
	public void test() {
		assertThat(filter.filter("ア"), is("a"));
		assertThat(filter.filter("あ"), is("あ"));
		assertThat(filter.filter("ヴ"), is("vu"));

		assertThat(filter.filter(""), is(""));
		assertThat(filter.filter("トウキョウハヨルノシチジ"), is("toukixyouhayorunositizi"));
	}
}
