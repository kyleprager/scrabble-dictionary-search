/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.search.concurrent;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 *
 * @author kprager
 */
public class Searcher implements Runnable {

//    private static final Logger log = Logger.getLogger(Searcher.class);
    public static final String CORK = "...End Scene.";
    private BlockingQueue<String> input;
    private ConcurrentSkipListSet<String> output;
    private final Set<String> dictionary;

    public Searcher(BlockingQueue<String> input,
            ConcurrentSkipListSet<String> output, Set<String> dictionary) {
        this.input = input;
        this.output = output;
        this.dictionary = dictionary;
    }

    @Override
    public void run() {

        // Search the dictionary for all incoming patterns
        long start = System.currentTimeMillis();
        String str;
        try {
            // if our input is not our CORK, and it's contained in our
            // dictionary, add it to our output set of real words.
            while ((str = input.take()) != CORK) {
                if (dictionary.contains(str)) {
                    output.add(str);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}
