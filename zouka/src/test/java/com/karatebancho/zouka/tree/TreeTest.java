package com.karatebancho.zouka.tree;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class TreeTest {

	@Test
	public void test() {
		TrieTree<String> root = new TrieTree<>();
		// 文字列を追加できる
		root.put("toukyou", "東京");
		// 文字列の数を確認できる
		assertThat(root.size(), is(1));
		// 文字列を取得できる
		String actual = root.get("toukyou").get(0);
		assertThat(actual, is("東京"));
		// さらに文字を追加できる
		root.put("saitama", "埼玉");
		// 文字列の数を確認できる
		assertThat(root.size(), is(2));
		assertThat(root.get("saitama").get(0), is("埼玉"));
		//
		root.put("toukou", "投稿");
		assertThat(root.size(), is(3));
		assertThat(root.get("toukou").get(0), is("投稿"));
	}

	@Test
	public void 同一のキーワードに複数のデータ() {
		TrieTree<String> root = new TrieTree<>();
		root.put("toushi", "投資");
		root.put("toushi", "透視");
		root.put("toushi", "凍死");
		assertThat(root.size(), is(3));
		List<String> actual = root.get("toushi");
		assertThat(actual.size(), is(3));
		assertThat(actual.get(0), is("投資"));
		assertThat(actual.get(1), is("透視"));
		assertThat(actual.get(2), is("凍死"));
	}

	@Test
	public void 削除() {
		org.junit.Assert.fail();
	}

	@Test
	public void getCommonPrefix() {
		TrieTree<String> root = new TrieTree<>();
		assertThat(root.getCommonPrefix("abc", "def"), is(""));
		assertThat(root.getCommonPrefix("abc", "abd"), is("ab"));
	}
}
