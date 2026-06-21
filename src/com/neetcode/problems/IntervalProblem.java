package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IntervalProblem {

	public static int[][] insert(int[][] intervals, int[] newInterval) {
		if (intervals.length < 1)
			return new int[][] { newInterval };

		List<int[]> mergedList = new ArrayList<>();
		int index = 0;
		while (index < intervals.length && intervals[index][1] < newInterval[0])
			mergedList.add(intervals[index++]);

		while (index < intervals.length && intervals[index][0] <= newInterval[1]) {
			newInterval[0] = Math.min(newInterval[0], intervals[index][0]);
			newInterval[1] = Math.max(newInterval[1], intervals[index][1]);
			index++;
		}
		mergedList.add(newInterval);

		while (index < intervals.length)
			mergedList.add(intervals[index++]);

		return mergedList.toArray(new int[mergedList.size()][]);
	}

	public static int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		List<int[]> arr = new ArrayList<>();
		arr.add(intervals[0]);
		int start = intervals[0][0];
		int end = intervals[0][1];
		for (int i = 1; i < intervals.length; i++) {
			int[] curr = intervals[i];
			int[] prev = arr.get(arr.size() - 1);
			if (curr[0] <= prev[1]) {
				arr.remove(arr.size() - 1);
				start = Math.min(curr[0], prev[0]);
				end = Math.max(curr[1], prev[1]);
				arr.add(new int[] { start, end });
			} else {
				arr.add(curr);
			}
		}
		return arr.toArray(new int[arr.size()][]);
	}

	public static int eraseOverlapIntervals(int[][] intervals) {
		if (intervals.length <= 1)
			return 0;
		Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

		int count = 0;
		int[] prev = intervals[0];
		for (int i = 1; i < intervals.length; i++) {
			int[] curr = intervals[i];
			if (prev[1] > curr[0])
				count++;
			else
				prev = intervals[i];
		}

		return count;
	}

	public static class Interval {
		int start, end;

		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	public boolean canAttendMeetings(List<Interval> intervals) {

		if (intervals.size() == 0 || intervals.size() == 1)
			return true;
		Collections.sort(intervals, (a, b) -> a.end - b.end);
		Interval next = intervals.get(intervals.size() - 1);
		for (int i = intervals.size() - 2; i >= 0; i--) {
			Interval current = intervals.get(i);

			if (current.end > next.start && current.end <= next.end)
				return false;
			next = current;
		}
		return true;
	}

	public static int minMeetingRooms(List<Interval> intervals) {
		// Check for the base case. If there are no intervals, return 0
		if (intervals.size() == 0) {
			return 0;
		}

		Integer[] start = new Integer[intervals.size()];
		Integer[] end = new Integer[intervals.size()];

		for (int i = 0; i < intervals.size(); i++) {
			start[i] = intervals.get(i).start;
			end[i] = intervals.get(i).end;
		}

		// Sort the intervals by end time
		Arrays.sort(end);

		// Sort the intervals by start time
		Arrays.sort(start);

		// The two pointers in the algorithm: e_ptr and s_ptr.
		int startPointer = 0, endPointer = 0;

		// Variables to keep track of maximum number of rooms used.
		int usedRooms = 0, count = 0;

		// Iterate over intervals.
		while (startPointer < intervals.size()) {

			// If there is a meeting that has started before the time
			// the meeting at `end_pointer` starts means overlapping
			if (start[startPointer] < end[endPointer]) {
				count += 1;
				startPointer += 1;
			}
			// a room is free now
			else {
				count -= 1;
				endPointer += 1;
			}
			usedRooms = Math.max(usedRooms, count);
		}

		return usedRooms;
	}

	public static void main(String args[]) {
		int[][] intervals = { { 1, 3 }, { 6, 9 } };
		int[] newInterval = { 2, 5 };
		print(insert(intervals, newInterval));
		System.out.println();
		int[][] mergeIntervals = { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } };
		print(merge(mergeIntervals));
		int[][] nonOverlapIntervals = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 3 } };
		System.out.println();
		System.out.println(eraseOverlapIntervals(nonOverlapIntervals));

	}

	private static void print(int[][] matrix) {
		int row = matrix.length;
		int col = matrix[0].length;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print("  " + matrix[i][j]);
			}
			// System.out.println();
		}

	}

}
