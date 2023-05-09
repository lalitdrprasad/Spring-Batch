package com.avizva.test;

import java.text.DecimalFormat;

public class Distance {

	public static double destination(int input1, int input2, int input3[][]) {
		double res = 0;
		double dest = Double.MAX_VALUE;
		DecimalFormat dec = new DecimalFormat("0.000000");
		for (int i = 0; i < input2; i++) {
			for (int j = 0; j < input2; j++) {
				if (i >= j)
					continue;
				dest = Math.sqrt(Math.pow(input3[i][0] - input3[i][1], 2) + Math.pow(input3[j][0] - input3[j][1], 2));
				dest = Double.parseDouble(dec.format(dest)) / input1;
				if (res < dest) {
					res = dest;
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		int input1 = 2;
		int input2 = 3;
		int input3[][] = { { 0, 0 }, { 0, 2 }, { 2, 0 } };
		System.out.println(destination(input1, input2, input3));
	}
}
