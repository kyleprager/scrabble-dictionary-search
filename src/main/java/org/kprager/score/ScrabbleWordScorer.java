/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.score;

/**
 *
 * @author kprager
 */
public class ScrabbleWordScorer {

    public static Integer scoreWord(String str) {
        Integer score = 0;
        for (Character c : str.toCharArray()) {
            switch (c) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                case 'l':
                case 'n':
                case 'r':
                case 's':
                case 't':
                    score += 1;
                    break;
                case 'd':
                case 'g':
                    score += 2;
                    break;
                case 'b':
                case 'c':
                case 'm':
                case 'p':
                    score += 3;
                    break;
                case 'f':
                case 'h':
                case 'v':
                case 'w':
                case 'y':
                    score += 4;
                    break;
                case 'k':
                    score += 5;
                    break;
                case 'j':
                case 'x':
                    score += 8;
                    break;
                case 'q':
                case 'z':
                    score += 10;
                    break;
            }
        }
        return score;
    }
}
