package com.neetcode.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class MiscDSAProblem {

	static class UnionFind {
		int[] root;
		int[] rank;

		UnionFind(int size) {
			root = new int[size];
			rank = new int[size];

			for (int i = 0; i < size; i++) {
				root[i] = i;
				rank[i] = 0;
			}
		}

		public int find(int x) {
			if (x == root[x])
				return x;
			return root[x] = find(root[x]);
		}

		public void union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);

			if (rootX != rootY) {
				if (rank[rootX] > rank[rootY])
					root[rootY] = rootX;
				else if (rank[rootX] < rank[rootY])
					root[rootX] = rootY;
				else {
					root[rootY] = rootX;
					rank[rootX]++;
				}
			}
		}

		public boolean isConnected(int x, int y) {
			return find(x) == find(y);
		}
	}

	static class Node {
		int dist;
		int i;
		int j;
		boolean done;

		Node(int d, int ii, int jj, boolean done) {
			dist = d;
			i = ii;
			j = jj;
			done = done;
		}
	}

	public static int minimumObstacles(int[][] grid) {
		int[][] dirs = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
		int n = grid.length, m = grid[0].length;
		PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
		Map<String, Node> mp = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				String key = "" + i + "," + j;
				Node node = null;
				if (i == 0 && j == 0)
					node = new Node(0, i, j, false);
				else
					node = new Node(100001, i, j, false);
				mp.put(key, node);
			}
		}

		pq.offer(mp.get("0,0"));
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			if (node.done == false) {
				node.done = true;
				int i = node.i;
				int j = node.j;
				for (int[] dir : dirs) {
					int new_i = i + dir[0];
					int new_j = j + dir[1];
					if (new_i >= 0 && new_i < n && new_j >= 0 && new_j < m) {
						String neiK = "" + new_i + "," + new_j;
						Node nei = mp.get(neiK);
						int cost = node.dist + grid[new_i][new_j];
						if (cost < nei.dist) {
							nei.dist = cost;
							pq.offer(mp.get(neiK));
						}
					}
				}
			}

		}
		return mp.get("" + (n - 1) + "," + (m - 1)).dist;
	}

	public static class Graph {
		public Map<Integer, List<int[]>> adjList;

		public int nvertices;

		public int nedges;

		public int[] degree;

		public boolean directed;

		public Graph(int v) {
			degree = new int[v];
			nvertices = v;
			nedges = 0;
			adjList = new HashMap<>();
			for (int i = 0; i < v; i++) {
				adjList.put(i, new ArrayList<>());
			}
		}

		public Graph(int v, boolean directed) {
			this(v);
			if (directed)
				this.directed = true;
		}

		public void insertEdge(int x, int y, int weight, boolean directed) {
			List<int[]> list = adjList.get(x);
			int[] tmp = new int[2];
			tmp[0] = y;
			tmp[1] = weight;
			list.add(tmp);
			adjList.put(x, list);
			if (!directed) {
				List<int[]> list2 = adjList.get(y);
				int[] tmp2 = new int[2];
				tmp2[0] = x;
				tmp2[1] = weight;
				list2.add(tmp2);
				adjList.put(y, list2);
			}
		}

		public void insertEdge(int x, int y, boolean directed) {
			insertEdge(x, y, 0, directed);
		}
	}

	public static class BfsGraph {
		boolean[] processed;
		boolean[] discovered;
		int[] parent;

		Graph g;

		public BfsGraph(Graph g) {
			this.g = g;
			processed = new boolean[g.nvertices];
			discovered = new boolean[g.nvertices];
			parent = new int[g.nvertices];
			for (int i = 0; i < g.nvertices; i++)
				parent[i] = -1;
		}

		public void bfs(int start) {

			Queue<Integer> q = new LinkedList<>();
			int v;
			int y;

			q.offer(start);
			discovered[start] = true;

			while (!q.isEmpty()) {
				v = q.poll();
				// process vertex early - v
				// System.out.println(v);
				processed[v] = true;
				List<int[]> neighbors = g.adjList.get(v);
				for (int[] neighbor : neighbors) {
					y = neighbor[0];
					if (!processed[y] || g.directed) {
						// process edge(v, y)
						// System.out.println(v + "-" + y);
					}
					if (!discovered[y]) {
						q.offer(y);
						discovered[y] = true;
						parent[y] = v;
					}
				}
				// process vertex late - v
			}
		}

		public void processEdge(int x, int y) {
			g.nedges++;
		}

		public void findPath(int start, int end) {
			if (start == end || (end == -1)) {
				// System.out.println(start);
			} else {
				findPath(start, parent[end]);
			}
		}
	}

	public static class BellmanFord {
		// Graph is Created Using Edge Class
		static class Edge {
			int source, destination, weight;

			Edge() {
				source = destination = weight = 0;
			}
		}

		int V, E;
		Edge edge[];

		// Constructor to initialize the graph
		BellmanFord(int v, int e) {
			V = v;
			E = e;
			edge = new Edge[e];
			for (int i = 0; i < e; ++i)
				edge[i] = new Edge();
		}

		// Bellman-Ford Algorithm to find shortest paths from source to all vertices
		void BellmanFordAlgo(BellmanFord graph, int source) {
			int V = graph.V, E = graph.E;
			int dist[] = new int[V];

			// Step 1: Initialize distances from source to all other vertices as INFINITE
			Arrays.fill(dist, Integer.MAX_VALUE);
			dist[source] = 0;

			// Step 2: Relax all edges |V| - 1 times.
			for (int i = 1; i < V; ++i) {
				for (int j = 0; j < E; ++j) {
					int u = graph.edge[j].source;
					int v = graph.edge[j].destination;
					int weight = graph.edge[j].weight;
					if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
						dist[v] = dist[u] + weight;
				}
			}

			// Step 3: Check for negative-weight cycles
			for (int j = 0; j < E; ++j) {
				int u = graph.edge[j].source;
				int v = graph.edge[j].destination;
				int weight = graph.edge[j].weight;
				if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
					System.out.println("Graph contains negative weight cycle");
					return;
				}
			}

			// Print distances from source to all vertices
			printDistances(dist, V);
		}

		// Print distances from source to all vertices
		void printDistances(int dist[], int V) {
			System.out.println("Vertex Distance from Source:");
			for (int i = 0; i < V; ++i)
				System.out.println(i + "\t\t" + dist[i]);
		}
	}

	public static int strStr(String haystack, String needle) {
		int[] pattern = new int[needle.length()];
		Arrays.fill(pattern, -1);
		buildPattern(needle, pattern);
		return matchIndex(haystack, needle, pattern);
	}

	private static void buildPattern(String str, int[] pattern) {
		int j = 0;
		int i = 1;
		while (i < str.length()) {
			if (str.charAt(i) == str.charAt(j)) {
				pattern[i] = j;
				i++;
				j++;
			} else if (j > 0) {
				j = pattern[j - 1] + 1;
			} else {
				i++;
			}
		}
	}

	private static int matchIndex(String str, String substr, int[] pattern) {
		int j = 0, i = 0;

		while (i + substr.length() - j <= str.length()) {
			if (str.charAt(i) == substr.charAt(j)) {
				if (j == substr.length() - 1)
					return i - j;
				i++;
				j++;
			} else if (j > 0) {
				j = pattern[j - 1] + 1;
			} else {
				i++;
			}
		}
		return -1;
	}

	// Java Program to implementation of Segment Tree

	static class SegmentTreeNode {
		// Range of indices covered by this node
		int start, end;

		// Sum of values in the range
		int sum;

		// Pointers to left and right child nodes
		SegmentTreeNode left, right;

		public SegmentTreeNode(int start, int end) {
			this.start = start;
			this.end = end;
			this.sum = 0;
			this.left = null;
			this.right = null;
		}
	}

	// Segment Tree Class
	public static class SegmentTree {
		// Root of the segment tree
		private SegmentTreeNode root;

		public SegmentTree(int[] nums) {
			root = buildTree(nums, 0, nums.length - 1);
		}

		// Build the segment tree recursively
		private SegmentTreeNode buildTree(int[] nums, int start, int end) {
			if (start > end) {
				return null; // Empty node
			}
			SegmentTreeNode node = new SegmentTreeNode(start, end);
			if (start == end) {
				// Leaf node: store the value directly
				node.sum = nums[start];
			} else {
				int mid = start + (end - start) / 2;

				// Build left subtree
				node.left = buildTree(nums, start, mid);

				// Build right subtree
				node.right = buildTree(nums, mid + 1, end);

				// Combine values from children
				node.sum = node.left.sum + node.right.sum;
			}
			return node;
		}

		// Query the range sum [i, j]
		public int rangeSum(int i, int j) {
			return rangeSum(root, i, j);
		}

		private int rangeSum(SegmentTreeNode node, int start, int end) {
			if (node == null || start > node.end || end < node.start) {
				// Out of range or null node
				return 0;
			}
			if (start <= node.start && end >= node.end) {
				// Fully covered by this node
				return node.sum;
			}
			return rangeSum(node.left, start, end) + rangeSum(node.right, start, end);
		}

		// Update the value at index i
		public void update(int i, int val) {
			update(root, i, val);
		}

		private void update(SegmentTreeNode node, int index, int val) {
			if (node.start == node.end) {
				// Leaf node: update the value
				node.sum = val;
			} else {
				int mid = node.start + (node.end - node.start) / 2;
				if (index <= mid) {
					// Update left subtree
					update(node.left, index, val);
				} else {
					// Update right subtree
					update(node.right, index, val);
				}

				// Recalculate sum
				node.sum = node.left.sum + node.right.sum;
			}
		}

	}

	public static class FenwickTree {
		private int[] tree;

		// Constructor to initialize the Fenwick Tree with size n
		public FenwickTree(int n) {
			tree = new int[n + 1]; // 1-based indexing
		}

		// Update the tree by adding 'value' at index 'idx'
		public void update(int idx, int value) {
			while (idx < tree.length) {
				tree[idx] += value;
				idx += idx & -idx; // Move to the next index
			}
		}

		// Get the prefix sum from index 1 to 'idx'
		public int query(int idx) {
			int sum = 0;
			while (idx > 0) {
				sum += tree[idx];
				idx -= idx & -idx; // Move to the parent index
			}
			return sum;
		}

		// Get the sum of elements in range [left, right]
		public int rangeQuery(int left, int right) {
			return query(right) - query(left - 1);
		}
	}

	static final int N = 4; // N-.max_x and max_y

	// A structure to hold the queries
	static class Query {
		int x1, y1; // x and y co-ordinates of bottom left
		int x2, y2; // x and y co-ordinates of top right

		public Query(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

	};

	// A function to update the 2D BIT
	static void updateBIT(int BIT[][], int x, int y, int val) {
		for (; x <= N; x += (x & -x)) {
			// This loop update all the 1D BIT inside the
			// array of 1D BIT = BIT[x]
			for (; y <= N; y += (y & -y))
				BIT[x][y] += val;
		}
		return;
	}

	// A function to get sum from (0, 0) to (x, y)
	static int getSum(int BIT[][], int x, int y) {
		int sum = 0;

		for (; x > 0; x -= x & -x) {
			// This loop sum through all the 1D BIT
			// inside the array of 1D BIT = BIT[x]
			for (; y > 0; y -= y & -y) {
				sum += BIT[x][y];
			}
		}
		return sum;
	}

	// A function to create an auxiliary matrix
	// from the given input matrix
	static void constructAux(int mat[][], int aux[][]) {
		// Initialise Auxiliary array to 0
		for (int i = 0; i <= N; i++)
			for (int j = 0; j <= N; j++)
				aux[i][j] = 0;

		// Construct the Auxiliary Matrix
		for (int j = 1; j <= N; j++)
			for (int i = 1; i <= N; i++)
				aux[i][j] = mat[N - j][i - 1];

		return;
	}

	// A function to construct a 2D BIT
	static void construct2DBIT(int mat[][], int BIT[][]) {
		// Create an auxiliary matrix
		int[][] aux = new int[N + 1][N + 1];
		constructAux(mat, aux);

		// Initialise the BIT to 0
		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++)
				BIT[i][j] = 0;

		for (int j = 1; j <= N; j++) {
			for (int i = 1; i <= N; i++) {
				// Creating a 2D-BIT using update function
				// everytime we/ encounter a value in the
				// input 2D-array
				int v1 = getSum(BIT, i, j);
				int v2 = getSum(BIT, i, j - 1);
				int v3 = getSum(BIT, i - 1, j - 1);
				int v4 = getSum(BIT, i - 1, j);

				// Assigning a value to a particular element
				// of 2D BIT
				updateBIT(BIT, i, j, aux[i][j] - (v1 - v2 - v4 + v3));
			}
		}
		return;
	}

	// A function to answer the queries
	static void answerQueries(Query q[], int m, int BIT[][]) {
		for (int i = 0; i < m; i++) {
			int x1 = q[i].x1 + 1;
			int y1 = q[i].y1 + 1;
			int x2 = q[i].x2 + 1;
			int y2 = q[i].y2 + 1;

			int ans = getSum(BIT, x2, y2) - getSum(BIT, x2, y1 - 1) - getSum(BIT, x1 - 1, y2)
					+ getSum(BIT, x1 - 1, y1 - 1);

			System.out.printf("Query(%d, %d, %d, %d) = %d\n", q[i].x1, q[i].y1, q[i].x2, q[i].y2, ans);
		}
		return;
	}

	public static void main(String args[]) {
		int size = 5;
		UnionFind uf = new UnionFind(size);
		uf.union(1, 2);
		uf.union(3, 4);
		boolean inSameSet = uf.find(1) == uf.find(2);
		System.out.println("Are 1 and 2 in the same set? " + inSameSet);

		Graph g = new Graph(4);
		g.insertEdge(0, 1, true);
		g.insertEdge(0, 2, false);
		g.insertEdge(1, 2, true);
		g.insertEdge(2, 0, false);
		g.insertEdge(2, 3, true);
		g.insertEdge(3, 3, false);
		System.out.println("Following is Breadth First Traversal " + "(starting from vertex 2)");
		BfsGraph BFS = new BfsGraph(g);
		BFS.bfs(0);

		int[][] grid = { { 0, 1, 1 }, { 1, 1, 0 }, { 1, 1, 0 } };
		System.out.println(minimumObstacles(grid));

		int V = 5;
		int E = 8;
		BellmanFord graph = new BellmanFord(V, E);
		// Define edges
		// Edge 0-1
		graph.edge[0].source = 0;
		graph.edge[0].destination = 1;
		graph.edge[0].weight = -1;

		// Edge 0-2
		graph.edge[1].source = 0;
		graph.edge[1].destination = 2;
		graph.edge[1].weight = 4;

		// Edge 1-2
		graph.edge[2].source = 1;
		graph.edge[2].destination = 2;
		graph.edge[2].weight = 3;

		// Edge 1-3
		graph.edge[3].source = 1;
		graph.edge[3].destination = 3;
		graph.edge[3].weight = 2;

		// Edge 1-4
		graph.edge[4].source = 1;
		graph.edge[4].destination = 4;
		graph.edge[4].weight = 2;

		// Edge 3-2
		graph.edge[5].source = 3;
		graph.edge[5].destination = 2;
		graph.edge[5].weight = 5;

		// Edge 3-1
		graph.edge[6].source = 3;
		graph.edge[6].destination = 1;
		graph.edge[6].weight = 1;

		// Edge 4-3
		graph.edge[7].source = 4;
		graph.edge[7].destination = 3;
		graph.edge[7].weight = -3;

		// Execute Bellman-Ford algorithm
		graph.BellmanFordAlgo(graph, 0);

		System.out.println(strStr("hello", "ll"));

		int[] nums = { 1, 3, 5, 7, 9, 11 };
		SegmentTree segmentTree = new SegmentTree(nums);

		System.out.println("Range Sum (0, 2): " + segmentTree.rangeSum(0, 2));
		segmentTree.update(1, 10);
		System.out.println("Range Sum (0, 2): " + segmentTree.rangeSum(0, 2));
		System.out.println("Range Sum (1, 3): " + segmentTree.rangeSum(1, 3));
		System.out.println("Range Sum (2, 5): " + segmentTree.rangeSum(2, 5));
		segmentTree.update(4, 6);
		System.out.println("Range Sum (3, 5): " + segmentTree.rangeSum(3, 5));

		FenwickTree fenwickTree = new FenwickTree(6);
		// Update values in the array
		fenwickTree.update(1, 4);
		fenwickTree.update(2, 2);
		fenwickTree.update(3, 5);
		fenwickTree.update(4, 8);
		fenwickTree.update(5, 3);
		fenwickTree.update(6, 1);
		// Query prefix sums
		System.out.println("Sum of first 3 elements: " + fenwickTree.query(3)); // Output: 11
		System.out.println("Sum of range [2, 5]: " + fenwickTree.rangeQuery(2, 5)); // Output: 18

		int mat[][] = { { 1, 2, 3, 4 }, { 5, 3, 8, 1 }, { 4, 6, 7, 5 }, { 2, 4, 8, 9 } };

		// Create a 2D Binary Indexed Tree
		int[][] BIT = new int[N + 1][N + 1];
		construct2DBIT(mat, BIT);

		Query q[] = { new Query(1, 1, 3, 2), new Query(2, 3, 3, 3), new Query(1, 1, 1, 1) };
		int m = q.length;

		answerQueries(q, m, BIT);

	}

}
