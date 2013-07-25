package com.karatebancho.zouka.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TrieTree<V> {
	protected List<V> list = new ArrayList<>();
	protected LinkedList<TrieTree<V>> children = new LinkedList<>();
	protected String prefix;
	protected int count;

	public TrieTree() {
		this("");
	}

	public TrieTree(String prefix) {
		this.prefix = prefix;
	}

	public int size() {
		return count;
	}

	public List<V> get(Object key) {
		if (key == null) {
			return null;
		}
		String strKey = key.toString();
		if (StringUtils.equals(strKey, prefix)) {
			return this.list;
		}
		for (TrieTree<V> target : this.children) {
			if (StringUtils.equals(strKey, target.getPrefix())) {
				return target.get(strKey);
			}
			String commonPrefix = getCommonPrefix(strKey, target.getPrefix());
			if (commonPrefix.length() > 0) {
				return target.get(strKey.substring(commonPrefix.length()));
			}
		}
		return null;
	}

	public V put(String key, V value) {
		count++;
		// 自分のノードにつける
		if (StringUtils.equals(key, prefix)) {
			list.add(value);
			return null;
		}
		int commonPrefixSize = getCommonPrefix(key, prefix).length();
		String subKey = key.substring(commonPrefixSize);
		// 子供で部分一致するものがあるか
		for (int i = 0; i < children.size(); i++) {
			TrieTree<V> child = children.get(i);
			// 一致したらそのノード
			if (child.getPrefix().equals(subKey)) {
				child.put(subKey, value);
				return null;
			}
			// 前方が一致したらノードの分割
			String commonPrefix = getCommonPrefix(subKey, child.getPrefix());
			if (commonPrefix.length() > 0) {
				// 新しい親ノード
				TrieTree<V> newNode = new TrieTree<>(commonPrefix);
				children.set(i, newNode);
				// 元々あったノード
				child.setPrefix(child.getPrefix().substring(
						commonPrefix.length()));
				newNode.putChild(child);
				// 新規ノード
				TrieTree<V> newChild = new TrieTree<>(
						subKey.substring(commonPrefix.length()));
				newChild.putData(value);
				newNode.putChild(newChild);

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

	public void putChild(TrieTree<V> tree) {
		this.children.add(tree);
	}

	public void putData(V value) {
		this.list.add(value);
	}
}
