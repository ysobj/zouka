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
		TrieTree<V> node = getNode(key);
		if (node != null) {
			return node.list;
		}
		return null;
	}

	public boolean isResponsible(String key) {
		if (StringUtils.isEmpty(key)) {
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
		String subKey = StringUtils.isEmpty(prefix) ? strKey
				: strKey.substring(StringUtils.getCommonPrefix(strKey, prefix)
						.length());
		if (StringUtils.isEmpty(subKey)) {
			return null;
		}
		for (TrieTree<V> target : this.children) {
			String commonPrefix = getCommonPrefix(subKey, target.getPrefix());
			if (commonPrefix.length() > 0) {
				return target.getNode(subKey);
			}
		}
		return null;
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

	public V put(String key, V value) {
		count++;
		// 自分のノードにつける
		if (StringUtils.equals(key, prefix)) {
			putData(value);
			return null;
		}
		int commonPrefixSize = getCommonPrefix(key, prefix).length();
		String subKey = key.substring(commonPrefixSize);
		// 子供で部分一致するものがあるか
		for (int i = 0; i < children.size(); i++) {
			TrieTree<V> child = children.get(i);
			// 一致した/それで始まっているならそのノード
			if (child.getPrefix().equals(subKey)
					|| subKey.startsWith(child.getPrefix())) {
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

	protected void putChild(TrieTree<V> tree) {
		this.children.add(tree);
	}

	public boolean remove(String key, V value) {
		this.count--;
		if (key == null) {
			return false;
		}
		String strKey = key.toString();
		if (StringUtils.equals(strKey, prefix)) {
			if (removeData(value)) {
				return true;
			}
		}
		int commonPrefixSize = getCommonPrefix(key, prefix).length();
		String subKey = key.substring(commonPrefixSize);
		// 子供で部分一致するものがあるか
		for (int i = 0; i < children.size(); i++) {
			TrieTree<V> child = children.get(i);
			// 一致したらそのノード
			TrieTree<V> node = child.getNode(subKey);
			if (node != null) {
				if (node.removeData(value)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "TrieTree [list=" + list + ", prefix=" + prefix + "]";
	}

}
