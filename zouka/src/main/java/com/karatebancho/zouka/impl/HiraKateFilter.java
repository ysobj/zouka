package com.karatebancho.zouka.impl;

import com.karatebancho.zouka.Filter;

public class HiraKateFilter implements Filter {
	private static final char HIRAGANA_FROM = 12353;
	private static final char HIRAGANA_TO = 12438;
	private static final int DELTA = 96;

	@Override
	public String filter(String original) {
		StringBuffer sb = new StringBuffer(original);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= HIRAGANA_FROM && c <= HIRAGANA_TO) {
				sb.setCharAt(i, (char) (c + DELTA));
			}
		}
		return sb.toString();
	}

}
