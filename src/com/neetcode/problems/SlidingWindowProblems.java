package com.neetcode.problems;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindowProblems {

	/*
	 * You are given an array prices where prices[i] is the price of a given stock
	 * on the ith day. You want to maximize your profit by choosing a single day to
	 * buy one stock and choosing a different day in the future to sell that stock.
	 * Return the maximum profit you can achieve from this transaction. If you
	 * cannot achieve any profit, return 0.
	 */
	public static int bestTimetoBuySellStocks(int[] prices) {
		int n = prices.length;
		if (n == 1)
			return 0;
		int maxP = 0;
		int l = 0, r = 1;

		while (r < n) {
			if (prices[l] < prices[r]) {
				int profit = prices[r] - prices[l];
				maxP = Math.max(maxP, profit);
			} else
				l = r;
			r++;
		}
		return maxP;
	}

	// Given a string s, find the length of the longest substring without repeating
	// characters.
	public static int lengthOfLongestSubstring(String s) {
		Map<Character, Integer> mp = new HashMap<>();
		int maxLen = 0, winStart = 0;
		for (int winEnd = 0; winEnd < s.length(); winEnd++) {
			char ch = s.charAt(winEnd);
			if (mp.containsKey(ch)) {
				winStart = Math.max(winStart, mp.get(ch) + 1);
			}
			mp.put(ch, winEnd);
			maxLen = Math.max(winEnd - winStart + 1, maxLen);
		}
		return maxLen;
	}

	/*
	 * You are given a string s and an integer k. You can choose any character of
	 * the string and change it to any other uppercase English character. You can
	 * perform this operation at most k times. Return the length of the longest
	 * substring containing the same letter you can get after performing the above
	 * operations.
	 */
	public static int characterReplacement(String s, int k) {
		int windowStart = 0, maxLength = 0, mostRepeatingCharCount = 0;
		HashMap<Character, Integer> charFreqMap = new HashMap();
		for (int windowEnd = 0; windowEnd < s.length(); windowEnd++) {
			char right = s.charAt(windowEnd);
			charFreqMap.put(right, charFreqMap.getOrDefault(right, 0) + 1);
			mostRepeatingCharCount = Math.max(mostRepeatingCharCount, charFreqMap.get(right));
			while (windowEnd - windowStart + 1 - mostRepeatingCharCount > k) {
				char left = s.charAt(windowStart);
				charFreqMap.put(left, charFreqMap.get(left) - 1);
				if (charFreqMap.get(left) == 0)
					charFreqMap.remove(left);
				windowStart++;
			}
			maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
		}
		return maxLength;
	}

	/*
	 * Given two strings s1 and s2, return true if s2 contains a permutation of s1,
	 * or false otherwise. In other words, return true if one of s1's permutations
	 * is the substring of s2. - 2 array of size 26 to keep count of chars present
	 * in both the strings[s1 and substring of s2] - match keeps the number of
	 * characters matches in both s1 and substring of s2 - if matches = 26, we have
	 * a permutation in that window - on each window, we check for conditions [ for
	 * both the chars(at left and at right) ]: - increment s2Map[rightCharInd]++ and
	 * decrement s2Map[leftCharInd]-- - if the char's count are same increment the
	 * matches - if the char's count differ by 1 - right: s1Map[rightCharInd] + 1 ==
	 * s2Map[rightCharInd], - left: s1Map[leftCharInd] - 1 == s2Map[leftCharInd]
	 * decrement the matches
	 */
	public static boolean checkInclusion(String s1, String s2) {
		if (s1.length() > s2.length())
			return false;
		char[] s1Map = new char[26];
		char[] s2Map = new char[26];
		for (int i = 0; i < s1.length(); i++) {
			s1Map[s1.charAt(i) - 'a']++;
			s2Map[s2.charAt(i) - 'a']++;
		}
		int matches = 0;
		for (int i = 0; i < 26; i++) {
			if (s1Map[i] == s2Map[i])
				matches++;
		}
		int windowStart = 0;
		for (int windowEnd = s1.length(); windowEnd < s2.length(); windowEnd++) {
			if (matches == 26)
				return true;
			int rightCharInd = s2.charAt(windowEnd) - 'a';
			s2Map[rightCharInd]++;
			if (s1Map[rightCharInd] == s2Map[rightCharInd])
				matches++;
			else if (s1Map[rightCharInd] + 1 == s2Map[rightCharInd])
				matches--;
			int leftCharInd = s2.charAt(windowStart) - 'a';
			s2Map[leftCharInd]--;
			if (s1Map[leftCharInd] == s2Map[leftCharInd])
				matches++;
			else if (s1Map[leftCharInd] - 1 == s2Map[leftCharInd])
				matches--;
			windowStart++;
		}
		return matches == 26;
	}

	/*
	 * Given two strings s and t of lengths m and n respectively, return the minimum
	 * window substring of s such that every character in t (including duplicates)
	 * is included in the window. If there is no such substring, return the empty
	 * string "". The testcases will be generated such that the answer is unique. A
	 * substring is a contiguous sequence of characters within the string.
	 */
	public static String minWindow(String str, String pattern) {
		int windowStart = 0, minLen = Integer.MAX_VALUE, matched = 0;
		Map<Character, Integer> charFreqMap = new HashMap<>();
		String res = "";
		for (char ch : pattern.toCharArray())
			charFreqMap.put(ch, charFreqMap.getOrDefault(ch, 0) + 1);

		for (int windowEnd = 0; windowEnd < str.length(); windowEnd++) {
			char right = str.charAt(windowEnd);

			if (charFreqMap.containsKey(right)) {
				charFreqMap.put(right, charFreqMap.get(right) - 1);
				if (charFreqMap.get(right) == 0)
					matched++;
			}

			while (matched == charFreqMap.size()) {
				if (minLen > windowEnd - windowStart + 1) {
					minLen = windowEnd - windowStart + 1;
					res = str.substring(windowStart, windowEnd + 1);
				}

				char left = str.charAt(windowStart);
				if (charFreqMap.containsKey(left)) {
					if (charFreqMap.get(left) == 0)
						matched--;
					charFreqMap.put(left, charFreqMap.get(left) + 1);
				}
				windowStart++;
			}
		}
		return res;
	}

	/*
	 * You are given an array of integers nums, there is a sliding window of size k
	 * which is moving from the very left of the array to the very right. You can
	 * only see the k numbers in the window. Each time the sliding window moves
	 * right by one position. Return the max sliding window.
	 * 
	 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
	 *	Explanation: 
	 *	Window position                Max
	 *	---------------               -----
	 *	[1  3  -1] -3  5  3  6  7       3
     *	 1 [3  -1  -3] 5  3  6  7       3
	 *	 1  3 [-1  -3  5] 3  6  7       5
	 *	 1  3  -1 [-3  5  3] 6  7       5
	 *	 1  3  -1  -3 [5  3  6] 7       6
	 *	 1  3  -1  -3  5 [3  6  7]      7
	 */
	public static int[] maxSlidingWindow(int[] nums, int k) {
		int[] res = new int[nums.length - k + 1];
		int wS = 0, s = 0;
		ArrayDeque<Integer> q = new ArrayDeque<>();

		for (int wE = 0; wE < nums.length; wE++) {
			// while the element at the first of the queue, i.e the index,
			// if it's out of the window, keep removing the element
			while (!q.isEmpty() && q.peekFirst() <= wE - k)
				q.pollFirst();
                System.out.println(q);
			// while the element at the last of the queue, i.e the index,
			// if it's less than equal to new element of the nums,
			// keep removing the element
			while (!q.isEmpty() && nums[q.peekLast()] <= nums[wE])
				q.pollLast();
            System.out.println(wE+" ------"+q);

			// insert the index of new element of nums
			q.offerLast(wE);
			
            System.out.println(q);

			// if wE greater than k-1, then add the first element
			// (index of the element in nums) of the queue to res
			if (wE >= k - 1)
				res[s++] = nums[q.peekFirst()];
            System.out.println(q);

		}
        System.out.println();

		return res;
	}

	public static void main(String args[]) {
		int[] prices = { 7, 1, 5, 3, 6, 4 };
		int[] nums = { 1, 3, -1, -3, 5, 3, 6, 7 };
		System.out.println(" Best time to buy & sell stocks " + bestTimetoBuySellStocks(prices));
		System.out.println(
				" Lenght of Longest Substring without repeating charachter " + lengthOfLongestSubstring("abcabcbb"));
		System.out.println(" Charachter replacement of String  " + characterReplacement("AABABBA", 1));
		System.out.println(" Check whether destination string is part of permutation of source String  "
				+ checkInclusion("ab", "eidbaooo"));
		System.out.println(" Minimum Window Substring  " + minWindow("ADOBECODEBANC", "ABC"));
		System.out.println(" Maximum Sliding Windows Window  ");
		Arrays.stream(maxSlidingWindow(nums, 3)).forEach(System.out::print);

	}

}
