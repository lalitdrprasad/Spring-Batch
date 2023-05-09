package com.avizva.test;

public class Test {

	public static int getMin(int n, int[] sticks, int[] costs) {
		int res = Integer.MAX_VALUE;

		for (int i = 0; i < n; i++) {
			int sum = 0;
			for (int j = 0; j < n; j++) {
				sum = sum + Math.abs((sticks[j] - sticks[i]) * costs[j]);
			}
			if (res > sum)
				res = sum;
		}
		return res;
	}

	public static void main(String[] args) {
		int n = 3;
		int ar1[] = { 1, 2, 3 };
		int ar2[] = { 20, 30, 40 };
		System.out.println(getMin(n, ar1, ar2));
	}
}
