package com.neetcode.problems;

public class TrieProblem {

	static class Trie {
		TrieNode root;

		public Trie() {
			root = new TrieNode();
		}

		public void insert(String word) {
			TrieNode curr = root;
			for (int i = 0; i < word.length(); i++) {
				int ind = word.charAt(i) - 'a';

				if (curr.node[ind] != null) {
					curr = curr.node[ind];
				} else {
					curr.node[ind] = new TrieNode();
					curr = curr.node[ind];
				}
			}
			curr.end = true;
		}

		public boolean search(String word) {
			TrieNode curr = root;
			for (int i = 0; i < word.length(); i++) {
				int ind = word.charAt(i) - 'a';
				if (curr.node[ind] != null)
					curr = curr.node[ind];
				else
					return false;
			}
			return curr.end == true;
		}

		public boolean startsWith(String prefix) {
			TrieNode curr = root;
			for (int i = 0; i < prefix.length(); i++) {
				int ind = prefix.charAt(i) - 'a';
				if (curr.node[ind] != null)
					curr = curr.node[ind];
				else
					return false;
			}
			return true;
		}
	}

	static class TrieNode {
		TrieNode[] node = new TrieNode[26];
		boolean end;
	}

	static class WordDictionary {
		TrieNode root;

		public WordDictionary() {
			root = new TrieNode();
		}

		public void addWord(String word) {
			TrieNode curr = root;
			for (int i = 0; i < word.length(); i++) {
				int ind = word.charAt(i) - 'a';

				if (curr.node[ind] != null) {
					curr = curr.node[ind];
				} else {
					curr.node[ind] = new TrieNode();
					curr = curr.node[ind];
				}
			}
			curr.end = true;
		}

		public boolean search(String word) {
			return trivialSearch(word, root);
		}

		private boolean trivialSearch(String word, TrieNode root) {
			TrieNode curr = root;
			if (word.length() == 0)
				return curr.end;
			boolean ans = false;
			for (int i = 0; i < word.length(); i++) {
				char ch = word.charAt(i);
				if (ch == '.') {
					for (int j = 0; j < 26; j++) {
						if (curr.node[j] != null) {
							ans = trivialSearch(word.substring(i + 1), curr.node[j]);
							if (ans)
								return true;
						}
					}
					return false;
				} else {
					int ind = ch - 'a';
					if (curr.node[ind] != null)
						curr = curr.node[ind];
					else
						return false;
				}
			}
			return curr.end;
		}

		class TrieNode {
			TrieNode[] node = new TrieNode[26];
			boolean end;
		}
	}

	public static void main(String args[]) {

		Trie trie = new Trie();
		trie.insert("apple");
		System.out.println(trie.search("apple")); // return True
		System.out.println(trie.search("app")); // return False
		System.out.println(trie.startsWith("app")); // return True
		trie.insert("app");
		System.out.println(trie.search("app")); // return True

		WordDictionary wordDictionary = new WordDictionary();
		wordDictionary.addWord("bad");
		wordDictionary.addWord("dad");
		wordDictionary.addWord("mad");
		System.out.println(wordDictionary.search("pad")); // return False
		System.out.println(wordDictionary.search("bad")); // return True
		System.out.println(wordDictionary.search(".ad")); // return True
		System.out.println(wordDictionary.search("b..")); // return True

	}

}
