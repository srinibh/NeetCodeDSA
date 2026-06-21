package com.neetcode.problems;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BitManipulationProblem {

	/*
	 * Given a non-empty array of integers nums, every element appears twice except
	 * for one. Find that single one. You must implement a solution with a linear
	 * runtime complexity and use only constant extra space.
	 */

	public static int singleNumber(int[] nums) {
		int res = 0;
		for (int num : nums) {
			res ^= num;
		}
		return res;
	}

	/*
	 * Write a function that takes an unsigned integer and returns the number of '1'
	 * bits it has (also known as the Hamming weight).
	 */
	public static int hammingWeight(int n) {
		int count = 0;
		System.out.println(n);

		while (n != 0) {
			n = n & (n - 1);
			// System.out.println(" loop "+n+" count "+count);
			// count++;
		}

		return count;

	}

	public static int[] countBits(int n) {
		int[] dp = new int[n + 1];
		for (int i = 1; i < n + 1; i++) {
			dp[i] = dp[i / 2] + i % 2;
		}
		return dp;
	}

	public static int reverseBits(int n) {
		int res = 0;
		for (int i = 0; i < 32; i++) {
			int bit = (n >> i) & 1; // check whether the bit is set or not [right shift each bit & 000..1] -> gives
									// the value present at that bit
			res = res | (bit << (31 - i)); // put the bit at 31 - i th position i.e, reverse
		}
		return res;
	}
	
    public static  int missingNumber(int[] nums) {
        int n =  nums.length;
        int sum = n * (n + 1) / 2;
        
        for(int i = 0; i < n; i++) {
            sum -= nums[i];
        }       
        return sum;
    }
    
   public  static int sum(int a, int b) {
        
        // Iterate till there is no carry 
        while (b != 0) { 
            
            // carry contains common set bits of a and b, left shifted by 1
            int carry = (a & b) << 1;

            // Update a with (a + b without carry)
            a = a ^ b;
          
            // Update b with carry
            b = carry; 
        } 
        return a;
    } 


	public static void main(String args[]) {
		int[] nums = { 4, 1, 2, 1, 2 };
		System.out.println(singleNumber(nums));
		System.out.println(hammingWeight(00000000000000000000000000001011));
		int[] cntBits = countBits(5);
		System.out.println();
		Arrays.stream(cntBits).forEach(System.out::print);
		System.out.println(reverseBits(00000000000000000000000000001011));
		int[] missingNums= {9,6,4,2,3,5,7,0,1};
		System.out.println(missingNumber(missingNums));
		System.out.println(sum(2,3));

	}

}
