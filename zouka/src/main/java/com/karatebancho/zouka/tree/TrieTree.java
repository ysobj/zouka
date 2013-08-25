package com.karatebancho.zouka.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TrieTree<V extends Comparable<? super V>> {
	protected List<V> list = new ArrayList<>();
	protected List<TrieTree<V>> children = new LinkedList<>();
	protected String prefix;
	protected int count;

	public TrieTree() {
		this("");
	}

	protected TrieTree(String prefix, List<V> data, List<TrieTree<V>> children) {
		this.prefix = prefix;
		this.list = data;
		this.children = children;
	}

	protected TrieTree(String prefix) {
		this.prefix = prefix;
	}

	public int size() {
		return count;
	}

	public List<V> get(Object key) {
		if (key == null) {
			return null;
		}
		TrieTree<V> node = getNode(key);
		if (node != null) {
			return node.list;
		}
		return null;
	}

	/**
	 * 
	 * 自分(もしくはその子)がこのキーを処理する責任があるか。
	 * キーが自分のprefixと一致するか、共通のprefixを持つ場合、責任があると判断する。
	 * 
	 * @param key
	 * @return 自分が処理する場合true
	 */
	public boolean isResponsible(String key) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		if (StringUtils.isEmpty(prefix)) {
			return true;
		}
		if (key.charAt(0) != prefix.charAt(0)) {
			return false;
		}
		if (StringUtils.equals(prefix, key)) {
			return true;
		}
		int commonPrefixSize = getCommonPrefix(key, prefix).length();
		return commonPrefixSize > 0;
	}

	/**
	 * 
	 * 渡された文字列の先頭から、自分のprefixを除いた残りを返します。
	 * 
	 * prefix: hoge, key: hogefuga = fuga
	 * 
	 * @param key
	 * @return
	 */
	protected String getSubKey(String key) {
		int commonPrefixSize = getCommonPrefix(key, prefix).length();
		String subKey = key.substring(commonPrefixSize);
		return subKey;
	}

	protected TrieTree<V> getNode(Object key) {
		if (key == null) {
			return null;
		}
		String strKey = key.toString();
		if (StringUtils.equals(strKey, prefix)) {
			return this;
		}
		String subKey = this.getSubKey(strKey);
		if (StringUtils.isEmpty(subKey)) {
			return null;
		}
		for (TrieTree<V> target : this.children) {
			if (target.isResponsible(subKey)) {
				return target.getNode(subKey);
			}
		}
		return null;
	}

	/**
	 * 
	 * ノード分割を行うべきキーか判定します。
	 * 自分のprefixと共通部分があるが、自分のprefixで前方一致しない文字列の場合、分割を行うと判断します。
	 * 
	 * prefix: hogefuga, key:hogehoge = true <br>
	 * prefix: hoge, key: hogehoge = false
	 * 
	 * @param key
	 * @return
	 */
	protected boolean isNeedSplitNode(String key) {
		return (getCommonPrefix(this.prefix, key).length() > 0 && !key
				.startsWith(this.prefix));
	}

	/**
	 * 
	 * キー/ノードの分割を行います。 具体的には、キーを分割して、新しい子供に自分のデータを引き継ぎます。
	 * 
	 * @param prefix
	 */
	protected void splitNode(String key) {
		int commonPrefixSize = getCommonPrefix(key, prefix).length();
		String subKey = prefix.substring(commonPrefixSize);
		TrieTree<V> newNode = new TrieTree<>(subKey, this.list, this.children);
		this.prefix = key;
		this.list = new ArrayList<>();
		this.children = new ArrayList<>();
		this.children.add(newNode);
	}

	protected void putData(V value) {
		for (int i = 0; i < this.list.size(); i++) {
			V tmp = this.list.get(i);
			if (tmp.compareTo(value) > 0) {
				this.list.add(i, value);
				return;
			}
		}
		this.list.add(value);
	}

	protected boolean removeData(V value) {
		for (int i = 0; i < this.list.size(); i++) {
			V tmp = list.get(i);
			if (tmp.equals(value)) {
				this.list.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean put(String key, V value) {
		count++;
		// 自分のノードにつける
		if (putOnMyOwn(key, value)) {
			return true;
		}
		String subKey = this.getSubKey(key);
		// 子供で部分一致するものがあるか
		for (int i = 0; i < children.size(); i++) {
			TrieTree<V> child = children.get(i);
			// 一致した/それで始まっているならそのノード
			if (child.isResponsible(subKey)) {
				child.put(subKey, value);
				return true;
			}
		}
		// 新規ノードを作ってその下に。
		TrieTree<V> tree = new TrieTree<>(subKey);
		tree.put(subKey, value);
		for (int i = 0; i < children.size(); i++) {
			TrieTree<V> child = children.get(i);
			if (child.prefix.compareTo(subKey) > 0) {
				children.add(i, tree);
				return true;
			}
		}
		children.add(tree);
		return true;
	}

	protected boolean putOnMyOwn(String key, V value) {
		if (StringUtils.equals(key, prefix)) {
			putData(value);
			return true;
		}
		if (isNeedSplitNode(key)) {
			String newPrefix = getCommonPrefix(this.prefix, key);
			this.splitNode(newPrefix);
			this.put(key, value);
			return true;
		}
		return false;
	}

	public String getCommonPrefix(String a, String b) {
		return StringUtils.getCommonPrefix(a, b);
	}

	public boolean remove(String key, V value) {
		if (key == null) {
			return false;
		}
		TrieTree<V> node = this.getNode(key);
		if (node != null) {
			if (node.removeData(value)) {
				this.count--;
				return true;
			}
		}
		return false;
	}

	public List<V> findValues(String key) {
		List<V> list = new ArrayList<>();
		findValues(key, list);
		return list;
	}

	protected void findValues(String key, List<V> list) {
		if (StringUtils.isEmpty(key)) {
			list.addAll(this.list);
		}
		for (TrieTree<V> child : this.children) {
			if (StringUtils.isEmpty(key)) {
				child.findValues(key, list);
			}
			if (child.isResponsible(key)) {
				int commonPrefixLength = getCommonPrefix(key, child.prefix)
						.length();
				if (key.length() == commonPrefixLength) {
					child.findValues("", list);
				} else if (key.startsWith(child.prefix)) {
					child.findValues(key.substring(child.prefix.length()), list);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "TrieTree{prefix=" + prefix + ", list=" + list + ", children="
				+ children + "}";
	}

}
