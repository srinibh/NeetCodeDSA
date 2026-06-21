package com.neetcode.problems;

import java.util.HashMap;
import java.util.Map;

public class DynamicProgrammingProblemIII {

	public static int uniquePaths(int m, int n) {
		int[][] dp = new int[m][n];
		// last column
		for (int i = 0; i < m; i++) {
			dp[i][n - 1] = 1;
		}
		// last row
		for (int i = 0; i < n; i++) {
			dp[m - 1][i] = 1;
		}

		for (int i = m - 2; i >= 0; i--) {
			for (int j = n - 2; j >= 0; j--) {
				dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
			}
		}
		return dp[0][0];
	}

	public static int longestCommonSubsequence(String text1, String text2) {
		int n1 = text1.length(), n2 = text2.length();
		int[][] dp = new int[n1 + 1][n2 + 1];

		for (int i = 1; i <= n1; i++) {
			char c1 = text1.charAt(i - 1);
			for (int j = 1; j <= n2; j++) {
				char c2 = text2.charAt(j - 1);
				if (c1 == c2) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[n1][n2];
	}

	public static int maxProfit(int[] prices) {
		boolean buy = false;
		Map<String, Integer> mp = new HashMap<>();
		return helper(prices, 0, mp, true);
	}

	private static int helper(int[] prices, int index, Map<String, Integer> mp, boolean buying) {
		if (index >= prices.length)
			return 0;
		if (mp.containsKey("(" + index + "," + buying + ")"))
			return mp.get("(" + index + "," + buying + ")");
		// in both the cases we have the cooldown
		int cooldown = helper(prices, index + 1, mp, buying);
		if (buying) {
			int buy = helper(prices, index + 1, mp, !buying) - prices[index];
			mp.put("(" + index + "," + buying + ")", Math.max(cooldown, buy));
		} else {
			// we can't buy in next index so we pass the index+2
			int sell = helper(prices, index + 2, mp, !buying) + prices[index];
			mp.put("(" + index + "," + buying + ")", Math.max(cooldown, sell));
		}
		return mp.get("(" + index + "," + buying + ")");
	}

	public static int change(int amount, int[] coins) {
		int n = coins.length;
		int dp[][] = new int[n][amount + 1];

		for (int i = 0; i < n; i++)
			dp[i][0] = 1;

		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= amount; j++) {
				int val = j - coins[i];
				if (i > 0) {
					if (val >= 0)
						dp[i][j] = dp[i - 1][j] + dp[i][val];
					else
						dp[i][j] = dp[i - 1][j];
				} else {
					if (val >= 0)
						dp[i][j] = dp[i][val];
					else
						dp[i][j] = 0;
				}
			}
		}

		return dp[n - 1][amount];
	}

	// Consise
	public static int change1(int amount, int[] coins) {
		int[][] dp = new int[coins.length + 1][amount + 1];
		dp[0][0] = 1;

		for (int i = 1; i <= coins.length; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= amount; j++) {
				dp[i][j] = dp[i - 1][j] + (j >= coins[i - 1] ? dp[i][j - coins[i - 1]] : 0);
			}
		}
		return dp[coins.length][amount];
	}

	// Optimal
	public static int change2(int amount, int[] coins) {
		int[] dp = new int[amount + 1];
		dp[0] = 1;
		for (int coin : coins) {
			for (int i = coin; i <= amount; i++) {
				dp[i] += dp[i - coin];
			}
		}
		return dp[amount];
	}

	public static int longestIncreasingPath(int[][] matrix) {
		int[][] cache = new int[matrix.length][matrix[0].length];
		int res = 1;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++)
				res = Math.max(res, backtrack(matrix, i, j, -1, cache));
		}
		return res;
	}

	private static int backtrack(int[][] matrix, int i, int j, int prev, int[][] cache) {
		if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length || prev >= matrix[i][j])
			return 0;

		if (cache[i][j] != 0)
			return cache[i][j];

		int max = 1;

		max = Math.max(max, 1 + backtrack(matrix, i - 1, j, matrix[i][j], cache));
		max = Math.max(max, 1 + backtrack(matrix, i, j - 1, matrix[i][j], cache));
		max = Math.max(max, 1 + backtrack(matrix, i + 1, j, matrix[i][j], cache));
		max = Math.max(max, 1 + backtrack(matrix, i, j + 1, matrix[i][j], cache));

		cache[i][j] = max;
		return max;
	}

	public static int minDistance(String word1, String word2) {
		int m = word1.length(), n = word2.length();
		int dp[][] = new int[n + 1][m + 1];
		for (int i = 1; i <= n; i++) {
			dp[i][0] = i;
		}

		for (int i = 1; i <= m; i++) {
			dp[0][i] = i;
		}

		for (int i = 1; i <= n; i++) {
			char ch1 = word2.charAt(i - 1);
			for (int j = 1; j <= m; j++) {
				char ch2 = word1.charAt(j - 1);

				if (ch1 == ch2)
					dp[i][j] = dp[i - 1][j - 1];
				else
					dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i - 1][j - 1], dp[i][j - 1]));
			}
		}

		return dp[n][m];
	}

	public static void main(String args[]) {
		System.out.println(uniquePaths(3, 7));
		System.out.println(longestCommonSubsequence("abc", "abc"));
		int[] prices = { 1, 2, 3, 0, 2 };
		System.out.println(maxProfit(prices));
		int[] coins = { 1, 2, 5 };
		System.out.println(change1(5, coins));
		System.out.println(change2(5, coins));
		int[][] matrix = { { 9, 9, 4 }, { 6, 6, 8 }, { 2, 1, 1 } };
		System.out.println(longestIncreasingPath(matrix));
		System.out.println(minDistance("intention","execution"));
	}

}
