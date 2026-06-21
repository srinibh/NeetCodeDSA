package com.neetcode.problems;

import java.util.LinkedList;
import java.util.Queue;

public class TreeProblemsI {

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

	public static TreeNode invertTree(TreeNode root) {
		if (root == null)
			return null;
		TreeNode tmp = root.right;
		root.right = invertTree(root.left);
		root.left = invertTree(tmp);

		return root;
	}

	public static int maxDepth(TreeNode root) {
		if (root == null)
			return 0;
		return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
	}

	static int maxDiameter;

	public static int diameterOfBinaryTree(TreeNode root) {
		maxDiameter = 0;
		height(root);
		return maxDiameter;
	}

	public static int height(TreeNode root) {
		if (root == null)
			return -1;
		int left = height(root.left);
		int right = height(root.right);
		int h = 1 + Math.max(left, right);
		maxDiameter = Math.max(maxDiameter, left + right + 2);
		return h;
	}

	public static boolean isBalanced(TreeNode root) {
		if (root == null)
			return true;
		int res = helper(root);
		return res == -1 ? false : true;
	}

	private static int helper(TreeNode root) {
		if (root == null)
			return 0;
		int leftH = helper(root.left);
		if (leftH == -1)
			return -1;
		int rightH = helper(root.right);
		if (rightH == -1)
			return -1;
		return Math.abs(leftH - rightH) > 1 ? -1 : 1 + Math.max(leftH, rightH);
	}

	public static boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null)
			return true;
		if (p == null || q == null)
			return false;
		return (p.val == q.val) && (isSameTree(p.left, q.left)) && (isSameTree(p.right, q.right));
	}

	public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
		if (root == null && subRoot == null)
			return true;
		if (root == null || subRoot == null)
			return false;
		Queue<TreeNode> q = new LinkedList<>();
		q.offer(root);
		while (!q.isEmpty()) {
			TreeNode node = q.poll();
			if (node.val == subRoot.val) {
				if (isSameTree(node, subRoot))
					return true;
			}
			if (node.left != null)
				q.offer(node.left);
			if (node.right != null)
				q.offer(node.right);
		}
		return false;
	}

	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null)
			return null;
		int val = root.val;
		if (val < p.val && val < q.val)
			return lowestCommonAncestor(root.right, p, q);
		else if (val > p.val && val > q.val)
			return lowestCommonAncestor(root.left, p, q);
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
		System.out.println(invertTree(root));
		System.out.println(maxDepth(root));
		System.out.println(diameterOfBinaryTree(root));
		System.out.println(height(root));
		System.out.println(isBalanced(root));
		System.out.println(isSameTree(root,root));
		System.out.println(isSubtree(root,root));
		System.out.println(lowestCommonAncestor(root,root,root));


	}

}
