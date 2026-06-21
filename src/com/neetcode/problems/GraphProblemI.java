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

public class GraphProblemI {

	public static int numIslands(char[][] grid) {
		boolean[][] mark = new boolean[grid.length][grid[0].length];
		int res = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (!mark[i][j] && grid[i][j] == '1') {
					res++;
					dfs(grid, i, j, mark);
				}
			}
		}
		return res;
	}

	private static void dfs(char[][] grid, int i, int j, boolean[][] mark) {
		if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || mark[i][j] || grid[i][j] == '0')
			return;
		mark[i][j] = true;
		dfs(grid, i + 1, j, mark);
		dfs(grid, i, j - 1, mark);
		dfs(grid, i - 1, j, mark);
		dfs(grid, i, j + 1, mark);
	}

	// Definition for a Node.
	static class Node {
		public int val;
		public List<Node> neighbors;

		public Node() {
			val = 0;
			neighbors = new ArrayList<Node>();
		}

		public Node(int _val) {
			val = _val;
			neighbors = new ArrayList<Node>();
		}

		public Node(int _val, ArrayList<Node> _neighbors) {
			val = _val;
			neighbors = _neighbors;
		}
	}

	public static Node cloneGraph(Node node) {
		if (node == null)
			return null;
		Map<Integer, Node> mp = new HashMap<>();
		Queue<Node> q = new LinkedList<>();
		Set<Node> set = new HashSet<>();
		q.offer(node);

		while (!q.isEmpty()) {
			Node curr = q.poll();
			set.add(curr);

			List<Node> nei = curr.neighbors;
			ArrayList<Node> newNei = new ArrayList<>();
			for (Node n : nei) {
				if (!set.contains(n))
					q.offer(n);
				if (!mp.containsKey(n.val)) {
					Node newN = new Node(n.val);
					mp.put(newN.val, newN);
					newNei.add(newN);
				} else {
					Node newN = mp.get(n.val);
					newNei.add(newN);
				}
			}
			if (!mp.containsKey(curr.val)) {
				Node newNode = new Node(curr.val, newNei);
				mp.put(curr.val, newNode);
			} else {
				Node newNode = mp.get(curr.val);
				newNode.neighbors = newNei;
				mp.put(curr.val, newNode);
			}
		}

		return mp.get(node.val);
	}

	public static int maxAreaOfIsland(int[][] grid) {
		boolean[][] mark = new boolean[grid.length][grid[0].length];
		int res = 0;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (!mark[i][j] && grid[i][j] == 1) {
					res = Math.max(res, dfs(grid, i, j, mark));
				}
			}
		}
		return res;
	}

	private static int dfs(int[][] grid, int i, int j, boolean[][] mark) {
		if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || mark[i][j] || grid[i][j] == 0) {
			return 0;
		}
		mark[i][j] = true;
		return (1 + dfs(grid, i + 1, j, mark) + dfs(grid, i, j - 1, mark) + dfs(grid, i - 1, j, mark)
				+ dfs(grid, i, j + 1, mark));
	}

	public static List<List<Integer>> pacificAtlantic(int[][] heights) {
		List<List<Integer>> res = new ArrayList<>();
		Set<String> pacific = new HashSet<>();
		Set<String> atlantic = new HashSet<>();
		int rows = heights.length, cols = heights[0].length;
		for (int i = 0; i < cols; i++) {
			dfs(heights, 0, i, pacific, heights[0][i]);
			dfs(heights, rows - 1, i, atlantic, heights[rows - 1][i]);
		}
		for (int i = 0; i < rows; i++) {
			dfs(heights, i, 0, pacific, heights[i][0]);
			dfs(heights, i, cols - 1, atlantic, heights[i][cols - 1]);
		}
		pacific.retainAll(atlantic);
		for (String s : pacific) {
			String[] arr = s.split(",");
			List<Integer> a = new ArrayList<>();
			a.add(Integer.parseInt(arr[0]));
			a.add(Integer.parseInt(arr[1]));
			res.add(a);
		}
		return res;
	}

	private static void dfs(int[][] grid, int i, int j, Set<String> visited, int prev) {
		if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] < prev
				|| visited.contains(i + "," + j))
			return;

		visited.add(i + "," + j);
		dfs(grid, i, j - 1, visited, grid[i][j]);
		dfs(grid, i, j + 1, visited, grid[i][j]);
		dfs(grid, i - 1, j, visited, grid[i][j]);
		dfs(grid, i + 1, j, visited, grid[i][j]);
	}

	public static void solve(char[][] board) {
		int[][] mark = new int[board.length][board[0].length];
		for (int j = 0; j < board[0].length; j++) {
			if (board[0][j] == 'O')
				dfs(board, 0, j, mark);
		}
		for (int j = 0; j < board[0].length; j++) {
			if (board[board.length - 1][j] == 'O')
				dfs(board, board.length - 1, j, mark);
		}
		for (int j = 0; j < board.length; j++) {
			if (board[j][0] == 'O')
				dfs(board, j, 0, mark);
		}
		for (int j = 0; j < board.length; j++) {
			if (board[j][board[0].length - 1] == 'O')
				dfs(board, j, board[0].length - 1, mark);
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (mark[i][j] == 0 && board[i][j] == 'O')
					board[i][j] = 'X';
			}
		}
	}

	private static void dfs(char[][] grid, int i, int j, int[][] mark) {
		if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || mark[i][j] == 1 || grid[i][j] == 'X')
			return;
		mark[i][j] = 1;
		dfs(grid, i, j - 1, mark);
		dfs(grid, i, j + 1, mark);
		dfs(grid, i - 1, j, mark);
		dfs(grid, i + 1, j, mark);
	}

	public static int orangesRotting(int[][] grid) {
		int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
		Queue<int[]> q = new LinkedList<>();
		int n = grid.length, m = grid[0].length;
		int countFresh = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 1)
					countFresh++;
				if (grid[i][j] == 2)
					q.offer(new int[] { i, j });
			}
		}

		if (countFresh == 0)
			return 0;
		int time = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int[] currPos = q.poll();
				int currI = currPos[0], currJ = currPos[1];
				for (int[] dir : dirs) {
					if (currI + dir[0] < 0 || currJ + dir[1] < 0 || currI + dir[0] >= n || currJ + dir[1] >= m
							|| grid[currI + dir[0]][currJ + dir[1]] != 1)
						continue;
					if (grid[currI + dir[0]][currJ + dir[1]] == 1) {
						grid[currI + dir[0]][currJ + dir[1]] = 2;
						q.offer(new int[] { currI + dir[0], currJ + dir[1] });
						countFresh--;
					}
				}

			}
			if (!q.isEmpty())
				time++;

		}
		return countFresh != 0 ? -1 : time;
	}

	public static void main(String args[]) {
		char[][] grid = { { '1', '1', '0', '0', '0' }, { '1', '1', '0', '0', '0' }, { '0', '0', '1', '0', '0' },
				{ '0', '0', '0', '1', '1' } };
		System.out.println("Number of Islands " + numIslands(grid));
		Node node1 = new Node(0);
		Node node2 = new Node(1);
		Node node3 = new Node(2);
		Node node4 = new Node(3);

		node1.neighbors.addAll(new ArrayList<>(Arrays.asList(node2, node3)));
		node2.neighbors.addAll(new ArrayList<>(Arrays.asList(node1, node3)));
		node3.neighbors.addAll(new ArrayList<>(Arrays.asList(node1, node2, node4)));
		node4.neighbors.addAll(new ArrayList<>(Arrays.asList(node3)));
		Node cloneNode = cloneGraph(node1);
		List<Node> neighbourList = cloneNode.neighbors;
		neighbourList.forEach(node8 -> node8.neighbors.forEach(node10 -> System.out.print(node10.val)));
		System.out.println();
		int[][] grid1 = { { 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0 },
				{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 } };
		System.out.println(" Max Area of Islands " + maxAreaOfIsland(grid1));
		int[][] heights = { { 1, 2, 2, 3, 5 }, { 3, 2, 3, 4, 4 }, { 2, 4, 5, 3, 1 }, { 6, 7, 1, 4, 5 },
				{ 5, 1, 1, 2, 4 } };

		System.out.println(pacificAtlantic(heights));

		char[][] board = { { 'X', 'X', 'X', 'X' }, { 'X', 'O', 'O', 'X' }, { 'X', 'X', 'O', 'X' },
				{ 'X', 'O', 'X', 'X' } };
		solve(board);
		print(board);
		int[][] grid2 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
		System.out.println(orangesRotting(grid2));

	}

	private static void print(char[][] matrix) {
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
