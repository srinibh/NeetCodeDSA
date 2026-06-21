package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicProgrammingProblemI {

	public static int climbStairs(int n) {
		int one = 1, two = 1;
		for (int i = 2; i <= n; i++) {
			int tmp = one;
			one = one + two;
			two = tmp;
		}
		return one;
	}

	public static int minCostClimbingStairs(int[] cost) {
		for (int i = cost.length - 3; i >= 0; i--) {
			cost[i] += Math.min(cost[i + 1], cost[i + 2]);
		}
		return Math.min(cost[0], cost[1]);
	}

	public static int rob(int[] nums) {
		int n = nums.length;
		if (n == 1)
			return nums[0];
		int prev2 = nums[0];
		int prev1 = Math.max(nums[0], nums[1]);

		for (int i = 2; i < n; i++) {
			int temp = prev1;
			prev1 = Math.max(prev1, nums[i] + prev2);
			prev2 = temp;
		}
		return prev1;
	}

	public static int rob1(int[] nums) {
		int n = nums.length;
		if (n == 1)
			return nums[0];
		if (n == 2)
			return Math.max(nums[0], nums[1]);
		int prev2 = nums[0];
		int prev1 = Math.max(nums[0], nums[1]);

		for (int i = 2; i < n - 1; i++) {
			int temp = prev1;
			prev1 = Math.max(prev1, nums[i] + prev2);
			prev2 = temp;
		}
		int ans1 = prev1;
		prev2 = nums[1];
		prev1 = Math.max(nums[1], nums[2]);

		for (int i = 3; i < n; i++) {
			int temp = prev1;
			prev1 = Math.max(prev1, nums[i] + prev2);
			prev2 = temp;
		}
		int ans2 = prev1;
		return Math.max(ans1, ans2);
	}

	public static String longestPalindrome(String s) {
		StringBuilder sb = new StringBuilder();
		// appending chars before, inbetween and end to make the string
		// such that we can use the manacher odd algorithm
		sb.append("$");
		for (int i = 0; i < s.length(); i++) {
			sb.append("#");
			sb.append(s.charAt(i));
		}
		sb.append("#");
		sb.append("&");

		int[] p = manacher_odd(sb.toString());
		int center = 0, resLen = 0;
		for (int i = 0; i < p.length; i++) {
			if (p[i] > resLen) {
				center = i;
				resLen = p[i];
			}
		}

		return s.substring((center - resLen) / 2, (center + resLen) / 2);
	}

	private static int[] manacher_odd(String s) {
		int[] p = new int[s.length()]; // max palindromes length centered at the index
		int center = 0; // center is to keep the new center
		int rightBoundary = 0; // rightBoundary to keep the boundary

		for (int i = 1; i < s.length() - 1; i++) {
			// find the mirror [..mirror...center...i..]
			int mirror = 2 * center - i;

			// if i is inside the boundary then only we can assign the value
			if (i < rightBoundary)
				p[i] = Math.min(p[mirror], rightBoundary - i);

			// while the chars are matching keep incrementing p[i]
			// which interns result into match the next chars to the left and right
			while (s.charAt(i - (p[i] + 1)) == s.charAt(i + (p[i] + 1)))
				p[i]++;

			// if the palindrome with centered i, exceeds the right boundary
			// then we need to update the right boundary and the new center
			if (i + p[i] > rightBoundary) {
				center = i;
				rightBoundary = i + p[i];
			}
		}
		return p;
	}

	public static int countSubstrings(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append("$");
		for (int i = 0; i < s.length(); i++) {
			sb.append("#");
			sb.append(s.charAt(i));
		}
		sb.append("#");
		sb.append("@");
		int[] p = manacher_odd1(sb.toString());
		int ans = 0;
		for (int i = 0; i < p.length; i++)
			ans += (p[i] + 1) / 2;
		return ans;
	}

	private static int[] manacher_odd1(String s) {
		int[] p = new int[s.length()];
		int center = 0, rightBoundary = 0;

		for (int i = 1; i < s.length() - 1; i++) {

			int mirror = 2 * center - i;
			if (i < rightBoundary)
				p[i] = Math.min(p[mirror], rightBoundary - i);

			while (s.charAt(i - (p[i] + 1)) == s.charAt(i + (p[i] + 1)))
				p[i]++;

			if (i + p[i] > rightBoundary) {
				center = i;
				rightBoundary = i + p[i];
			}
		}

		return p;
	}

	public static int numDecodings(String s) {
		int n = s.length();

		int prev = 1, prev2 = 0, curr = 0;

		for (int i = n - 1; i >= 0; i--) {
			char ch = s.charAt(i);
			curr = 0;
			if (ch != '0') {
				curr += prev;
				if (i < n - 1 && ((ch - '0' == 1) || (ch - '0' <= 2 && s.charAt(i + 1) - '0' <= 6)))
					curr += prev2;
			}
			int tmp = prev;
			prev = curr;
			prev2 = tmp;
		}
		return curr;
	}

	public static int coinChange(int[] coins, int amount) {
		int[] dp = new int[amount + 1];
		Arrays.fill(dp, amount + 1);
		dp[0] = 0;
		for (int i = 1; i <= amount; i++) {
			for (int j = 0; j < coins.length; j++) {
				if (coins[j] <= i) {
					dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
				}
			}
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}

	public static int maxProduct(int[] nums) {
		int n = nums.length;
		int min = nums[0], max = nums[0];
		int result = nums[0];

		for (int i = 1; i < n; i++)
			result = Math.max(result, nums[i]);

		for (int i = 1; i < n; i++) {
			if (nums[i] == 0) {
				min = 1;
				max = 1;
			} else {
				int tmp1 = min;
				min = Math.min(min * nums[i], Math.min(max * nums[i], nums[i]));
				max = Math.max(tmp1 * nums[i], Math.max(max * nums[i], nums[i]));
				result = Math.max(result, max);
			}
		}

		return result;
	}

	public static boolean wordBreak(String s, List<String> wordDict) {
		int n = s.length();
		boolean dp[] = new boolean[n + 1];
		dp[n] = true;

		for (int i = n - 1; i >= 0; i--) {
			for (String w : wordDict) {
				if (i + w.length() <= n && w.equals(s.substring(i, i + w.length())))
					dp[i] = dp[i + w.length()];

				if (dp[i])
					break;
			}
		}
		return dp[0];
	}

	public static int lengthOfLIS(int[] nums) {
		int n = nums.length;
		int[] lis = new int[n];
		Arrays.fill(lis, 1);
		lis[n - 1] = 1;
		int res = 1;
		for (int i = n - 2; i >= 0; i--) {
			for (int j = i + 1; j < n; j++) {
				if (nums[i] < nums[j])
					lis[i] = Math.max(lis[i], 1 + lis[j]);
			}
			res = Math.max(res, lis[i]);
		}
		return res;
	}

	static Boolean[][] dp;

	public static boolean canPartition(int[] nums) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		if (sum % 2 != 0)
			return false;
		dp = new Boolean[nums.length][sum / 2 + 1];
		return subsetSum(nums, 0, sum / 2);
	}

	// DP
	private static boolean subsetSum(int[] nums, int ind, int sum) {
		if (ind >= nums.length || sum < 0)
			return false;
		if (sum == 0)
			return true;
		if (dp[ind][sum] != null)
			return dp[ind][sum];
		dp[ind][sum] = subsetSum(nums, ind + 1, sum - nums[ind]) || subsetSum(nums, ind + 1, sum);
		return dp[ind][sum];
	}

	public static void main(String args[]) {
		System.out.println(climbStairs(3));
		int[] cost= {1,100,1,1,1,100,1,1,100,1};
		System.out.println(minCostClimbingStairs(cost));
		int[] houseRob = {2,7,9,3,1};
		System.out.println(rob(houseRob));
		System.out.println(rob1(houseRob));
		System.out.println(longestPalindrome("babad"));
		System.out.println(countSubstrings("aaa"));
		System.out.println(numDecodings("226"));
		int[] coins= {1,2,5};
		System.out.println(coinChange(coins,11));
		int[] nums= {2,3,-2,4};
		System.out.println(maxProduct(nums));
		String s = "applepenapple";
		List<String> worDictList=new ArrayList<>();
		worDictList.add("apple");
		worDictList.add("pen");
		System.out.println(wordBreak(s,worDictList));
		int[] increasNum= {10,9,2,5,3,7,101,1};
		System.out.println(lengthOfLIS(increasNum));
		int[] partitionNums = {1,5,11,5};
		System.out.println(canPartition(partitionNums));











	}

}
