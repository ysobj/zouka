package com.karatebancho.zouka.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class TrieTree<V> implements Map<String, V> {
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

	@Override
	public int size() {
		return count;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		String strKey = key.toString();
		if (StringUtils.equals(strKey, prefix)) {
			return this.list.get(0);
		}
		for (TrieTree<V> target : this.children) {
			if (StringUtils.equals(strKey, target.getPrefix())) {
				return target.get(strKey);
			}
		}
		return null;
	}

	@Override
	public V put(String key, V value) {
		if (StringUtils.equals(key, prefix)) {
			list.add(value);
			return null;
		}
		// if(prefix.length() > 0 && StringUtils.startsWith(str, key))
		TrieTree<V> tree = new TrieTree<>(key);
		tree.put(key, value);
		children.add(tree);
		count++;
		return null;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends V> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<String, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCommonPrefix(String a, String b) {
		return StringUtils.getCommonPrefix(a, b);
	}

	public String getPrefix() {
		return prefix;
	}

}
