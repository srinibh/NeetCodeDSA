package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphProblemII {

	public static void wallsAndGates(int[][] grid) {
		int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		Queue<int[]> q = new LinkedList<>();
		int n = grid.length, m = grid[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 0)
					q.offer(new int[] { i, j });
			}
		}

		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int[] currPos = q.poll();
				int currI = currPos[0], currJ = currPos[1];
				int val = grid[currI][currJ];
				for (int[] dir : dirs) {
					if (currI + dir[0] < 0 || currJ + dir[1] < 0 || currI + dir[0] >= n || currJ + dir[1] >= m
							|| grid[currI + dir[0]][currJ + dir[1]] == -1)
						continue;
					else if (grid[currI + dir[0]][currJ + dir[1]] == 2147483647) {
						grid[currI + dir[0]][currJ + dir[1]] = 1 + val;
						q.offer(new int[] { currI + dir[0], currJ + dir[1] });
					} else {
						grid[currI + dir[0]][currJ + dir[1]] = Math.min(grid[currI + dir[0]][currJ + dir[1]], 1 + val);
					}
				}

			}
		}
	}

	public static boolean canFinish(int numCourses, int[][] prerequisites) {
		// List<Integer> result = new ArrayList<>();
		int result = 0;

		// 1. Initialize the graph
		Map<Integer, List<Integer>> graph = new HashMap<>();
		Map<Integer, Integer> inDegree = new HashMap<>();

		for (int i = 0; i < numCourses; i++) {
			graph.put(i, new ArrayList<>());
			inDegree.put(i, 0);
		}

		// 2. Build the graph
		for (int i = 0; i < prerequisites.length; i++) {
			int child = prerequisites[i][0], parent = prerequisites[i][1];
			graph.get(parent).add(child);
			inDegree.put(child, inDegree.get(child) + 1);
		}

		// 3. Add all the sources(i.e, vertices with in-degree 0) to a queue
		Queue<Integer> sources = new LinkedList<>();
		for (Map.Entry<Integer, Integer> entry : inDegree.entrySet())
			if (entry.getValue() == 0)
				sources.offer(entry.getKey());

		// 4. For each source, add it to the result, subtract 1 from all of it's
		// children's in-degree
		// & add if any child has in-degree 0, add it to sources queue
		while (!sources.isEmpty()) {
			int vertex = sources.poll();
			result++;
			for (int child : graph.get(vertex)) {
				inDegree.put(child, inDegree.get(child) - 1);
				if (inDegree.get(child) == 0)
					sources.offer(child);
			}
		}

		// 5. If size of result equal to numCourses then return true else return false
		return result == numCourses;
	}

	public static int[] findOrder(int numCourses, int[][] prerequisites) {
		List<Integer> result = new ArrayList<>();

		// 1. Initialize the graph
		Map<Integer, List<Integer>> graph = new HashMap<>();
		Map<Integer, Integer> inDegree = new HashMap<>();

		for (int i = 0; i < numCourses; i++) {
			graph.put(i, new ArrayList<>());
			inDegree.put(i, 0);
		}

		// 2. Build the graph
		for (int i = 0; i < prerequisites.length; i++) {
			int child = prerequisites[i][0], parent = prerequisites[i][1];
			graph.get(parent).add(child);
			inDegree.put(child, inDegree.get(child) + 1);
		}

		// 3. Add all the sources(i.e, vertices with in-degree 0) to a queue
		Queue<Integer> sources = new LinkedList<>();
		for (Map.Entry<Integer, Integer> entry : inDegree.entrySet())
			if (entry.getValue() == 0)
				sources.offer(entry.getKey());

		// 4. For each source, add it to the result, subtract 1 from all of it's
		// children's in-degree
		// & add if any child has in-degree 0, add it to sources queue
		while (!sources.isEmpty()) {
			int vertex = sources.poll();
			result.add(vertex);
			for (int child : graph.get(vertex)) {
				inDegree.put(child, inDegree.get(child) - 1);
				if (inDegree.get(child) == 0)
					sources.offer(child);
			}
		}

		if (result.size() != numCourses)
			return new int[] {};
		return result.stream().mapToInt(i -> i).toArray();
	}

	static int[] parent;
	static int[] rank;

	public static int[] findRedundantConnection(int[][] edges) {
		int n = edges.length;
		init(n);
		int[] res = new int[2];
		for (int[] edge : edges) {
			if (!union(edge[0], edge[1]))
				res = new int[] { edge[0], edge[1] };
		}
		return res;
	}

	private static void init(int n) {
		parent = new int[n + 1];
		rank = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
	}

	private static int find(int val) {
		while (val != parent[val]) {
			parent[val] = parent[parent[val]];
			val = parent[val];
		}
		return parent[val];
	}

	private static boolean union(int x, int y) {
		int p1 = find(x);
		int p2 = find(y);
		if (p1 == p2)
			return false;
		if (rank[p1] > rank[p2]) {
			parent[p2] = p1;
			rank[p1] += rank[p2];
		} else {
			parent[p1] = p2;
			rank[p2] += rank[p1];
		}
		return true;
	}

	public static int countComponents(int n, int[][] edges) {
		HashMap<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
		boolean[] visited = new boolean[n];

		int count = 0;
		// Step - 1 Build the graph
		for (int i = 0; i < n; i++) {
			graph.put(i, new ArrayList<Integer>());
		}

		for (int i = 0; i < edges.length; i++) {
			// Make Undirected Graph
			graph.get(edges[i][0]).add(edges[i][1]);
			graph.get(edges[i][1]).add(edges[i][0]);
		}

		// Step -2 run algorithm
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				count++;
				dfs(i, graph, visited);
			}
		}
		return count;

	}

	private static void dfs(int at, HashMap<Integer, List<Integer>> graph, boolean[] visited) {
		visited[at] = true;
		for (Integer child : graph.get(at)) {
			if (!visited[child]) {
				dfs(child, graph, visited);
			}
		}
	}

	public static boolean validTree(int n, int[][] edges) {
		// write your code here
		if (edges.length != n - 1)
			return false;
		if (n == 0 || n == 1)
			return true;
		Set<Integer> mark = new HashSet<>();
		Map<Integer, List<Integer>> graph = new HashMap<>();

		for (int i = 0; i < edges.length; i++) {
			int n1 = edges[i][0];
			int n2 = edges[i][1];
			List<Integer> arr1 = graph.getOrDefault(n1, new ArrayList<>());
			arr1.add(n2);
			List<Integer> arr2 = graph.getOrDefault(n2, new ArrayList<>());
			arr2.add(n1);
			graph.put(n1, arr1);
			graph.put(n2, arr2);
		}
		dfs(graph, 0, mark);
		return mark.size() == n;
	}

	private static void dfs(Map<Integer, List<Integer>> graph, int i, Set<Integer> mark) {
		mark.add(i);
		for (int node : graph.get(i)) {
			if (!mark.contains(node))
				dfs(graph, node, mark);
		}
	}

	public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Map<String, List<String>> graph = new HashMap<>();
		//wordList.add(0, beginWord);
		for (int i = 0; i < wordList.size(); i++) {
			for (int j = i + 1; j < wordList.size(); j++) {
				String s1 = wordList.get(i);
				String s2 = wordList.get(j);
				if (differByOne(s1, s2)) {
					List<String> arr1 = graph.getOrDefault(s1, new ArrayList<>());
					arr1.add(s2);
					List<String> arr2 = graph.getOrDefault(s2, new ArrayList<>());
					arr2.add(s1);
					graph.put(s1, arr1);
					graph.put(s2, arr2);
				}
			}
		}

		if (!graph.containsKey(endWord))
			return 0;

		Queue<String> q = new LinkedList<>();

		q.offer(beginWord);

		if (q.size() == 0)
			return 0;
		Set<String> visited = new HashSet<>();
		int pathSize = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			pathSize++;
			for (int i = 0; i < size; i++) {
				String s1 = q.poll();
				visited.add(s1);
				if (s1.equals(endWord))
					return pathSize;
				for (String s : graph.get(s1)) {
					if (!visited.contains(s))
						q.offer(s);
				}
			}
		}

		return 0;
	}

	private static boolean differByOne(String word1, String word2) {
		if (word1.length() != word2.length())
			return false;
		int n = word1.length();
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				count++;
				if (count > 1)
					return false;
			}
		}
		return count == 1;
	}

	public static void main(String args[]) {
		int[][] wallGate = { { 2147483647, -1, 0, 2147483647 }, { 2147483647, 2147483647, 2147483647, -1 },
				{ 2147483647, -1, 2147483647, -1 }, { 0, -1, 2147483647, 2147483647 } };
		wallsAndGates(wallGate);
		print(wallGate);
		int[][] edges = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 4 }, { 1, 5 } };
		int[] conns = findRedundantConnection(edges);
		for (int i = 0; i < conns.length; i++) {
			System.out.println(conns[i]);
		}
		int n = 5;
		int[][] edges1 = { { 0, 1 }, { 1, 2 }, { 3, 4 } };
		System.out.println(countComponents(n, edges1));
		int[][] edges2 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 } };
		System.out.println(validTree(n, edges2));
		String beginWord = "hit";
		String endWord = "cog";
		String[] wordList = { "hit","hot", "dot", "dog", "lot", "log", "cog" };
		List<String> fixedSizeList = Arrays.asList(wordList);
		System.out.println(ladderLength(beginWord, endWord, fixedSizeList));

	}
	
	private static void print(int[][] matrix) {
		int row = matrix.length;
		int col = matrix[0].length;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print("  " + matrix[i][j]);
			}
			System.out.println();
		}

	}


}
