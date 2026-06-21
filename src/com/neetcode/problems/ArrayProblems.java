package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class ArrayProblems {

	/*
	 * Given an integer array nums, return true if any value appears at least twice
	 * in the array, and return false if every element is distinct.
	 */
	public static boolean containsDuplicate(int[] nums) {
		Set<Integer> set = new HashSet<>();
		for (int num : nums) {
			if (!set.add(num))
				return true;
		}
		return false;
	}

	/*
	 * Given an array of integers nums and an integer target, return indices of the
	 * two numbers such that they add up to target.
	 */
	public static int[] twoSum(int[] nums, int target) {
		int n = nums.length;
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < n; i++) {
			int remaining = target - nums[i];
			if (map.containsKey(remaining)) {
				return new int[] { map.get(remaining), i };
			}
			map.put(nums[i], i);
		}
		return new int[] { -1, -1 };

	}

	/*
	 * Given an integer array nums and an integer k, return the k most frequent
	 * elements. You may return the answer in any order.
	 */
	public static int[] topKFrequent(int[] nums, int k) {
		Map<Integer, Integer> mp = new HashMap<>();
		int[] res = new int[k];
		for (int num : nums) {
			mp.put(num, mp.getOrDefault(num, 0) + 1);
		}
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> b.val - a.val);
		for (int key : mp.keySet())
			pq.offer(new Pair(key, mp.get(key)));
		for (int i = 0; i < k; i++)
			res[i] = pq.poll().key;

		return res;

	}

	static class Pair {
		int key;
		int val;

		Pair(int k, int v) {
			key = k;
			val = v;
		}
	}

	/*
	 * Given an integer array nums, return an array answer such that answer[i] is
	 * equal to the product of all the elements of nums except nums[i]. The product
	 * of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer. You
	 * must write an algorithm that runs in O(n) time and without using the division
	 * operation.
	 */
	public static int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] res = new int[n];
		int pre = 1;
		res[0] = 1;
		for (int i = 0; i < n - 1; i++) {
			pre *= nums[i];
			res[i + 1] = pre;
		}
		int post = 1;
		for (int i = n - 1; i > 0; i--) {
			post *= nums[i];
			res[i - 1] *= post;
		}
		return res;
	}

	/*
	 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be
	 * validated according to the following rules: Each row must contain the digits
	 * 1-9 without repetition. Each column must contain the digits 1-9 without
	 * repetition. Each of the nine 3 x 3 sub-boxes of the grid must contain the
	 * digits 1-9 without repetition. Note: A Sudoku board (partially filled) could
	 * be valid but is not necessarily solvable. Only the filled cells need to be
	 * validated according to the mentioned rules.
	 */
	public static boolean isValidSudoku(char[][] board) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				char c = board[i][j];
				if (isDigit(c) && !isValid(c, i, j, board)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean isDigit(char c) {
		if (c - '0' > 9 || c - '0' < 0)
			return false;
		return true;
	}

	private static boolean isValid(char c, int i, int j, char[][] board) {
		if (isValidRow(c, i, j, board) && isValidCol(c, i, j, board) && isValidGrid(c, i, j, board))
			return true;
		return false;
	}

	private static boolean isValidRow(char c, int row, int col, char[][] board) {
		for (int i = 0; i < 9; i++) {
			if (board[row][i] == c && i != col)
				return false;
		}
		return true;
	}

	private static boolean isValidCol(char c, int row, int col, char[][] board) {
		for (int i = 0; i < 9; i++) {
			if (board[i][col] == c && i != row)
				return false;
		}
		return true;
	}

	private static boolean isValidGrid(char c, int row, int col, char[][] board) {
		int initialRow = 3 * (row / 3), initialCol = 3 * (col / 3);

		for (int i = initialRow; i < initialRow + 3; i++) {
			for (int j = initialCol; j < initialCol + 3; j++) {
				if (board[i][j] == c && i != row && j != col)
					return false;
			}
		}
		return true;
	}

	/*
	 * Given an unsorted array of integers nums, return the length of the longest
	 * consecutive elements sequence. You must write an algorithm that runs in O(n)
	 * time.
	 */
	public static int longestConsecutive(int[] nums) {
		Set<Integer> set = new HashSet<>();
		int result = 0;
		for (int num : nums)
			set.add(num);
		int count = 0;
		for (int num : nums) {
			if (!set.contains(num - 1)) {
				while (set.contains(num++))
					count++;
				result = Math.max(result, count);
				count = 0;
			}
		}
		return result;
	}

	/*
	 * Given a 1-indexed array of integers numbers that is already sorted in
	 * non-decreasing order, find two numbers such that they add up to a specific
	 * target number. Let these two numbers be numbers[index1] and numbers[index2]
	 * where 1 <= index1 < index2 <= numbers.length. Return the indices of the two
	 * numbers, index1 and index2, added by one as an integer array [index1, index2]
	 * of length 2. The tests are generated such that there is exactly one solution.
	 * You may not use the same element twice. Your solution must use only constant
	 * extra space.
	 */
	public static int[] twoSumPointer(int[] numbers, int target) {
		int left = 0, right = numbers.length - 1;

		while (left < right) {
			int sum = numbers[left] + numbers[right];
			if (sum == target) {
				return new int[] { left + 1, right + 1 };
			} else if (sum < target)
				left++;
			else
				right--;
		}

		return new int[] { -1, -1 };
	}

	/*
	 * Given an integer array nums, return all the triplets [nums[i], nums[j],
	 * nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] +
	 * nums[k] == 0. Notice that the solution set must not contain duplicate
	 * triplets.
	 */
	public static List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 2; i++) {
			if (i > 0 && nums[i] == nums[i - 1])
				continue;
			search(i, nums, result);
		}
		return result;
	}

	private static void search(int index, int[] nums, List<List<Integer>> result) {
		int left = index + 1, right = nums.length - 1;
		while (left < right) {
			int sum = nums[index] + nums[left] + nums[right];
			if (sum == 0) {
				result.add(Arrays.asList(nums[index], nums[left], nums[right]));
				left++;
				right--;
				while (left < right && nums[left] == nums[left - 1])
					left++;
				while (left < right && nums[right] == nums[right + 1])
					right--;
			} else if (sum < 0) {
				left++;
			} else {
				right--;
			}
		}
	}

	/*
	 * You are given an integer array height of length n. There are n vertical lines
	 * drawn such that the two endpoints of the ith line are (i, 0) and (i,
	 * height[i]). Find two lines that together with the x-axis form a container,
	 * such that the container contains the most water. Return the maximum amount of
	 * water a container can store. Notice that you may not slant the container.
	 */
	public static int maxArea(int[] height) {
		int area = 0, maxArea = 0;
		int left = 0, right = height.length - 1;

		while (left < right) {
			area = Math.min(height[left], height[right]) * (right - left);
			maxArea = Math.max(maxArea, area);
			if (height[left] < height[right]) {
				left++;
			} else {
				right--;
			}
		}

		return maxArea;
	}

	/*
	 * Given n non-negative integers representing an elevation map where the width
	 * of each bar is 1, compute how much water it can trap after raining.
	 */
	public static int trap(int[] height) {
		int left = 0, right = height.length - 1;
		int lMax = height[0], rMax = height[right];
		int ans = 0;
		while (left <= right) {
			if (lMax <= rMax) {
				lMax = Math.max(lMax, height[left]);
				int val = Math.min(lMax, rMax) - height[left];
				ans += val < 0 ? 0 : val;
				left++;
			} else {
				rMax = Math.max(rMax, height[right]);
				int val = Math.min(lMax, rMax) - height[right];
				ans += val < 0 ? 0 : val;
				right--;
			}
		}
		return ans;
	}

	/*
	 * Given an array of integers nums containing n + 1 integers where each integer
	 * is in the range [1, n] inclusive. There is only one repeated number in nums,
	 * return this repeated number. You must solve the problem without modifying the
	 * array nums and uses only constant extra space.
	 */

	public static int findDuplicateSlowFastPointer(int[] nums) {
		// 1 , 3 , 4 ,2 , 2
		int slow = nums[0], fast = nums[0];
		System.out.println(slow + "  - " + fast);
		do {
			slow = nums[slow];
			fast = nums[nums[fast]];
			System.out.println(slow + " -- " + fast);
		} while (slow != fast);
		slow = nums[0];
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[fast];
			System.out.println(slow + " --- " + fast);
		}
		return slow;
	}

	public static void main(String args[]) {
		int[] dupNums = { 1, 1, 1, 3, 3, 4, 3, 2, 4, 2 };
		System.out.println("To Check if Array contains Duplicates " + containsDuplicate(dupNums));
		int[] twoSum = { 2, 7, 11, 15 };
		System.out.println("To check if two elements of an array map to the sum /----------------");
		Arrays.stream(twoSum(twoSum, 9)).forEach(System.out::println);
		System.out.println("-------------------/");
		int[] topFreqNums = { 1, 1, 1, 2, 2, 3 };
		int[] resultTopFreqElems = topKFrequent(topFreqNums, 2);
		Arrays.stream(resultTopFreqElems).forEach(System.out::print);
		System.out.println();
		int[] prodExcpetSelfs = { 1, 2, 3, 4, 5 };
		Arrays.stream(productExceptSelf(prodExcpetSelfs)).forEach(System.out::print);
		System.out.println();
		char[][] board = { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
				{ '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
				{ '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
				{ '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
				{ '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };
		System.out.println("is valid Sudoko " + isValidSudoku(board));
		int[] longSubSeq = { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 };
		System.out.println("Longest Sub Sequence " + longestConsecutive(longSubSeq));
		System.out.println("To check if two elements of an array map to the sum /----------------");
		Arrays.stream(twoSumPointer(twoSum, 9)).forEach(System.out::println);
		System.out.println("-------------------/");
		int[] threeSumNums = { -1, 0, 1, 2, -1, -4 };
		System.out.println(" Three sum Numbers " + threeSum(threeSumNums));
		int[] containerWithMostWater = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
		System.out.println(" Container with Most Water " + maxArea(containerWithMostWater));
		int[] intRainTrap = { 4, 2, 0, 3, 2, 5 };
		int[] intRainTrap2 = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		System.out.println(" Maximum Height Area for Rain Trapping water " + trap(intRainTrap));
		System.out.println(" Maximum Height Area for Rain Trapping water " + trap(intRainTrap2));
		int[] duplicates = { 1, 3, 4, 2, 2 };
		System.out.println(" Array to check for duplicates " + findDuplicateSlowFastPointer(duplicates));

	}

}
