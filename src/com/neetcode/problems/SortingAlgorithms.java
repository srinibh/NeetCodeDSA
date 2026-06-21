package com.neetcode.problems;

import java.util.Arrays;

public class SortingAlgorithms {

	public static void bubbleSort(int[] arr) {
		int n = arr.length;
		boolean swapped;
		for (int i = 0; i < n - 1; i++) {
			swapped = false; // Track if any swap happens in this pass
			for (int j = 0; j < n - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) { // Compare adjacent elements
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp; // Swap if they’re out of order
					swapped = true;
				}
			}
			if (!swapped)
				break; // Stop if no swaps happened (already sorted)
		}
	}

	public static void selectionSort(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			int minIndex = i; // Assume current index has the smallest value
			for (int j = i + 1; j < n; j++) {
				if (arr[j] < arr[minIndex]) { // Find smaller element
					minIndex = j;
				}
			}
			int temp = arr[minIndex];
			arr[minIndex] = arr[i];
			arr[i] = temp; // Swap the smallest value into its correct position
		}
	}

	public static void insertionSort(int[] arr) {
		int n = arr.length;
		for (int i = 1; i < n; i++) {
			int key = arr[i]; // Element to insert into the sorted section
			int j = i - 1;
			while (j >= 0 && arr[j] > key) { // Shift larger elements right
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key; // Place the element in the correct position
		}
	}

	public static void mergeSort(int[] arr, int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2; // Find the middle point
			mergeSort(arr, left, mid); // Sort left half
			mergeSort(arr, mid + 1, right); // Sort right half
			merge(arr, left, mid, right); // Merge both halves
		}
	}

	public static void merge(int[] arr, int left, int mid, int right) {
		int n1 = mid - left + 1;
		int n2 = right - mid;

		int[] leftArray = new int[n1];
		int[] rightArray = new int[n2];

		for (int i = 0; i < n1; i++) {
			leftArray[i] = arr[left + i];
		}
		for (int i = 0; i < n2; i++) {
			rightArray[i] = arr[mid + 1 + i];
		}

		int i = 0, j = 0, k = left;
		while (i < n1 && j < n2) {
			if (leftArray[i] <= rightArray[j]) {
				arr[k++] = leftArray[i++]; // Copy smaller element
			} else {
				arr[k++] = rightArray[j++];
			}
		}

		while (i < n1) {
			arr[k++] = leftArray[i++]; // Copy remaining from left side
		}
		while (j < n2) {
			arr[k++] = rightArray[j++]; // Copy remaining from right side
		}
	}

	public static void quickSort(int[] arr, int low, int high) {
		if (low < high) {
			int pivotIndex = partition(arr, low, high); // Partition and find pivot position
			quickSort(arr, low, pivotIndex - 1); // Sort elements before pivot
			quickSort(arr, pivotIndex + 1, high); // Sort elements after pivot
		}
	}

	public static int  partition(int[] arr, int low, int high) {
		int pivot = arr[high]; // Choose last element as pivot
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (arr[j] < pivot) { // Move smaller elements to the left
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		int temp = arr[i + 1];
		arr[i + 1] = arr[high]; // Place pivot in the correct position
		arr[high] = temp;
		return i + 1;
	}
	
	public static void main(String args[]) {
		int[] arrUnsorted1= {10,9,8,7,6,5,4,3,2,1};
		bubbleSort(arrUnsorted1);
		int[] arrSorted1=arrUnsorted1;
		Arrays.stream(arrSorted1).forEach(System.out::print);
		System.out.println();
		int[] arrUnsorted2= {10,9,8,7,6,5,4,3,2,1};
		selectionSort(arrUnsorted2);
		int[] arrSorted2=arrUnsorted2;
		Arrays.stream(arrUnsorted2).forEach(System.out::print);
		System.out.println();
		int[] arrUnsorted3= {10,9,8,7,6,5,4,3,2,1};
		insertionSort(arrUnsorted3);
		int[] arrSorted3=arrUnsorted3;
		Arrays.stream(arrUnsorted3).forEach(System.out::print);
		System.out.println();
		int[] arrUnsorted4= {10,9,8,7,6,5,4,3,2,1};
		quickSort(arrUnsorted4,0,arrUnsorted4.length-1);
		int[] arrSorted4=arrUnsorted4;
		Arrays.stream(arrUnsorted4).forEach(System.out::print);
		System.out.println();
		int[] arrUnsorted5= {10,9,8,7,6,5,4,3,2,1};
		mergeSort(arrUnsorted5,0,arrUnsorted5.length-1);
		int[] arrSorted5=arrUnsorted5;
		Arrays.stream(arrUnsorted5).forEach(System.out::print);


		
		
	}
  
}
