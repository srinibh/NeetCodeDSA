package com.neetcode.problems;

import java.util.ArrayList;
import java.util.List;

public class BackTrackingProblemII {
	static boolean[][] visited;

	public static boolean exist(char[][] board, String word) {
		int rows = board.length;
		int cols = board[0].length;
		visited = new boolean[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (word.charAt(0) == board[i][j] && search(i, j, 0, word, board))
					return true;
			}
		}
		return false;
	}

	public static boolean search(int i, int j, int index, String word, char[][] board) {
		if (index == word.length())
			return true;

		if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || word.charAt(index) != board[i][j]
				|| visited[i][j]) {
			return false;
		}

		visited[i][j] = true;

		if (search(i + 1, j, index + 1, word, board) || search(i - 1, j, index + 1, word, board)
				|| search(i, j + 1, index + 1, word, board) || search(i, j - 1, index + 1, word, board)) {
			return true;
		}

		visited[i][j] = false;
		return false;
	}

	public static List<List<String>> partition(String s) {
		List<List<String>> result = new ArrayList();
		helper(s, new ArrayList<String>(), result);
		return result;
	}

	public static void helper(String s, List<String> step, List<List<String>> result) {
		if (s == null || s.length() == 0) {
			result.add(new ArrayList<>(step));
			return;
		}

		for (int i = 1; i <= s.length(); i++) {
			String temp = s.substring(0, i);
			if (isPalindrome(temp)) {
				step.add(temp);
				helper(s.substring(i, s.length()), step, result);
				step.remove(step.size() - 1);
			}
		}
		return;
	}

	private static boolean isPalindrome(String s) {
		int start = 0, end = s.length() - 1;
		while (start <= end) {
			if (s.charAt(start++) != s.charAt(end--))
				return false;
		}
		return true;
	}

	static int[] row;
	static boolean[] rw, ld, rd;

	public static List<List<String>> solveNQueens(int n) {
		row = new int[n];
		rw = new boolean[n];
		ld = new boolean[2 * n - 1];
		rd = new boolean[2 * n - 1];
		List<List<String>> ans = new ArrayList<>();
		backtrack(0, n, ans);
		return ans;
	}

	private static void backtrack(int c, int n, List<List<String>> ans) {
		if (c == n) {
			ans.add(printer(row, n));
		}
		for (int r = 0; r < n; r++) {
			if (!rw[r] && !ld[r - c + n - 1] && !rd[r + c]) {
				rw[r] = ld[r - c + n - 1] = rd[r + c] = true;
				row[c] = r;
				backtrack(c + 1, n, ans);
				rw[r] = ld[r - c + n - 1] = rd[r + c] = false;
			}
		}
	}

	private static List<String> printer(int[] row, int n) {
		List<String> res = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder(n);
			for (int j = 0; j < n; j++) {
				if (j == row[i])
					sb.append('Q');
				else
					sb.append('.');
			}
			res.add(sb.toString());
		}
		return res;
	}

	public static void main(String args[]) {
		char[][] board = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
		String word = "ABCCED";
		System.out.println(exist(board, word));
		System.out.println(partition("aab"));
		System.out.println(solveNQueens(4));

	}

}
