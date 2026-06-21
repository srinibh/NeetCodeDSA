package com.neetcode.problems;

import java.util.HashMap;
import java.util.Map;

public class LinkedListProblems {

	public static class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}

	public static ListNode reverseList(ListNode head) {
		ListNode curr = head, next = head, prev = null;

		while (curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}

		return prev;
	}

	public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		ListNode dummy = new ListNode(0);
		ListNode result = dummy;
		while (list1 != null && list2 != null) {
			if (list1.val < list2.val) {
				dummy.next = list1;
				dummy = dummy.next;
				list1 = list1.next;
			} else {
				dummy.next = list2;
				dummy = dummy.next;
				list2 = list2.next;
			}
		}

		if (list1 != null) {
			dummy.next = list1;
		}

		if (list2 != null) {
			dummy.next = list2;
		}

		return result.next;
	}

	public static void reorderList(ListNode head) {
		ListNode slow = head, fast = head.next;
		int len = 0;
		int fulllen = 0;
		while (fast != null && fast.next != null) {
			len++;
			slow = slow.next;
			fast = fast.next.next;
		}
		if (fast != null) {
			fulllen = 2 * (len + 1);
		} else {
			fulllen = 2 * len + 1;

		}

		System.out.println("length of linked list " + fulllen);
		ListNode first = head, second = reverse(slow.next);
		slow.next = null;

		while (second != null) {
			ListNode tmp1 = first.next, tmp2 = second.next;
			first.next = second;

			second.next = tmp1;
			first = tmp1;
			second = tmp2;
		}

	}

	private static ListNode reverse(ListNode head) {
		ListNode curr = head, prev = null, next = null;

		while (curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}

	public static ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode dummyHead = new ListNode(0);
		dummyHead.next = head;
		ListNode slow = dummyHead, fast = dummyHead;

		while (n > 0) {
			fast = fast.next;
			n--;
		}

		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next;
		}

		slow.next = slow.next.next;

		return dummyHead.next;
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode res = new ListNode(0);
		ListNode curr1 = l1, curr2 = l2, curr3 = res;
		int carry = 0;

		while (curr1 != null || curr2 != null || carry > 0) {
			int v1 = curr1 == null ? 0 : curr1.val;
			int v2 = curr2 == null ? 0 : curr2.val;
			int sum = v1 + v2 + carry;
			carry = sum / 10;
			curr3.next = new ListNode(sum % 10);
			curr3 = curr3.next;
			curr1 = curr1 == null ? curr1 : curr1.next;
			curr2 = curr2 == null ? curr2 : curr2.next;
		}

		return res.next;
	}

	public static boolean hasCycle(ListNode head) {
		if (head == null)
			return false;
		ListNode slow = head, fast = head.next;

		while (fast != null && slow != fast) {
			slow = slow.next;
			fast = fast.next;
			if (fast != null)
				fast = fast.next;
		}
		return slow == fast;
	}

	class Node {
		int key;
		int val;
		Node prev;
		Node next;

		Node(int key, int val) {
			this.key = key;
			this.val = val;
		}
	}

	class LRUCache {
		Node head = new Node(0, 0);
		Node tail = new Node(0, 0);
		Map<Integer, Node> mp;
		int size;

		public LRUCache(int capacity) {
			head.next = tail;
			tail.prev = head;
			mp = new HashMap<>();
			size = capacity;
		}

		public int get(int key) {
			if (mp.containsKey(key)) {
				Node node = mp.get(key);
				remove(node);
				insert(node);
				return node.val;
			} else
				return -1;
		}

		public void put(int key, int value) {
			if (mp.containsKey(key)) {
				Node node = mp.get(key);
				remove(node);
			}
			if (mp.size() == size)
				remove(tail.prev);
			insert(new Node(key, value));
		}

		private void remove(Node node) {
			mp.remove(node.key);
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}

		private void insert(Node node) {
			Node headNext = head.next;
			head.next = node;
			node.prev = head;
			node.next = headNext;
			headNext.prev = node;
			mp.put(node.key, node);
		}
	}

	public static ListNode mergeKLists(ListNode[] lists) {
		int k = lists.length;
		if (k == 0)
			return null;
		int i = 0;
		while (i + 1 < k) {
			ListNode l1 = lists[i], l2 = lists[i + 1];
			lists[i + 1] = merge2LL(l1, l2);
			i++;
		}
		return lists[k - 1];
	}

	public static ListNode merge2LL(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);
		ListNode tmp = dummyHead;
		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				tmp.next = l1;
				ListNode nextL1 = l1.next;
				l1.next = l2;
				l1 = nextL1;
				tmp = tmp.next;
			} else {
				tmp.next = l2;
				ListNode nextL2 = l2.next;
				l2.next = l1;
				l2 = nextL2;
				tmp = tmp.next;
			}
		}

		if (l1 == null)
			tmp.next = l2;
		if (l2 == null)
			tmp.next = l1;
		return dummyHead.next;
	}

	public static ListNode reverseKGroup(ListNode head, int k) {

		ListNode root = new ListNode(0, head); // dummy head
		ListNode curr = head, prev = root;

		while (curr != null) {
			ListNode tail = curr; // keep track of the 1st element of each group
			int listIndex = 0;

			while (curr != null && listIndex < k) {
				curr = curr.next;
				listIndex++;
			}
			// listIndex != k means we have a group less than k size
			if (listIndex != k)
				prev.next = tail;
			// less than k size so simply pointing prev to the
			// first element of the group
			else {
				// reverse the group
				prev.next = reverse(tail, k);
				// prev will move to the first element(now the last) of the group
				// so that next of it would have the reverse of the group
				prev = tail;
			}
		}
		return root.next;
	}

	private static ListNode reverse(ListNode head, int k) {
		ListNode curr = head, prev = null, next = null;

		while (curr != null && k > 0) {
			k--;
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		head = prev;
		return head;
	}

	public static void main(String args[]) {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		ListNode reverse = reverseList(head);
		printListNodes(reverse, "Reverse Linked list");
		ListNode head1 = new ListNode(1);
		head1.next = new ListNode(2);
		head1.next.next = new ListNode(3);
		head1.next.next.next = new ListNode(4);
		head1.next.next.next.next = new ListNode(5);

		ListNode tail = new ListNode(1);
		tail.next = new ListNode(2);
		tail.next.next = new ListNode(3);
		ListNode mergeLists = mergeTwoLists(head1, tail);
		printListNodes(mergeLists, "Merge two linklists");

		ListNode head3 = new ListNode(1);
		head3.next = new ListNode(2);
		head3.next.next = new ListNode(3);
		head3.next.next.next = new ListNode(4);
		head3.next.next.next.next = new ListNode(5);
		head3.next.next.next.next.next = new ListNode(6);
		head3.next.next.next.next.next.next = new ListNode(7);

		reorderList(head3);
		printListNodes(head3, "Reorder  linklists");

		ListNode head4 = new ListNode(1);
		head4.next = new ListNode(2);
		head4.next.next = new ListNode(3);
		head4.next.next.next = new ListNode(4);
		head4.next.next.next.next = new ListNode(5);
		head4.next.next.next.next.next = new ListNode(6);
		head4.next.next.next.next.next.next = new ListNode(7);

		ListNode dummy = removeNthFromEnd(head4, 2);
		printListNodes(dummy, "Reorder  Nth Node from the end of the linkedlist");

		ListNode head5 = new ListNode(9);
		head5.next = new ListNode(9);
		head5.next.next = new ListNode(9);
		head5.next.next.next = new ListNode(9);
		ListNode tail5 = new ListNode(9);
		tail5.next = new ListNode(9);
		tail5.next.next = new ListNode(9);
		ListNode sumLists = addTwoNumbers(head5, tail5);
		printListNodes(sumLists, "Add two linklists");

		ListNode head6 = new ListNode(1);
		head6.next = new ListNode(2);
		head6.next.next = new ListNode(3);
		head6.next.next.next = new ListNode(4);
		head6.next.next.next.next = new ListNode(5);
		head6.next.next.next.next.next = new ListNode(6);
		head6.next.next.next.next.next.next = new ListNode(7);
		head6.next.next.next.next.next.next = head6.next.next;
		System.out.println(" Linked List to detect cycle " + hasCycle(head6));

		LRUCache lRUCache = new LinkedListProblems().new LRUCache(2);
		lRUCache.put(1, 1); // cache is {1=1}
		lRUCache.put(2, 2); // cache is {1=1, 2=2}
		System.out.println("LinkedList cache");
		System.out.println(lRUCache.get(1)); // return 1
		lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
		System.out.println(lRUCache.get(2)); // returns -1 (not found)
		lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
		System.out.println(lRUCache.get(1)); // return -1 (not found)
		System.out.println(lRUCache.get(3)); // return 3
		System.out.println(lRUCache.get(4)); // return 4

		ListNode head7 = new ListNode(1);
		head7.next = new ListNode(4);
		head7.next.next = new ListNode(5);
		ListNode head8 = new ListNode(1);
		head8.next = new ListNode(3);
		head8.next.next = new ListNode(4);
		ListNode head9 = new ListNode(2);
		head9.next = new ListNode(6);

		ListNode[] lstNodes = { head7, head8, head9 };
		printListNodes(mergeKLists(lstNodes), "Merge k list Nodes");

		ListNode head10 = new ListNode(1);
		head10.next = new ListNode(2);
		head10.next.next = new ListNode(3);
		head10.next.next.next = new ListNode(4);
		head10.next.next.next.next = new ListNode(5);
		head10.next.next.next.next.next = new ListNode(6);
		head10.next.next.next.next.next.next = new ListNode(7);
		printListNodes(reverseKGroup(head10, 2), "Print linked list Nodes in Reverse Group");
	}

	public static void printListNodes(ListNode temp, String message) {
		System.out.println(message);
		while (temp != null) {
			System.out.print(" " + temp.val);
			temp = temp.next;
		}
		System.out.println();

	}

}
