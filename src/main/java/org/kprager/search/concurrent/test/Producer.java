/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kprager.search.concurrent.test;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kprager
 */
public class Producer implements Runnable {

    private BlockingQueue<String> buffer;

    public Producer(BlockingQueue<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random r = new Random();
        while (true) {
            try {
                System.out.println("Producing");
                buffer.put("Kyle");
                Thread.sleep(r.nextInt(1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }

    }
}
