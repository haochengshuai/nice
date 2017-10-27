package haocs.utils.operation;

import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

/**
 *  四则运算 暂不支持负数 
 * @author haocs
 * @since 2017年7月21日15:04:49
 */
public class Cal {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		Cal ca = new Cal();
		Scanner scan = new Scanner(System.in);
		String s = scan.nextLine();

		boolean checkNum = checkNum(s);
		if (checkNum) {
			ca.qu(s);
		}
	}

	/**
	 * 校验
	 * 
	 * @param s
	 * @return
	 */
	private static boolean checkNum(String s) {

		// TODO 正则 判断 开头 和结尾
		if (s.startsWith("-") || s.startsWith("+") || s.startsWith("*")
				|| s.startsWith("/")) {
			System.err.println("语法错误....");
			return false;
		}
		if (s.endsWith("-") || s.endsWith("+") || s.endsWith("*")
				|| s.endsWith("/") || s.endsWith("=")) {
			System.err.println("语法错误....");
			return false;
		}

		int left = StringUtils.countMatches(s, "(");
		int right = StringUtils.countMatches(s, ")");

		if (left == right) {

			String[] split = s.split("\\+|-|\\*|/");
			for (int i = 0; i < split.length; i++) {
				if (StringUtils.isEmpty(split[i])) {
					System.err.println("不支持负数... ");
					return false;
				}
			}

			if (s.indexOf("(") > -1 || s.indexOf(")") > -1) {
				char[] spl = s.toCharArray();
				for (int k = 0; k < spl.length; k++) {
					if (spl[k] == '(') {

						if (k > 0) {

							if (spl[k + 1] == '-' || spl[k + 1] == '+' || spl[k + 1] == '*' || spl[k + 1] == '/') {
								System.err.println("语法错误 ... ");
								return false;
							}
							if (spl[k - 1] == '-' || spl[k - 1] == '+' || spl[k - 1] == '*' || spl[k - 1] == '/') {
								
							}else{
								System.err.println("语法错误 ... ");
								return false;
							}
							
						}
						// 前必须是运算符
						// 后必须是数字
					} else if (spl[k] == ')') {
						if (k < spl.length-1 ) {

							// 前必须是数字
							// 后必须是运算符
							if (spl[k - 1] == '-' || spl[k - 1] == '+'
									|| spl[k - 1] == '*' || spl[k - 1] == '/') {
								System.err.println("语法错误 ... ");
								return false;
							}
							if (spl[k + 1] == '-' || spl[k + 1] == '+'
									|| spl[k + 1] == '*' || spl[k + 1] == '/') {
							}else{
								System.err.println("语法错误 ... ");
								return false;
							}
						}
					}
				}
				return true;
			} else {
				return true;
			}

		} else {
			System.err.println("小括号输入有误... ");
		}
		return false;
	}

	/*
	 * 循环去括号函数
	 */
	public void qu(String s) {
		int i;
		int first = 0;
		int last = 0;
		for (i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ('(')) {
				first = i;
			} else if (s.charAt(i) == (')'))

			{
				last = i;
				break;

			}
		}
		// 没有括号了
		if (last == 0) {
			System.out.println("上述算式的结果是：" + calculate(s));
		} else {
			String s1 = s.substring(0, first);
			String s2 = s.substring(first + 1, last);
			String s3 = s.substring(last + 1, s.length());
			float d = calculate(s2);
			String s4 = Float.toString(d);
			if(StringUtils.isNotBlank(s1)  || StringUtils.isNotBlank(s3)){
				s = s1 + s4 + s3;
				qu(s.trim());
			}else{
				System.out.println("上述算式的结果是  ：" + s4);
			}

		}

	}

	/*
	 * 四则运算计算函数
	 */
	public float calculate(String s) {
		int p = 0;
		int count = 0;

		String[] data = s.split("\\+|-|\\*|/");
		p = data.length - 1;

		// 将加减乘除和数字分离 ，并顺序存储在一个String类型的数组里
		String k[] = new String[2 * p + 1];
		int k1 = 0;
		int first = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*'
					|| s.charAt(i) == '/') {
				k[k1] = s.substring(first, i);
				k1++;
				k[k1] = "" + s.charAt(i);
				k1++;
				first = i + 1;
			}
		}
		k[k1] = s.substring(first, s.length());

		// 循环输出当前String数组中的各字符或字符型数字
		for (int i = 0; i < k.length - count; i++) {
			System.out.print(k[i] + " ");
		}
		System.out.println();

		// 分别计算加减符号的个数 乘除的符号个数
		int count1 = 0, count2 = 0;
		for (int i = 0; i < k.length; i++) {
			if (k[i].equals("*") || k[i].equals("/")) {
				count1++;
			} else if (k[i].equals("+") || k[i].equals("-")) {
				count2++;
			}
		}
		// 根据乘除号的个数循环优先计算，并将运算结果赋值在String型数组里，每计算一次String数组乘或除后的各字符串向前移两步
		for (int j = 0; j < count1; j++) {
			for (int i = 0; i < k.length - count; i++) {
				if (k[i].equals("*") || k[i].equals("/")) {
					if (k[i].equals("*")) {
						k[i - 1] = "" + (Float.parseFloat(k[i - 1]))
								* (Float.parseFloat(k[i + 1]));
						count = count + 2;
						for (int m = i + 2; m < k.length; m++) {
							k[m - 2] = k[m];
						}
						break;

					} else {
						k[i - 1] = "" + (Float.parseFloat(k[i - 1]))
								/ (Float.parseFloat(k[i + 1]));
						count = count + 2;
						for (int m = i + 2; m < k.length; m++) {
							k[m - 2] = k[m];
						}
						break;
					}

				}
			}
		}
		// 经过乘除后，循环输出剩余的加减字符和字符型数字
		for (int i = 0; i < k.length - count; i++) {
			System.out.print(k[i] + " ");
		}
		System.out.println();
		// 根据加减符号的个数，循环进行加减计算，并将计算结果存储在String型数组中，每计算一次，String数组加或减后各字符串向前移两步
		for (int j = 0; j < count2; j++) {
			for (int i = 0; i < k.length - count; i++) {
				if (k[i].equals("+") || k[i].equals("-")) {
					if (k[i].equals("+")) {
						float l = (Float.parseFloat(k[i - 1]))
								+ (Float.parseFloat(k[i + 1]));
						k[i - 1] = "" + l;
						count = count + 2;
						for (int m = i + 2; m < k.length; m++) {
							k[m - 2] = k[m];
						}
						break;
					} else if (k[i].equals("-")) {
						float n = (Float.parseFloat(k[i - 1]))
								- (Float.parseFloat(k[i + 1]));
						k[i - 1] = "" + n;
						count = count + 2;
						for (int m = i + 2; m < k.length; m++) {
							k[m - 2] = k[m];
						}
						break;
					}
				}
			}
		}
		// 所有加减乘除计算后，得到结果
		for (int i = 0; i < k.length - count; i++) {
			System.out.print(k[i] + " ");
		}
		System.out.println();
		// 返回最后结果
		return Float.parseFloat(k[0]);
	}
}