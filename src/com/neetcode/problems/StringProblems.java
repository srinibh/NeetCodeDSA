package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringProblems {

	/*
	 * Given two strings s and t, return true if t is an anagram of s, and false
	 * otherwise. An Anagram is a word or phrase formed by rearranging the letters
	 * of a different word or phrase, typically using all the original letters
	 * exactly once.
	 */
	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length())
			return false;

		int[] chars = new int[26];

		for (int i = 0; i < s.length(); i++) {
			chars[s.charAt(i) - 'a']++;
			chars[t.charAt(i) - 'a']--;
		}

		for (int n : chars) {
			if (n != 0)
				return false;
		}

		return true;
	}

	/*
	 * Given an array of strings strs, group the anagrams together. You can return
	 * the answer in any order.
	 */
	public static List<List<String>> groupAnagrams1(String[] strs) {
		List<List<String>> res = new ArrayList<>();
		Map<String, List<String>> mp = new HashMap<>();
		for (String str : strs) {
			char[] c = str.toCharArray();
			Arrays.sort(c);
			String r = new String(c);
			if (!mp.containsKey(r))
				mp.put(r, new ArrayList<String>());
			mp.get(r).add(str);
		}
		for (String key : mp.keySet()) {
			res.add(mp.get(key));
		}

		return res;
	}

	/*
	 * Given an array of strings strs, group the anagrams together. You can return
	 * the answer in any order.
	 */
	public static List<List<String>> groupAnagrams2(String[] strs) {
		List<List<String>> res = new ArrayList<>();
		Map<String, List<String>> mp = new HashMap<>();
		for (String str : strs) {
			char[] hash = new char[26];
			for (char ch : str.toCharArray())
				hash[ch - 'a']++;
			String r = new String(hash);
			if (!mp.containsKey(r))
				mp.put(r, new ArrayList<String>());
			mp.get(r).add(str);
		}
		for (String key : mp.keySet()) {
			res.add(mp.get(key));
		}

		return res;
	}

	/*
	 * Description Design an algorithm to encode a list of strings to a string. The
	 * encoded string is then sent over the network and is decoded back to the
	 * original list of strings.
	 */
	public static String encode(List<String> strs) {

		StringBuilder sb = new StringBuilder();
		char seperator = '`';
		for (String str : strs) {
			sb.append(str.length());
			sb.append(seperator);
			sb.append(str);
		}
		return sb.toString();
	}

	public static List<String> decode(String str) {

		List<String> strs = new ArrayList<>();
		int i = 0;
		while (i < str.length()) {
			int j = i;
			while (str.charAt(j) != '`')
				j++;
			int len = Integer.parseInt(str.substring(i, j));
			strs.add(str.substring(j + 1, j + 1 + len));
			i = j + 1 + len;
		}
		return strs;
	}

	/*
	 * A phrase is a palindrome if, after converting all uppercase letters into
	 * lowercase letters and removing all non-alphanumeric characters, it reads the
	 * same forward and backward. Alphanumeric characters include letters and
	 * numbers.
	 */
	public static boolean isPalindrome(String s) {
		int i = 0, j = s.length() - 1;
		while (i < j) {
			while (i < j && !Character.isLetterOrDigit(s.charAt(i)))
				i++;
			while (i < j && !Character.isLetterOrDigit(s.charAt(j)))
				j--;
			if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j)))
				return false;
			i++;
			j--;
		}

		return true;
	}

	public static void main(String args[]) {
		String s = "anagram";
		String t = "nagaram";
		System.out.println(isAnagram(s, t));
		String[] input = { "eat", "tea", "tan", "ate", "nat", "bat" };
		System.out.println(groupAnagrams1(input));
		System.out.println(groupAnagrams2(input));
		String[] encodeString1 = { "lint", "code", "love", "you" };
		System.out.println(encode(Arrays.asList(encodeString1)));
		System.out.println(decode(encode(Arrays.asList(encodeString1))));
		String chkPalin = "A man, a plan, a canal: Panama";
		System.out.println("Check if Palindrome " + isPalindrome(chkPalin));

	}

}
