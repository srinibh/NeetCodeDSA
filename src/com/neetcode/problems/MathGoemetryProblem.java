package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathGoemetryProblem {

	public static void rotate(int[][] matrix) {
		transpose(matrix);
		for (int[] nums : matrix) {
			reverse(nums);
		}
	}

	private static void transpose(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix.length; j++) {
				int tmp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = tmp;
			}
		}
	}

	private static void reverse(int[] nums) {
		for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
			int tmp = nums[i];
			nums[i] = nums[j];
			nums[j] = tmp;
		}
	}

	public static List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> res = new ArrayList<>();
		int m = matrix.length - 1;
		int n = matrix[0].length - 1;
		int sr = 0, sc = 0;
		int i = sr, j = sc;
		while (sc <= n || sr <= m) {

			// 1st row
			while (j <= n) {
				res.add(matrix[i][j]);
				j++;
			}
			sr++;
			j = n;
			i = sr;

			// condition
			if (i > m || j > n) {
				break;
			}
			// last colunm
			while (i <= m) {
				res.add(matrix[i][j]);
				i++;
			}
			n--;
			i = m;
			j = n;

			if (i > m || j > n) {
				break;
			}

			// last row
			while (j >= sc) {
				res.add(matrix[i][j]);
				j--;
			}
			m--;
			j = sc;
			i = m;

			if (i > m || j > n) {
				break;
			}
			// 1st column
			while (i >= sr) {
				res.add(matrix[i][j]);
				i--;
			}
			sc++;
			i = sr;
			j = sc;

			if (i > m || j > n) {
				break;
			}
		}
		return res;
	}

	public static void setZeroes(int[][] matrix) {
		int col0 = 1;
		int n = matrix.length, m = matrix[0].length;
		// first column
		for (int i = 0; i < n; i++) {
			if (matrix[i][0] == 0)
				col0 = 0;
		}
		// first row
		for (int i = 0; i < m; i++) {
			if (matrix[0][i] == 0)
				matrix[0][0] = 0;
		}
		// starting from (1,1)
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0)
					matrix[i][j] = 0;
			}
		}

		if (matrix[0][0] == 0) {
			for (int i = 1; i < m; i++)
				matrix[0][i] = 0;
		}

		if (col0 == 0) {
			for (int i = 0; i < n; i++)
				matrix[i][0] = 0;
		}
	}

	public static boolean isHappy(int n) {
		int slow = n, fast = n;
		do {
			slow = sum(slow);
			fast = sum(sum(fast));
		} while (slow != fast);
		return slow == 1;

	}

	private static int sum(int n) {
		int sum = 0;
		while (n > 0) {
			int digit = n % 10;
			sum += (digit * digit);
			n /= 10;
		}
		return sum;
	}

	public static int[] plusOne(int[] digits) {
		int n = digits.length;

		for (int i = n - 1; i >= 0; i--) {
			if (digits[i] < 9) {
				digits[i]++;
				return digits;
			}
			digits[i] = 0;
		}
		int[] newDigits = new int[n + 1];
		newDigits[0] = 1;
		return newDigits;

	}

	public static double myPow(double x, int n) {
		if (n < 0)
			return myPowNeg(x, n);
		return myPowPos(x, n);
	}

	private static double myPowNeg(double x, int n) {
		if (x == 1 || n == 0)
			return 1;
		if (n == -1)
			return 1 / x;
		double ans = myPow(x, n / 2);
		ans *= ans;
		if (n % 2 != 0)
			return ans / x;
		return ans;
	}

	private static double myPowPos(double x, int n) {
		if (x == 1 || n == 0)
			return 1;
		double ans = myPow(x, n / 2);
		ans *= ans;
		if (n % 2 != 0)
			return ans * x;
		return ans;
	}
	
	    public static String multiply(String num1, String num2) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < 400; i++)
	            sb.append(0);
	        num1 = reverse(num1);
	        num2 = reverse(num2);
	        if(num1.length() > num2.length()) {
	            String tmp = num1;
	            num1  = num2;
	            num2 = tmp;
	        }
	        int carry = 0;
	        int i = 0, j = 0;
	        for (i = 0; i < num2.length(); i++) {
	            carry = 0;
	            for (j = 0; j < num1.length(); j++) {
	                int a =  num2.charAt(i)-'0';
	                int b = num1.charAt(j)-'0';
	                int n = a * b + carry;
	                int prev = sb.charAt(i+j)-'0';
	                int sum = (prev + n) % 10;
	                carry = (n+prev) / 10;
	                sum +='0';
	                sb.setCharAt(i + j, (char) sum);
	            }
	            sb.setCharAt(i+j, (char) (carry+'0'));
	        }

	        sb.setCharAt(i+j-1, (char) (carry+'0'));
	        sb.reverse();
	        int ind = 0;
	        while(sb.length() > 0 && sb.charAt(ind) == '0')
	            sb.deleteCharAt(ind);
	        return sb.length() == 0?"0":sb.toString();
	    }

	    private static String reverse(String s) {
	        StringBuilder sb = new StringBuilder(s);
	        sb.reverse();
	        return sb.toString();
	    }
	

	
	public static void main(String args[]) {
		int[][] matrix = { { 5, 1, 9, 11 }, { 2, 4, 8, 10 }, { 13, 3, 6, 7 }, { 15, 14, 12, 16 } };
		print(matrix);
		rotate(matrix);
		System.out.println();
		print(matrix);
		int[][] spiralmatrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		System.out.println(spiralOrder(spiralmatrix));
		int[][] zeromatrix = { { 0, 1, 2, 0 }, { 3, 4, 5, 2 }, { 1, 3, 1, 5 } };
		print(zeromatrix);
		setZeroes(zeromatrix);
		print(zeromatrix);
		System.out.println(isHappy(19));
		int[] digit = { 4, 3, 2, 1 };
		int[] arrNum = plusOne(digit);
		Arrays.stream(arrNum).forEach(System.out::print);
		System.out.println();
        System.out.println(myPow(2.0000,10));
        System.out.println(myPow(2.0000,-2));
        System.out.println(multiply("123","456"));

	}


	private static void print(int[][] matrix) {
		int row = matrix.length;
		int col = matrix[0].length;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print("  " + matrix[i][j]);
			}
			System.out.println();
		}

	}

}
