/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.math.permutation;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author kprager
 */
public class Permutation {

//    public static void main(String[] args) throws InterruptedException {
//        Set<String> myset = permute("hello");
//        System.out.println("permutations: " + myset.size());
//        int ctr = 1;
//        for(String s : myset) {
//            System.out.printf("%d. %s\n", ctr++, s);
//        }
//
//        System.out.println("\n==================\n");
//        System.out.println("\n====Iterative=====\n");
//        long start = System.currentTimeMillis();
//        Set<String> set = permute("asdfioty?");
//        long stop = System.currentTimeMillis();
//        System.out.printf("Generated %d strings in %d ms\n", set.size(), stop - start);
//    }

    public static Set<String> permute(String str) {
        Set<String> set = new TreeSet<String>();
        permute(str.toCharArray(), 0, str.length() - 1, set);
        return set;
    }

    private static void permute(char[] a, int i, int n, Set<String> set) {
        int j;
        if (i == n) {
            set.add(new String(a));
            a = null;
        } else {
            for (j = i; j <= n; j++) {
                a = swap(a, i, j);
                permute(a, i + 1, n, set);
                a = swap(a, i, j);
            }
        }
    }

    public static void permute(String str, BlockingQueue<String> buffer) throws InterruptedException {
        permute(str.toCharArray(), 0, str.length() - 1, buffer);
    }

    private static void permute(char[] a, int i, int n, BlockingQueue<String> buffer) throws InterruptedException {
        int j;
        if (i == n) {
            buffer.put(new String(a));
            a = null;
        } else {
            for (j = i; j <= n; j++) {
                a = swap(a, i, j);
                permute(a, i + 1, n, buffer);
                a = swap(a, i, j);
            }
        }
    }

    private static char[] swap(char[] arr, int a, int b) {
        char tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
        return arr;
    }
}
