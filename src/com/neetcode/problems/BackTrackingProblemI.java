package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackTrackingProblemI {

	public static List<List<Integer>> subsets(int[] nums) {
		List<Integer> tmp = new ArrayList<>();
		List<List<Integer>> ans = new ArrayList<>();
		backtrack(nums, 0, tmp, ans);
		return ans;
	}

	private static void backtrack(int[] nums, int index, List<Integer> tmp, List<List<Integer>> ans) {
		if (index > nums.length)
			return;
		if (index == nums.length) {
			ans.add(new ArrayList<>(tmp));
			return;
		}
		tmp.add(nums[index]);
		backtrack(nums, index + 1, tmp, ans);
		tmp.remove(tmp.size() - 1);
		backtrack(nums, index + 1, tmp, ans);
	}

	public static List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack(candidates, 0, new ArrayList<>(), res, target);

		return res;
	}

	private static void backtrack(int[] nums, int index, List<Integer> tmp, List<List<Integer>> ans, int target) {
		if (index >= nums.length || target < 0)
			return;
		if (target == 0) {
			ans.add(new ArrayList<>(tmp));
			return;
		}

		for (int i = index; i < nums.length; i++) {
			tmp.add(nums[i]);
			backtrack(nums, i, tmp, ans, target - nums[i]);
			tmp.remove(tmp.size() - 1);
		}
	}

	public static List<List<Integer>> permute(int[] nums) {
		boolean[] mark = new boolean[nums.length];
		List<List<Integer>> ans = new ArrayList<>();
		backtrack(nums, mark, new ArrayList<>(), ans);
		return ans;
	}

	private static void backtrack(int[] nums, boolean[] mark, List<Integer> tmp, List<List<Integer>> ans) {
		if (tmp.size() == nums.length) {
			ans.add(new ArrayList<>(tmp));
			return;
		}

		for (int i = 0; i < nums.length; i++) {
			if (mark[i] == false) {
				mark[i] = true;
				tmp.add(nums[i]);
				backtrack(nums, mark, tmp, ans);
				tmp.remove(tmp.size() - 1);
				mark[i] = false;
			}
		}
	}

	public static List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> ans = new ArrayList<>();
		Arrays.sort(nums);
		backtrackSubSetWithDup(nums, 0, new ArrayList<>(), ans);
		return ans;
	}

	private static void backtrackSubSetWithDup(int[] nums, int index, List<Integer> tmp, List<List<Integer>> ans) {
		if (index > nums.length)
			return;
		if (index == nums.length) {
			ans.add(new ArrayList<>(tmp));
			return;
		}
		tmp.add(nums[index]);
		backtrack(nums, index + 1, tmp, ans);
		tmp.remove(tmp.size() - 1);
		while (index + 1 < nums.length && nums[index] == nums[index + 1])
			index++;
		backtrack(nums, index + 1, tmp, ans);
	}

	public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
		List<List<Integer>> ans = new ArrayList<>();
		Arrays.sort(candidates);
		backtrack(candidates, 0, target, new ArrayList<>(), ans);
		return ans;
	}

	private static void backtrack(int[] nums, int index, int target, List<Integer> tmp, List<List<Integer>> ans) {
		if (target == 0) {
			ans.add(new ArrayList<>(tmp));
			return;
		}
		if (index >= nums.length || target < 0)
			return;

		tmp.add(nums[index]);
		backtrack(nums, index + 1, target - nums[index], tmp, ans);
		tmp.remove(tmp.size() - 1);
		while (index + 1 < nums.length && nums[index] == nums[index + 1])
			index++;
		backtrack(nums, index + 1, target, tmp, ans);
	}

	static Map<Character, char[]> mp;

	public static List<String> letterCombinations(String digits) {
		if (digits.equals(""))
			return new ArrayList<>();
		mp = new HashMap<>();
		mp.put('2', new char[] { 'a', 'b', 'c' });
		mp.put('3', new char[] { 'd', 'e', 'f' });
		mp.put('4', new char[] { 'g', 'h', 'i' });
		mp.put('5', new char[] { 'j', 'k', 'l' });
		mp.put('6', new char[] { 'm', 'n', 'o' });
		mp.put('7', new char[] { 'p', 'q', 'r', 's' });
		mp.put('8', new char[] { 't', 'u', 'v' });
		mp.put('9', new char[] { 'w', 'x', 'y', 'z' });
		List<String> res = new ArrayList<>();
		recursive(digits, 0, "", res);
		return res;
	}

	private static void recursive(String str, int i, String s, List<String> res) {
		if (i > str.length())
			return;
		if (i == str.length()) {
			res.add(s);
			return;
		}
		char chs[] = mp.get(str.charAt(i));
		for (int j = 0; j < chs.length; j++) {
			recursive(str, i + 1, s + chs[j], res);
		}

	}

	public static void main(String args[]) {
		int[] subsets = { 1, 2, 3 };
		System.out.println(subsets(subsets));
		int[] candidates = { 2, 3, 5 };
		int target = 8;
		System.out.println(combinationSum(candidates, target));
		System.out.println(permute(subsets));
		int[] subsetwithDups = { 1, 2, 2 };
		System.out.println(subsetsWithDup(subsetwithDups));
		int[] candidates2 = { 10, 1, 2, 7, 6, 1, 5 };
		System.out.println(combinationSum2(candidates2, 8));
		System.out.println(letterCombinations("23"));
	}

}
