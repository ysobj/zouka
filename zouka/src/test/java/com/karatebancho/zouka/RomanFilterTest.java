package com.karatebancho.zouka;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.karatebancho.zouka.impl.RomanFilter;

public class RomanFilterTest {
	private RomanFilter filter = new RomanFilter();

	@Test
	public void test() {
		assertThat(filter.filter("ア"), equalTo("a"));
		assertThat(filter.filter("あ"), equalTo("あ"));
		assertThat(filter.filter("ヴ"), equalTo("vu"));
		assertThat(filter.filter("チャ"), equalTo("tixya"));

		assertThat(filter.filter(""), equalTo(""));
		assertThat(filter.filter("トウキョウハヨルノシチジ"),
				equalTo("toukixyouhayorunositizi"));
		assertThat(filter.filter("トウキョウハヨルノシチj"),
				equalTo("toukixyouhayorunositiz"));
		assertThat(filter.filter("オc"), equalTo("ot"));
		assertThat(filter.filter("オch"), equalTo("ot"));
		assertThat(filter.filter("オcy"), equalTo("ot"));
		assertThat(filter.filter("キzy"), equalTo("kiz"));
		assertThat(filter.filter("シf"), equalTo("sih"));
		assertThat(filter.filter("アーク"), equalTo("aku"));
	}
}