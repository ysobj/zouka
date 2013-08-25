package com.karatebancho.zouka.tree;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class TreeTest {

	@Test
	public void test() {
		PatriciaTree<String> root = new PatriciaTree<>();
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
		//
		root.put("tenki", "天気");
		root.put("tenki", "転機");
		assertThat(root.get("tenki").size(), is(2));
	}

	@Test
	public void 同一のキーワードに複数のデータ() {
		PatriciaTree<String> root = new PatriciaTree<>();
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
	public void キーの分割がない場合の削除() {
		PatriciaTree<String> root = new PatriciaTree<>();
		root.put("toushi", "投資");
		root.put("naha", "那覇");
		assertThat(root.size(), is(2));
		root.remove("toushi", "投資");
		assertThat(root.size(), is(1));
		List<String> actual = root.get("toushi");
		assertThat(actual.size(), is(0));
	}

	@Test
	public void キーの分割が発生する場合の削除() {
		PatriciaTree<String> root = new PatriciaTree<>();
		root.put("toukyou", "東京");
		root.put("saitama", "埼玉");
		root.put("toukou", "投稿");
		root.put("toukou", "登校");
		assertThat(root.size(), is(4));
		assertThat(root.get("toukou").size(), is(2));
		assertThat(root.get("toukou").get(0), is("投稿"));
		root.remove("toukou", "投稿");
		root.remove("toukou", "登校");
		assertThat(root.size(), is(2));
		assertThat(root.get("toukou").size(), is(0));
	}

	@Test
	public void findAllValues() {
		PatriciaTree<String> root = new PatriciaTree<>();
		root.put("toukyou", "東京");
		root.put("saitama", "埼玉");
		root.put("toukou", "投稿");
		root.put("toukou", "登校");
		List<String> list = root.findValues("t");
		assertThat(list.size(), is(3));
	}

	@Test
	public void findAllValues2() {
		PatriciaTree<String> root = new PatriciaTree<>();
		root.put("toukyou", "東京");
		root.put("saitama", "埼玉");
		root.put("toukou", "投稿");
		root.put("toukou", "登校");
		List<String> list = root.findValues("touko");
		assertThat(list.size(), is(2));
	}

	@Test
	public void findAllValues3() {
		PatriciaTree<String> root = new PatriciaTree<>();
		root.put("toukyou", "東京");
		root.put("saitama", "埼玉");
		root.put("toukou", "投稿");
		root.put("toukou", "登校");
		List<String> list = root.findValues("toukon");
		assertThat(list.size(), is(0));
	}

	@Test
	public void remove() {
		PatriciaTree<String> root = new PatriciaTree<>();
		root.put("toukyou", "東京");
		root.put("saitama", "埼玉");
		root.put("toukou", "投稿");
		root.remove("toukyou", "東京");
		assertThat(root.size(), is(2));
		root.remove("toukyou", "東京");
		assertThat(root.size(), is(2));
	}

	@Test
	public void getCommonPrefix() {
		PatriciaTree<String> root = new PatriciaTree<>();
		assertThat(root.getCommonPrefix("abc", "def"), is(""));
		assertThat(root.getCommonPrefix("abc", "abd"), is("ab"));
	}
}
