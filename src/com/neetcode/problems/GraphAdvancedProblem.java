package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class GraphAdvancedProblem {

	static class Edge {
		int[] x;
		int[] y;
		int cost;

		Edge(int[] x, int[] y) {
			this.x = x;
			this.y = y;
			this.cost = Math.abs(x[0] - y[0]) + Math.abs(x[1] - y[1]);
		}
	}

	public static int minCostConnectPoints(int[][] points) {
		// MST
		// prims
		int cost = 0;
		Set<int[]> visited = new HashSet<>(); // to store the visited vertices
		int numOfVertices = points.length;
		PriorityQueue<Edge> q = new PriorityQueue<>((a, b) -> a.cost - b.cost); // to store the edges based on cost
		visited.add(points[0]);
		Queue<int[]> source = new LinkedList<>(); // sources to determine which node to relax
		source.add(points[0]);
		while (visited.size() != numOfVertices) { // till all nodes are visited or n-1 edges are added
			int[] src = source.poll();
			putEdges(src, visited, points, q); // put hte edges to the queue
			while (!q.isEmpty()) {
				Edge edge = q.poll(); // get the best edge
				if (!detectCycle(src, edge.y, visited)) { // if cycle is not form after adding the edge
					cost += edge.cost;
					visited.add(edge.y);
					source.add(edge.y);
					break; // so that it doesn't look to add the other edges right away
				}
			}
		}
		return cost;
	}

	private static void putEdges(int[] point, Set<int[]> set, int[][] points, PriorityQueue<Edge> q) {
		for (int[] pnt : points) {
			if (pnt != point && !set.contains(pnt))
				q.offer(new Edge(point, pnt));
		}
	}

	// to detect cycle
	private static boolean detectCycle(int[] a, int[] b, Set<int[]> set) {
		return set.contains(a) && set.contains(b);
	}

	public static int networkDelayTime(int[][] times, int n, int k) {
		Map<Integer, List<int[]>> graph = new HashMap<>();
		for (int[] time : times) {
			List<int[]> neighbours = graph.getOrDefault(time[0], new ArrayList<>());
			neighbours.add(new int[] { time[1], time[2] });
			graph.put(time[0], neighbours);
		}
		int[] cost = new int[n + 1];
		for (int i = 1; i <= n; i++)
			cost[i] = 100005;
		cost[k] = 0;
		Queue<Integer> q = new LinkedList<>();
		q.offer(k);
		while (!q.isEmpty()) {
			int vertex = q.poll();
			if (!graph.containsKey(vertex))
				continue;

			List<int[]> neighbours = graph.get(vertex);
			for (int[] nei : neighbours) {
				int newCost = cost[vertex] + nei[1];
				if (newCost < cost[nei[0]]) {
					cost[nei[0]] = newCost;
					q.offer(nei[0]);
				}
			}
		}
		int ans = -1;
		for (int i = 1; i <= n; i++) {
			if (cost[i] >= 100005)
				return -1;
			if (cost[i] > ans)
				ans = cost[i];
		}
		return ans;
	}

	public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
		int[] cost = new int[n];
		Arrays.fill(cost, Integer.MAX_VALUE);
		int[] tmp = new int[n];
		Arrays.fill(tmp, Integer.MAX_VALUE);
		cost[src] = 0;
		tmp[src] = 0;
		while (k >= 0) {
			for (int[] flight : flights) {
				if (cost[flight[0]] != Integer.MAX_VALUE) {
					int newCost = cost[flight[0]] + flight[2];
					if (newCost < tmp[flight[1]])
						tmp[flight[1]] = newCost;
				}
			}
			cost = Arrays.copyOfRange(tmp, 0, n);
			k--;
		}
		return cost[dst] == Integer.MAX_VALUE ? -1 : cost[dst];
	}

	public static void main(String args[]) {
		int[][] points = { { 3, 12 }, { -2, 5 }, { -4, 1 } };
		System.out.println("Minimum Cost Connection Points " + minCostConnectPoints(points));
		int[][] times = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
		int n = 4;
		int k = 2;
		System.out.println("Network Delay Time " + networkDelayTime(times, n, k));
		int n1 = 3;
		int[][] flights = { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
		int src = 0;
		int dst = 2;
		int k1 = 0;
		System.out.println("Cheapest flight with Kstops " + findCheapestPrice(n1, flights, src, dst, k1));

	}

}
