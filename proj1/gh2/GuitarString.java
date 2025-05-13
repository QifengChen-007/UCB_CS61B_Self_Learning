package gh2;

import deque.Deque;
import deque.LinkedListDeque;

/**
 * Simulates a vibrating guitar string using the Karplus-Strong algorithm.
 *
 * Note: This class depends on a working implementation of the Deque interface.
 */
public class GuitarString {

    /** Constants. Do not change. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = 0.996; // Energy decay factor

    /** Buffer for storing sound data. */
    private Deque<Double> buffer;

    /**
     * Create a guitar string of the given frequency.
     * Initializes the buffer with zeros.
     */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new LinkedListDeque<>();
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    /**
     * Pluck the guitar string by replacing the buffer with white noise.
     * Values are random numbers between -0.5 and 0.5.
     */
    public void pluck() {
        int capacity = buffer.size();
        buffer = new LinkedListDeque<>();
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(Math.random() - 0.5);
        }
    }

    /**
     * Advance the simulation one time step using the Karplus-Strong algorithm.
     * Removes the first sample, averages it with the new first, applies decay, and appends it.
     */
    public void tic() {
        double first = buffer.removeFirst();
        double second = buffer.get(0);
        double newSample = ((first + second) / 2.0) * DECAY;
        buffer.addLast(newSample);
    }

    /**
     * Return the current sample (the value at the front of the buffer).
     */
    public double sample() {
        return buffer.get(0);
    }
}
