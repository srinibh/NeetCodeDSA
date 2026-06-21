package com.neetcode.problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class GreedyProblemI {

	public static int maxSubArray(int[] nums) {
		int total = 0;
		int result = nums[0];
		for (int num : nums) {
			if (total < 0)
				total = 0;
			total = total + num;
			result = Math.max(result, total);
		}
		return result;
	}

	public static boolean canJump(int[] nums) {
		int ans = 0, n = nums.length;
		for (int i = 0; i < n - 1; i++) {
			if (ans < i)
				return false;
			if (ans < i + nums[i])
				ans = i + nums[i];
			if (ans >= n - 1)
				return true;
		}
		return ans >= n - 1;
	}

	public static int jump(int[] nums) {
		int n = nums.length;
		int dp[] = new int[n];
		Arrays.fill(dp, 10000);
		dp[n - 1] = 0;
		for (int i = n - 2; i >= 0; i--) {
			for (int j = nums[i]; j >= 0; j--) {
				if (i + j < n)
					dp[i] = Math.min(dp[i], 1 + dp[i + j]);
			}
		}
		return dp[0];
	}

	static class Pair {
		int value;
		int index;

		Pair(int v, int i) {
			value = v;
			index = i;
		}
	}

	public static int canCompleteCircuit(int[] gas, int[] cost) {
		int ind = 0, sum = 0;
		int[] diff = new int[gas.length];
		// PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> b.value - a.value);
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> b.value - a.value);

		for (int i = 0; i < gas.length; i++) {
			diff[i] = gas[i] - cost[i];
			sum += diff[i];
			if (diff[i] >= 0)
				pq.offer(new Pair(diff[i], i));
		}
		pq.forEach(i -> System.out.println(i.index + " index  " + i.value));
		if (sum < 0)
			return -1;

		while (!pq.isEmpty()) {
			Pair p = pq.poll();
			if (p.value >= 0) {
				int pathSum = 0;
				ind = p.index;
				int j = p.index;
				do {
					pathSum += diff[j];
					if (pathSum < 0)
						break;
					j = (j + 1) % gas.length;
				} while (j != p.index);

				if (pathSum >= 0)
					break;
			}
		}

		return ind;
	}

	public static boolean isNStraightHand(int[] hand, int groupSize) {
		if (hand.length % groupSize != 0)
			return false;

		Map<Integer, Integer> mp = new HashMap<>();
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> a - b);

		for (int i = 0; i < hand.length; i++) {
			if (!mp.containsKey(hand[i])) {
				pq.offer(hand[i]);
				mp.put(hand[i], 1);
			} else {
				mp.put(hand[i], mp.get(hand[i]) + 1);
			}
		}

		while (!pq.isEmpty()) {
			int min = pq.peek();
			int sz = 0;
			while (sz < groupSize) {
				if (!mp.containsKey(min))
					return false;
				mp.put(min, mp.get(min) - 1);
				if (mp.get(min) == 0) {
					mp.remove(min);
					int val = pq.poll();
					if (val != min)
						return false;
				}
				min++;
				sz++;
			}

		}
		return true;
	}

	public static boolean isValid(String str) {
		int leftMin = 0, leftMax = 0;
		for (char ch : str.toCharArray()) {
			if (ch == '(') {
				leftMin++;
				leftMax++;
			} else if (ch == ')') {
				leftMin--;
				leftMax--;
			} else {
				leftMin--;
				leftMax++;
			}
			if (leftMax < 0) { // Means we encounter ) as a starting character or # of occurrences of ) is > (
				return false;
			}
			if (leftMin < 0) {
				leftMin = 0;
			}
		}
		return leftMin == 0;
	}

	public static void main(String args[]) {
		int[] maxSubArr = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
		System.out.println(maxSubArray(maxSubArr));
		int[] canJump = { 2, 3, 1, 1, 4 };
		System.out.println(jump(canJump));
		int[] canJump2 = { 2, 3, 1, 1, 4 };
		System.out.println(canJump(canJump2));
		int[] gas = { 1, 2, 3, 4, 5 };
		int[] cost = { 3, 4, 5, 1, 2 };
		System.out.println(canCompleteCircuit(gas, cost));
		int[] hand = { 1, 2, 3, 6, 2, 3, 4, 7, 8 };
		int groupSize = 3;
		System.out.println(isNStraightHand(hand, groupSize));
		System.out.println(isValid("(*))"));

	}

}
