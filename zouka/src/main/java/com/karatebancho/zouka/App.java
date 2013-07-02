package com.karatebancho.zouka;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		Tokenizer tokenizer = Tokenizer.builder().build();
		StringBuilder sb = new StringBuilder();
		for (Token token : tokenizer.tokenize("寿司が食べられない。")) {
			sb.append(token.getReading());
		}
		System.out.println(sb.toString());
	}
}
