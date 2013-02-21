/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.search.concurrent;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import org.kprager.math.combination.Combination;
import org.kprager.math.permutation.Permutation;

/**
 *
 * @author kprager
 */
public class Generator implements Runnable {

    private final BlockingQueue<String> buffer;
    private final String letters;

    public Generator(BlockingQueue<String> buffer, String letters) {
        this.buffer = buffer;
        this.letters = letters;
    }

    @Override
    public void run() {
        // Starting to generate letter sequences.
        int maxLength = letters.length() > 7 ? 7 : letters.length();
        if (letters.contains("?")) {
            for (char c = 'a'; c <= 'z'; c++) {
                addAllLetterSequencesToBuffer(letters.replace('?', c), 3, maxLength);
            }
        } else {
            addAllLetterSequencesToBuffer(letters, 2, maxLength);
        }

        // put a CORK in it.
        buffer.add(Searcher.CORK);
    }

    /**
     * 
     * @param letters letters to generate letter sequences with
     * @param minLength minimum length of generated letter sequences
     * @param maxLength maximum length of generated letter sequences
     */
    private void addAllLetterSequencesToBuffer(String letters, Integer minLength, Integer maxLength) {
        // generate all combinations over minLength
        Set<String> combos = Combination.getCombinations(letters.toCharArray(), minLength, maxLength);
        for (String s : combos) {
            // generate all permutations of each combination.
            try {
                Permutation.permute(s, buffer);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
