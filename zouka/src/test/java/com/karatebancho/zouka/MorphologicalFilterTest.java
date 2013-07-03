package com.karatebancho.zouka;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.karatebancho.zouka.impl.MorphologicalFilter;

public class MorphologicalFilterTest {
	protected MorphologicalFilter t = new MorphologicalFilter();

	@Test
	public void 単純なケース() {
		assertThat(t.filter("寿司が食べたい"), is("スシガタベタイ"));
	}

	@Test
	public void 例外系空文字列等() {
		t.filter(null);
	}

	@Test
	public void 変換途中を想定() {
		assertThat(t.filter("寿司が食べたいg"), is("スシガタベタイg"));
	}
}
