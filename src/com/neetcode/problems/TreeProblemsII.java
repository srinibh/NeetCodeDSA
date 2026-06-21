package com.neetcode.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class TreeProblemsII {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	public static List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		if (root == null)
			return result;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			List<Integer> currLevel = new ArrayList<>(levelSize);
			for (int i = 0; i < levelSize; i++) {
				TreeNode currNode = queue.poll();
				currLevel.add(currNode.val);
				if (currNode.left != null)
					queue.offer(currNode.left);
				if (currNode.right != null)
					queue.offer(currNode.right);
			}
			result.add(currLevel);
		}
		return result;
	}

	public static List<Integer> rightSideView(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		if (root == null)
			return res;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			System.out.println(levelSize);
			for (int i = 0; i < levelSize; i++) {
				TreeNode currNode = queue.poll();
				if (currNode.left != null)
					queue.offer(currNode.left);
				if (currNode.right != null)
					queue.offer(currNode.right);
				if (i == levelSize - 1)
					res.add(currNode.val);
			}
		}
		return res;
	}

	public static int goodNodes(TreeNode root) {
		return goodNodes(root, Integer.MIN_VALUE);
	}

	public static int goodNodes(TreeNode root, int max) {
		if (root == null)
			return 0;
		int res = root.val >= max ? 1 : 0;
		res += goodNodes(root.left, Math.max(max, root.val));
		res += goodNodes(root.right, Math.max(max, root.val));
		return res;
	}

	public static boolean isValidBST(TreeNode root) {
		Long min = Long.MIN_VALUE;
		Long max = Long.MAX_VALUE;
		return helper(root, min, max);
	}

	private static boolean helper(TreeNode root, Long min, Long max) {
		if (root == null)
			return true;
		Long val = (long) root.val;
		if (val <= min || val >= max)
			return false;
		return helper(root.left, min, val) && helper(root.right, val, max);
	}

	public static int kthSmallest(TreeNode root, int k) {
		int[] ans = new int[2];
		ans[1] = k;
		inorder(root, ans);
		return ans[0];
	}

	private static void inorder(TreeNode root, int[] ans) {
		if (root == null)
			return;
		inorder(root.left, ans);
		ans[1]--;
		if (ans[1] == 0)
			ans[0] = root.val;
		inorder(root.right, ans);
	}

	static int preInd;

	public static TreeNode buildTree(int[] preorder, int[] inorder) {
		preInd = 0;
		int n = inorder.length;
		int startIndex = 0, endIndex = n - 1;
		Map<Integer, Integer> inorderMap = new HashMap<>();
		for (int i = 0; i < n; i++)
			inorderMap.put(inorder[i], i);
		return helper(preorder, inorder, startIndex, endIndex, inorderMap);
	}

	public static TreeNode helper(int[] preorder, int[] inorder, int startIndex, int endIndex,
			Map<Integer, Integer> inorderMap) {
		if (startIndex > endIndex)
			return null;
		int val = preorder[preInd++];
		TreeNode root = new TreeNode(val);
		root.left = helper(preorder, inorder, startIndex, inorderMap.get(val) - 1, inorderMap);
		root.right = helper(preorder, inorder, inorderMap.get(val) + 1, endIndex, inorderMap);
		return root;
	}

	static int max;

	public static int maxPathSum(TreeNode root) {
		max = -1001;
		helperMax(root);
		return max;
	}

	private static int helperMax(TreeNode root) {
		if (root == null)
			return 0;
		int val = root.val;
		max = Math.max(max, val);
		int lVal = helperMax(root.left);
		int rVal = helperMax(root.right);
		max = Math.max(max, Math.max(rVal + val, Math.max(lVal + val, lVal + rVal + val)));

		return Math.max(rVal + val, Math.max(lVal + val, val));
	}

	/**
	 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
	 * left; TreeNode right; TreeNode(int x) { val = x; } }
	 */

	// Encodes a tree to a single string.
	public static String serialize(TreeNode root) {
		StringBuilder result = new StringBuilder();
		serialHelper(root, result);
		return result.toString();
	}

	private static void serialHelper(TreeNode root, StringBuilder result) {
		if (root == null) {
			return;
		}
		Queue<TreeNode> q = new LinkedList<>();
		q.offer(root);
		while (!q.isEmpty()) {
			TreeNode curr = q.poll();
			if (curr == null) {
				result.append(",");
				continue;
			}
			result.append(curr.val + ",");
			q.offer(curr.left);
			q.offer(curr.right);
		}
	}

	// Decodes your encoded data to tree.
	public static TreeNode deserialize(String data) {
		String[] nodes = data.split(",");

		if (nodes[0] == "")
			return null;
		return deserialHelper(nodes);
	}

	private static TreeNode deserialHelper(String[] data) {
		String val = data[0];
		TreeNode root = new TreeNode(Integer.parseInt(val));
		Queue<TreeNode> q = new LinkedList<>();
		q.offer(root);
		for (int i = 1; i < data.length; i += 2) {
			TreeNode curr = q.poll();
			if (!data[i].equals("")) {
				curr.left = new TreeNode(Integer.parseInt(data[i]));
				q.offer(curr.left);
			}
			if (i + 1 < data.length && !data[i + 1].equals("")) {
				curr.right = new TreeNode(Integer.parseInt(data[i + 1]));
				q.offer(curr.right);
			}
		}

		return root;
	}

	public static void main(String args[]) {

		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		root.left.right = new TreeNode(6);
		root.left.left = new TreeNode(7);

		List<List<Integer>> result1 = levelOrder(root);
		System.out.println(result1);
		ArrayList<Integer> result2 = (ArrayList<Integer>) rightSideView(root);
		System.out.println(goodNodes(root));
		System.out.println(result2);
		System.out.println(isValidBST(root));
		System.out.println(kthSmallest(root, 3));

		int[] preorder = { 3, 9, 20, 15, 7 };
		int[] inorder = { 9, 3, 15, 20, 7 };
		TreeNode newTree = buildTree(preorder, inorder);
		System.out.println(levelOrder(newTree));
		System.out.println(maxPathSum(root));
		System.out.println(serialize(root));
		System.out.println(levelOrder(deserialize(serialize(root))));


	}

}
