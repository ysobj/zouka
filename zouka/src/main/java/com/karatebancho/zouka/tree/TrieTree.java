package com.karatebancho.zouka.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TrieTree<V> {
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
	 * 自分がこのキーを処理する責任があるか。 キーが自分のprefixと一致するか、共通のprefixを持つ場合、責任があると判断する。
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

	protected boolean isNeedSplitNode(String key) {
		return (getCommonPrefix(this.prefix, key).length() > 0 && !key
				.startsWith(this.prefix));
	}

	protected void putData(V value) {
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

	protected void splitNode(String prefix) {
		String subKey = getSubKey(prefix);
		TrieTree<V> newNode = new TrieTree<>(subKey, this.list, this.children);
		this.prefix = prefix;
		this.list = new ArrayList<>();
		this.children = new ArrayList<>();
		this.children.add(newNode);
	}

	public V put(String key, V value) {
		count++;
		// 自分のノードにつける
		if (StringUtils.equals(key, prefix)) {
			putData(value);
			return null;
		}
		if (isNeedSplitNode(key)) {
			String newPrefix = getCommonPrefix(this.prefix, key);
			this.splitNode(newPrefix);
			this.put(key, value);
			return null;
		}
		String subKey = this.getSubKey(key);
		// 子供で部分一致するものがあるか
		for (int i = 0; i < children.size(); i++) {
			TrieTree<V> child = children.get(i);
			// 一致した/それで始まっているならそのノード
			if (child.isResponsible(subKey)) {
				child.put(subKey, value);
				return null;
			}
		}
		// 新規ノードを作ってその下に。
		TrieTree<V> tree = new TrieTree<>(key);
		tree.put(key, value);
		children.add(tree);
		return null;
	}

	public void setPrefix(String val) {
		this.prefix = val;
	}

	public String getCommonPrefix(String a, String b) {
		return StringUtils.getCommonPrefix(a, b);
	}

	public String getPrefix() {
		return prefix;
	}

	protected void putChild(TrieTree<V> tree) {
		this.children.add(tree);
	}

	public boolean remove(String key, V value) {
		this.count--;
		if (key == null) {
			return false;
		}
		TrieTree<V> node = this.getNode(key);
		if (node != null) {
			node.removeData(value);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "TrieTree{prefix=" + prefix + ", list=" + list + ", children="
				+ children + "}";
	}

}
