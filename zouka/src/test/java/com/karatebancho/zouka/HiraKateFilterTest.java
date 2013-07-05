package com.karatebancho.zouka;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.karatebancho.zouka.impl.HiraKataFilter;

public class HiraKateFilterTest {
	protected HiraKataFilter filter = new HiraKataFilter();

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
