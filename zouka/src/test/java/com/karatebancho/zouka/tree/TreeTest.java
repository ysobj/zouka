package com.karatebancho.zouka.tree;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
		String actual = root.get("toukyou");
		assertThat(actual, is("東京"));
		// さらに文字を追加できる
		root.put("saitama", "埼玉");
		// 文字列の数を確認できる
		assertThat(root.size(), is(2));
		assertThat(root.get("saitama"), is("埼玉"));
		//
		root.put("toukou", "投稿");
		assertThat(root.size(), is(3));
		assertThat(root.get("toukou"), is("投稿"));
	}

	@Test
	public void getCommonPrefix() {
		TrieTree<String> root = new TrieTree<>();
		assertThat(root.getCommonPrefix("abc", "def"), is(""));
		assertThat(root.getCommonPrefix("abc", "abd"), is("ab"));
	}
}
