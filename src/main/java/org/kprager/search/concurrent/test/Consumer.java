/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.search.concurrent.test;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author kprager
 */
public class Consumer implements Runnable {

    private BlockingQueue<String> buffer;

    public Consumer(BlockingQueue<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random r = new Random();
        try {
            while (true) {
                System.out.println("Consuming");
                buffer.take();
                Thread.sleep(r.nextInt(1000));
            }
        } catch (InterruptedException ex) {
        }
    }
}
