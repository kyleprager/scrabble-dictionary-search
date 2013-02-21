package org.kprager.search;

import org.kprager.score.ScoredWord;
import org.kprager.dictionary.Dictionary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.kprager.math.combination.Combination;
import org.kprager.math.permutation.Permutation;

public class SingleThreadedSearch {

    private static final Set<String> dictionaryTree;

    static {
        dictionaryTree = Dictionary.getDictionary();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Create new WordSearch object for testing
        SingleThreadedSearch ws = new SingleThreadedSearch();

        // Test regex search.  This can be used to search for words
        // that include letters already on the board that you have to work
        // around.
        long start = System.currentTimeMillis();
        String regex = ".*l.*";
        System.out.println("Testing regex search with regex: " + regex);
        System.out.println(ws.regexSearch("hello", regex));
        long stop = System.currentTimeMillis();
        System.out.printf("Matched expression in %d ms\n", stop - start);

        // Test a single word
        start = System.currentTimeMillis();
        Queue<String> q = ws.search("hello?");
        stop = System.currentTimeMillis();
        System.out.printf("Found %d words in %d ms\n", q.size(), stop - start);


        // Test search with a few letter sequences at once
        String[] arr = {"hello", "hello?", "tsqeeto?", "myletters"};

        // Recursive permutation generation
        System.out.println("Recursively generating permutations....");
        for (int i = 0; i < arr.length; i++) {
            start = System.currentTimeMillis();
            q = ws.search(arr[i]);
            stop = System.currentTimeMillis();
            System.out.printf("Found %d words in %d ms\n", q.size(), stop - start);
        }

        // Test scored word list generation
        System.out.println("Testing ScoredWord generation");
        List<ScoredWord> list;
        for (int i = 0; i < arr.length; i++) {
            start = System.currentTimeMillis();
            list = ws.getScoredWordResults(arr[i]);
            stop = System.currentTimeMillis();
            System.out.printf("Found %d words in %d ms\n", list.size(), stop - start);
            System.out.println(list);
        }
    }

    /**
     *
     * @param letters Letters to search for dictionary words with.
     * @return List of <b>ScoredWord</b> objects in an ordered list sorted by score high to low.
     */
    public List<ScoredWord> getScoredWordResults(String letters) throws InterruptedException {
        // Setup TreeSet dictionary
        List<ScoredWord> scoredWords = new ArrayList<ScoredWord>();
        for (String s : search(letters)) {
            scoredWords.add(new ScoredWord(s));
        }
        Collections.sort(scoredWords);
        return scoredWords;
    }

    /**
     *
     * @param letters Letters to search for dictionary words with.
     * @return Set of dictionary words generated with given letters.
     */
    public Queue<String> search(String letters) throws InterruptedException {
        if (!letters.contains("?")) {
            return searchDictionary(letters);
        }
        Queue<String> words = new LinkedList<String>();
        for (char c = 'a'; c <= 'z'; c++) {
            words.addAll(searchDictionary(letters.replace('?', c), 3));
        }
        return words;
    }

    /**
     *
     * @param letters Letters to search for dictionary words with. Assuming no '?' characters.
     * @return Set of dictionary words generated with given letters.
     */
    public Queue<String> searchDictionary(String letters) throws InterruptedException {
        return searchDictionary(letters, 2);
    }

    /**
     *
     * @param letters Letters to search for dictionary words with. Assuming no '?' characters.
     * @param minWordLength Minimum length of generated words
     * @return Set of dictionary words generated with given letters.
     */
    public Queue<String> searchDictionary(String letters, Integer minWordLength) throws InterruptedException {
        Set<String> combinations = Combination.getCombinations(letters.toCharArray(), minWordLength, letters.length());
        Queue<String> permutations = new LinkedList<String>();
        Set<String> tmp = new TreeSet<String>();
        for (String s : combinations) {
            tmp = Permutation.permute(s);
            for (String str : tmp) {
                if (dictionaryTree.contains(str)) {
                    permutations.add(str);
                }
            }
        }
        return permutations;
    }

    /**
     *
     * @param letters Letters to search for dictionary words with.
     * @return Set of dictionary words generated with given letters.
     */
    public Set<String> regexSearch(String letters, String regex) throws InterruptedException {
        Set<String> matches = new TreeSet();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        for (String s : search(letters)) {
            matcher = pattern.matcher(s);
            if (matcher.matches()) {
                matches.add(s);
            }
        }
        return matches;
    }
}
