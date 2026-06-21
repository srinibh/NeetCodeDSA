package com.neetcode.problems;

import java.util.PriorityQueue;

public class HeapOrPriorityQueueProblem {

	static class KthLargest {
		PriorityQueue<Integer> pq;
		static int k;

		public KthLargest(int k, int[] nums) {
			this.k = k;
			pq = new PriorityQueue<>();
			for (int num : nums)
				pq.offer(num);

			while (pq.size() > k)
				pq.poll();
		}

		public int add(int val) {
			pq.offer(val);
			if (pq.size() > k)
				pq.poll();

			return pq.peek();
		}
	}

	public static int lastStoneWeight(int[] stones) {
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // max heap, Collections.reverseOrder()
		System.out.println(pq.isEmpty());
		for (int stone : stones)
			pq.offer(stone);
		while (pq.size() > 1) {
			int x = pq.poll();
			int y = pq.poll();
			System.out.println(x + "  " + y);
			if (x != y) {
				pq.offer(x - y); // abs not rqd as x would always be greater than equal to y
			}
			System.out.println(pq);
		}

		return pq.isEmpty() ? 0 : pq.peek();
	}

	public static int[][] kClosest(int[][] points, int k) {
		int[][] res = new int[k][2];
		PriorityQueue<Point> pq = new PriorityQueue<Point>((a, b) -> new Double(b.dist).compareTo(new Double(a.dist)));

		for (int[] point : points) {
			pq.offer(new Point(point[0], point[1]));
			if (pq.size() > k)
				pq.poll();
		}
		int ind = 0;
		while (!pq.isEmpty()) {
			Point p = pq.poll();
			res[ind][0] = p.x;
			res[ind][1] = p.y;
			ind++;
		}
		return res;
	}

	public static int[][] kClosest2(int[][] points, int k) {
		int[][] res = new int[k][2];
		PriorityQueue<int[]> pq = new PriorityQueue<int[]>(
				(a, b) -> (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1]));

		for (int[] point : points) {
			pq.offer(point);
			if (pq.size() > k)
				pq.poll();
		}
		int ind = 0;
		while (!pq.isEmpty()) {
			int[] p = pq.poll();
			res[ind][0] = p[0];
			res[ind][1] = p[1];
			ind++;
		}
		return res;
	}

	static class Point {
		int x;
		int y;

		double dist;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
			dist = Math.pow(x * x + y * y, 0.5);
		}
	}

	public static void main(String args[]) {
		int[] arr = { 4, 5, 8, 2 };
		KthLargest kthLargest = new KthLargest(3, arr);
		kthLargest.add(3); // return 4
		kthLargest.add(5); // return 5
		kthLargest.add(10); // return 5
		kthLargest.add(9); // return 8
		kthLargest.add(4); // return 8
		int[] stones = { 2, 7, 4, 1, 8, 1 };
		System.out.println(" Last stone weight " + lastStoneWeight(stones));
		int[][] points = { { 3, 3 }, { 5, -1 }, { -2, 4 } };
		int k = 2;
		System.out.println("K closest points ");
		int[][] sol1 = kClosest(points, k);
		for (int i = 0; i < sol1.length; i++) {
			for (int j = 0; j < sol1.length; j++) {
				System.out.print(sol1[i][j] + "  ");
			}
			System.out.println();
		}
		int[][] sol2 = kClosest2(points, k);
		for (int i = 0; i < sol2.length; i++) {
			for (int j = 0; j < sol2.length; j++) {
				System.out.print(sol2[i][j] + "  ");
			}
			System.out.println();

		}

	}

}
