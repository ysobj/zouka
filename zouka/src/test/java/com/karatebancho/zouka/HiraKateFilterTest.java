package com.karatebancho.zouka;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.karatebancho.zouka.impl.HiraKateFilter;

public class HiraKateFilterTest {
	protected HiraKateFilter filter = new HiraKateFilter();

	@Test
	public void 基本的な処理() {
		assertThat(filter.filter("あ"), is("ア"));
		assertThat(filter.filter("ゔ"), is("ヴ"));
		assertThat(filter.filter("ゕ"), is("ヵ"));
		assertThat(filter.filter("ア"), is("ア"));
		assertThat(filter.filter("松"), is("松"));
		assertThat(filter.filter("Ａ"), is("Ａ"));
		assertThat(filter.filter("z"), is("z"));
		assertThat(filter.filter("0"), is("0"));
	}
}
