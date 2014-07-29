package com.zhimeng.loupanguanli.util;

public class MathUtil {

	/*
	 * 求两个数的最大公约数
	 */
	public static int getGCD(int a, int b) {
		if (a < b) {// a中放大数
			int temp = a;
			a = b;
			b = temp;
		}
		while (b != 0) {
			int r = a % b;
			a = b;
			b = r;
		}
		return a;
	}

}
