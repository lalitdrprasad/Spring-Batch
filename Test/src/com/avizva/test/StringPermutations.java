package com.avizva.test;

import java.util.HashSet;
import java.util.Set;

public class StringPermutations {

	public static Set<String> solve(String word, String str, Set<String> result) {
		if (str.length() == 0) {
			result.add(word);
		}
		for (int i = 0; i < str.length(); i++) {
			String strWithoutCurrentIndex = str.substring(0, i) + str.substring(i + 1);
			solve(word + str.charAt(i), strWithoutCurrentIndex, result);
		}
		return result;
	}

	public static void main(String[] args) {

		String str = "ABCDE";
		Set<String> result = new HashSet<>();
		Set<String> res = solve("", str, result);
		System.out.println(res);
		System.out.println(res.size());
	}
}
