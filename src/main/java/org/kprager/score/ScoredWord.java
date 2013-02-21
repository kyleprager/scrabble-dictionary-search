/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kprager.score;

/**
 *
 * @author kprager
 */
public class ScoredWord implements Comparable {
    private String word;
    private Integer score;

    public ScoredWord(String word) {
        this.setWord(word);
    }

    public Integer getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    public final void setWord(String word) {
        this.word = word;
        this.score = ScrabbleWordScorer.scoreWord(word);
    }

    @Override
    public int compareTo(Object o) {
        return ((ScoredWord)o).getScore().compareTo(this.getScore());
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(word).append(", ").append(score).append("]").toString();
    }
}
