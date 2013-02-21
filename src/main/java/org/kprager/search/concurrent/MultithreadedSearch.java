/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.search.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import org.kprager.dictionary.Dictionary;
import org.kprager.score.ScoredWord;

/**
 *
 * @author kprager
 */
public class MultithreadedSearch {
//    private static final Logger log = Logger.getLogger(Main.class);
    Set<String> dictionary = Dictionary.getDictionary();

    public static void main(String[] args) {
        System.out.println("Starting...");
        
        String letters = "fabulous?";
        if (args.length > 0) {
            letters = args[0];
        }
        
        // we must convert the letters to lowercase so that they match 
        // the dictionary entries.  I tried "Keda" and it used the upercase 'K',
        // thus not producting the proper output of words.
        letters = letters.toLowerCase();
        
        System.out.println("Using letters: " + letters);
        
        Set<String> out = new MultithreadedSearch().go(letters);
        
        List<ScoredWord> scoredWords = new ArrayList<ScoredWord>();
        for (String s : out) {
            scoredWords.add(new ScoredWord(s));
        }
        Collections.sort(scoredWords);
        
        System.out.println("We found " + out.size() + " words and scored them.  Here they are: ");
        
        System.out.println(scoredWords);
    }

    public Set<String> go(String letters) {
        // create a buffer to put letter sequences into
        BlockingQueue<String> buffer = new LinkedBlockingQueue<String>(5000);
        
        // create a buffer to put scored words into
        ConcurrentSkipListSet<String> outBuffer = new ConcurrentSkipListSet< String>();
        
        // create our producer and consumer threads
        Thread generator = new Thread(new Generator(buffer, letters), "generate");
        Thread searcher = new Thread(new Searcher(buffer, outBuffer, dictionary), "search");
        
        // start em up
        generator.start();
        searcher.start();
        
        // join this thread's execution to the consumer thread's executions so
        // we don't continue on and return the buffer full of scored words
        // until the consumer thread has finished executing.
        try {
            searcher.join();
        } catch (InterruptedException ex) {
            System.out.println(ex);
            // this should be logged...
        } finally {
            // return the scored words.
            return outBuffer;
        }

    }
}
