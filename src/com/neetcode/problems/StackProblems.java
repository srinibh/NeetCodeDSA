package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class StackProblems {

	public static boolean isValid(String s) {
		Stack<Character> st = new Stack<>();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(' || c == '{' || c == '[')
				st.push(c);
			else if (c == ')' && !st.empty()) {
				char ch = st.pop();
				if (ch != '(')
					return false;
			} else if (c == '}' && !st.empty()) {
				char ch = st.pop();
				if (ch != '{')
					return false;
			} else if (c == ']' && !st.empty()) {
				char ch = st.pop();
				if (ch != '[')
					return false;
			} else
				return false;
		}

		return st.empty();
	}

	class Pair

	{
		int val;
		int min;

		Pair(int v, int m) {
			val = v;
			min = m;
		}

		void setVal(int v) {
			val = v;
		}

		void setMin(int m) {
			min = m;
		}
	}

	class MinStack {
		Stack<Pair> st;

		public MinStack() {
			st = new Stack<>();
		}

		public void push(int val) {
			if (st.empty())
				st.push(new Pair(val, val));
			else {
				int min = Math.min(st.peek().min, val);
				st.push(new Pair(val, min));
			}
		}

		public void pop() {
			st.pop();
		}

		public int top() {
			return st.peek().val;
		}

		public int getMin() {
			return st.peek().min;
		}

	}

	/**
	 * Your MinStack object will be instantiated and called as such: MinStack obj =
	 * new MinStack(); obj.push(val); obj.pop(); int param_3 = obj.top(); int
	 * param_4 = obj.getMin();
	 */

	public static int evalRPN(String[] tokens) {
		Stack<Integer> st = new Stack<>();
		for (String s : tokens) {
			if (s.equals("+")) {
				st.push(st.pop() + st.pop());
			} else if (s.equals("-")) {
				int n2 = st.pop();
				int n1 = st.pop();
				st.push(n1 - n2);
			} else if (s.equals("*")) {
				st.push(st.pop() * st.pop());
			} else if (s.equals("/")) {
				int n2 = st.pop();
				int n1 = st.pop();
				st.push(n1 / n2);
			} else {
				st.push(Integer.parseInt(s));
			}
		}
		return st.peek();
	}

	public static List<String> generateParenthesis(int n) {
		List<String> ans = new ArrayList<>();
		generate(0, n, 0, 0, ans, "");
		return ans;
	}

	private static void generate(int index, int n, int lCount, int rCount, List<String> ans, String op) {
		if (lCount > n || rCount > n)
			return;
		if (lCount == rCount && lCount == n) {
			ans.add(op);
		}
		if (lCount > rCount) {
			String op1 = op + ")";
			generate(index + 1, n, lCount, rCount + 1, ans, op1);
		}
		String op2 = op + "(";
		generate(index + 1, n, lCount + 1, rCount, ans, op2);
	}

	public static int[] dailyTemperatures(int[] temperatures) {
		int n = temperatures.length;
		Stack<int[]> st = new Stack<>();
		int[] res = new int[n];

		for (int i = 0; i < n; i++) {
			while (!st.empty() && st.peek()[0] < temperatures[i]) {
				int[] temp = st.pop();
				res[temp[1]] = i - temp[1];
			}
			st.push(new int[] { temperatures[i], i });
		}

		return res;
	}

	// 1
	public static int carFleet1(int target, int[] position, int[] speed) {
		int n = position.length, res = 0;
		double[][] cars = new double[n][2];

		for (int i = 0; i < n; i++) {
			cars[i] = new double[] { position[i], (double) (target - position[i]) / speed[i] };
		}
		Arrays.sort(cars, (a, b) -> Double.compare(a[0], b[0]));
		double curr = 0;
		for (int i = n - 1; i >= 0; i--) {
			if (cars[i][1] > curr) {
				curr = cars[i][1];
				res++;
			}
		}
		return res;
	}

	// 2
	public static int carFleet2(int target, int[] pos, int[] speed) {
		Map<Integer, Double> m = new TreeMap<>(Collections.reverseOrder());
		for (int i = 0; i < pos.length; ++i)
			m.put(pos[i], (double) (target - pos[i]) / speed[i]);
		int res = 0;
		double cur = 0;
		for (double time : m.values()) {
			if (time > cur) {
				cur = time;
				res++;
			}
		}
		return res;
	}

	public static int largestRectangleArea(int[] heights) {
		int maxArea = 0;
		Stack<int[]> st = new Stack<>();
		for (int i = 0; i < heights.length; i++) {
			int start = i;
			while (!st.isEmpty() && st.peek()[1] > heights[i]) {
				int[] pair = st.pop();
				maxArea = Math.max(maxArea, pair[1] * (i - pair[0]));
				start = pair[0];
			}
			st.push(new int[] { start, heights[i] });
		}

		while (!st.isEmpty()) {
			int[] pair = st.pop();
			maxArea = Math.max(maxArea, pair[1] * (heights.length - pair[0]));
		}

		return maxArea;
	}

	public static void main(String args[]) {
		String validParanthesis = "()[]{}";
		String[] tokens = { "10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+" };
		int[] temperatures = { 73, 74, 75, 71, 69, 72, 76, 73 };
		int target = 12;
		int[] position = { 10, 8, 0, 5, 3 };
		int[] speed = { 2, 4, 1, 1, 3 };
		int[] height = { 2, 1, 5, 6, 2, 3 };

		System.out.println("Valid Paranthesis " + isValid(validParanthesis));
		System.out.println("Evaluate Paranthesis " + evalRPN(tokens));
		System.out.println("Generate Paranthesis " + generateParenthesis(3));
		System.out.println("Generate Paranthesis ");
		Arrays.stream(dailyTemperatures(temperatures)).forEach(System.out::print);
		System.out.println("Car fleet Problem 1 " + carFleet1(target, position, speed));
		System.out.println("Car fleet Problem 2 " + carFleet2(target, position, speed));
		System.out.println("Maximum Area of Rectangle " + largestRectangleArea(height));

	}

}
