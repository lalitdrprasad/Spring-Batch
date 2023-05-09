package com.avizva.test;

import java.util.HashSet;
import java.util.Set;

public class IsoGram {
	public static void main(String[] args) {
		String str = "HelloMyNameIsLalitPrasad";
		char[] ar = str.toCharArray();
		Set<Character> set = new HashSet<Character>();
		boolean isIsogram = true;
		for (char c : ar) {
			if (set.contains(c))
				isIsogram = false;
			else
				set.add(c);
		}
		System.out.println(isIsogram);
	}
}
