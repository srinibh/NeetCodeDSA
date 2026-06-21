package com.neetcode.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinarySearchProblems {

	public static int search(int[] nums, int target) {
		int l = 0, r = nums.length - 1;

		while (l <= r) {
			int mid = l + (r - l) / 2;

			if (nums[mid] == target)
				return mid;
			else if (nums[mid] < target)
				l = mid + 1;
			else
				r = mid - 1;
		}

		return -1;
	}

	// worst case go through O(n+m) elements
	public static boolean searchMatrix1(int[][] matrix, int target) {
		int m = matrix.length, n = matrix[0].length;
		int row = 0, col = n - 1;
		while (row >= 0 && row < m && col >= 0 && col < n) {
			int val = matrix[row][col];
			if (val == target)
				return true;
			else if (val < target)
				row++;
			else
				col--;
		}
		return false;
	}

	// worst case it would go through O(log(n*m)) elements
	public static boolean searchMatrix2(int[][] matrix, int target) {
		int m = matrix.length, n = matrix[0].length;
		int left = 0, right = m * n - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			int val = matrix[mid / n][mid % n];
			if (val == target)
				return true;
			else if (val < target)
				left = mid + 1;
			else
				right = mid - 1;
		}
		return false;
	}

	public static int minEatingSpeed(int[] piles, int h) {
		int left = 1, right = piles[0];
		for (int pile : piles) {
			if (pile < left)
				left = pile;
			if (pile > right)
				right = pile;
		}
		int result = right;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (isSatisfy(piles, mid, h)) {
				result = Math.min(result, mid);
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return result;
	}

	private static boolean isSatisfy(int[] piles, int mid, int h) {
		int count = 0;
		for (int pile : piles) {
			count += (pile / mid);
			if (pile % mid != 0)
				count++;
		}
		return count <= h;
	}

	public static int rotatedSearch(int[] nums, int target) {
		int left = 0, right = nums.length - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;

			if (nums[mid] == target)
				return mid;
			// left sorted portion
			if (nums[left] <= nums[mid]) {
				if (target < nums[left] || target > nums[mid])
					left = mid + 1;
				else
					right = mid - 1;
			}
			// right sorted portion
			else {
				if (target < nums[mid] || target > nums[right])
					right = mid - 1;
				else
					left = mid + 1;
			}
		}
		return -1;
	}

	public static int findMin(int[] nums) {
		int n = nums.length;
		int start = 0, end = n - 1;
		int res = 0;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			int prev = (mid - 1 + n) % n, next = (mid + 1) % n;
			if (nums[mid] < nums[prev] && nums[mid] < nums[next])
				return nums[mid];
			else if (nums[end] <= nums[mid])
				start = mid + 1;
			else
				end = mid - 1;
		}
		return nums[res];
	}

	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		if (nums1.length > nums2.length)
			return findMedianSortedArrays(nums2, nums1);
		int total = nums1.length + nums2.length;
		int half = (total + 1) / 2;
		int l = 0, r = nums1.length;
		double result = 0d;
		while (l <= r) {
			int i = l + (r - l) / 2;
			int j = half - i;
			int nums1L = i > 0 ? nums1[i - 1] : Integer.MIN_VALUE;
			int nums1R = i < nums1.length ? nums1[i] : Integer.MAX_VALUE;
			int nums2L = j > 0 ? nums2[j - 1] : Integer.MIN_VALUE;
			int nums2R = j < nums2.length ? nums2[j] : Integer.MAX_VALUE;

			if (nums1L <= nums2R && nums2L <= nums1R) {
				if (total % 2 == 0) {
					return (Math.max(nums1L, nums2L) + Math.min(nums1R, nums2R)) / 2.0;
				} else {
					return Math.max(nums1L, nums2L);
				}
			} else if (nums1L > nums2R) {
				r = i - 1;
			} else {
				l = i + 1;
			}
		}
		return result;
	}

	class TimeMap {
		class Pair {
			int timestamp;
			String value;

			Pair(String value, int timestamp) {
				this.value = value;
				this.timestamp = timestamp;
			}
		}

		Map<String, List<Pair>> mp;

		public TimeMap() {
			mp = new HashMap<>();
		}

		public void set(String key, String value, int timestamp) {
			if (!mp.containsKey(key)) {
				mp.put(key, new ArrayList<>());
			}
			mp.get(key).add(new Pair(value, timestamp));
		}

		public String get(String key, int timestamp) {
			String ans = "";
			if (!mp.containsKey(key)) {
				return ans;
			}
			List<Pair> list = mp.get(key);

			int index = binarSearch(list, timestamp);
			if (index == -1) {
				return ans;
			}

			ans = list.get(index).value;
			return ans;
		}

		private int binarSearch(List<TimeMap.Pair> list, int timestamp) {
			int l = 0;
			int r = list.size() - 1;
			while (l <= r) {
				int mid = l + (r - l) / 2;
				if (list.get(mid).timestamp == timestamp) {
					return mid;
				} else if (list.get(mid).timestamp < timestamp) {
					l = mid + 1;
				} else {
					r = mid - 1;
				}
			}
			return r;
		}

	}

	/**
	 * Your TimeMap object will be instantiated and called as such: TimeMap obj =
	 * new TimeMap(); obj.set(key,value,timestamp); String param_2 =
	 * obj.get(key,timestamp);
	 */
	public static void main(String args[]) {
		int[] binSearch = { 2, 7, 9, 11, 13, 15 };
		System.out.println(" Binary Serach to serach an element " + search(binSearch, 13));
		int[][] matrix = { { 1, 3, 5, 7 }, { 10, 11, 16, 20 }, { 23, 30, 34, 60 } };
		int target = 16;
		System.out.println("Search Matrix " + searchMatrix2(matrix, target));
		System.out.println("Search Matrix " + searchMatrix1(matrix, target));
		int[] piles = { 30, 11, 23, 4, 20 };
		int h = 5;
		System.out.println("Minimum Eating piles " + minEatingSpeed(piles, h));
		int[] rotatedNums = { 4, 5, 6, 7, 0, 1, 2 };
		System.out.println("Rotated  Search " + rotatedSearch(rotatedNums, 0));
		int[] minnumrotate = { 4, 5, 6, 7, 0, 1, 2 };
		System.out.println("Minimum Rotation  " + findMin(minnumrotate));
		int[] num1 = { 1, 2 };
		int[] num2 = { 3, 4 };
		System.out.println("Rotated  Search " + findMedianSortedArrays(num1, num2));

		TimeMap timeMap = new BinarySearchProblems().new TimeMap();
		timeMap.set("foo", "bar", 1);
		System.out.println(timeMap.get("foo", 1));
		System.out.println(timeMap.get("foo", 3));
		timeMap.set("foo", "bar2", 4);
		System.out.println(timeMap.get("foo", 4));
		System.out.println(timeMap.get("foo", 5));

	}
}
