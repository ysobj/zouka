/**
 * 
 */
package com.karatebancho.zouka.impl;

import org.apache.commons.lang3.StringUtils;
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import com.karatebancho.zouka.Filter;

/**
 * @author ysobj
 * 
 */
public class MorphologicalFilter implements Filter {
	Tokenizer tokenizer = Tokenizer.builder().build();

	@Override
	public String filter(String original) {
		if (StringUtils.isEmpty(original)) {
			return original;
		}
		StringBuilder sb = new StringBuilder();
		for (Token token : tokenizer.tokenize(original)) {
			String reading = token.getReading();
			if (StringUtils.isEmpty(reading)) {
				sb.append(token.getSurfaceForm());
			} else {
				sb.append(reading);
			}
		}
		return sb.toString();
	}

}
