/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.math.combination;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kprager
 */
public class Combination {

//    public static void main(String[] args) {
//        char[] arr = "coajak".toCharArray();
//        Set<String> set = getCombinations(arr, 1, arr.length);
//        System.out.println("combinations: " + set.size());
//        int ctr = 1;
//        Iterator<String> iter = set.iterator();
//        while(iter.hasNext()) {
//            System.out.printf("%d. %s\n", ctr++, iter.next());
//        }
//    }

    public static Set<String> getCombinations(char[] elements, int startingCombinationSize, int endingCombinationSize) {
        Set<String> set = new HashSet<String>();
        for (int i = startingCombinationSize; i <= endingCombinationSize; i++) {
            set.addAll(getCombinations(elements, i));
        }
        return set;
    }

    private static Set<String> getCombinations(char[] elements, int combinationSize) {
        Set<String> set = new HashSet<String>();
        int[] indices;
        CombinationGenerator x = new CombinationGenerator(elements.length, combinationSize);
        StringBuffer combination;
        while (x.hasMore()) {
            combination = new StringBuffer();
            indices = x.getNext();
            for (int i = 0; i < indices.length; i++) {
                combination.append(elements[indices[i]]);
            }
            set.add(combination.toString());
        }
        return set;
    }
}
