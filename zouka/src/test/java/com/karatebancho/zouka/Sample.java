package com.karatebancho.zouka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import com.karatebancho.zouka.impl.RomanFilter;
import com.karatebancho.zouka.tree.TrieTree;

public class Sample {

	public static void main(String[] args) throws Exception {
		new Sample().process();
	}

	public void process() throws IOException {
		RomanFilter filter = new RomanFilter();
		InputStream is = this.getClass().getResourceAsStream("/data.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str;
		TrieTree<String> root = new TrieTree<>();
		int i = 0;
		while ((str = reader.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(str);
			String val = tokenizer.nextToken();
			String key = tokenizer.nextToken();
			root.put(filter.filter(key), val);
			i++;
		}
		System.out.println(i);
		List<String> result = root.get("tenki");
		System.out.println(result.size());
	}
}
