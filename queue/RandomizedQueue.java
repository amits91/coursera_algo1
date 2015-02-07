/*
 * ==============================================================
 *
 *       Filename:  RandomizedQueue.java
 *    Description:  
 *        Created:  07/05/2014
 *         Author:  Amit Srivastava (srivastava.amit@gmail.com)
 *
 * ==============================================================
 */
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q = null;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    // construct an empty deque
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }
    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }

    // swap
    private void swap(Item[] a, int i, int j) {
        Item item = a[i];
        a[i] = a[j];
        a[j] = item;
    }

    // add item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        // double size of array if necessary
        if (size == q.length) resize(2*q.length);    
        // add item
        q[size++] = item;                            
    }

    // delete and return random item
    public Item dequeue() {
        if (isEmpty()) 
            throw new java.util.NoSuchElementException("Randomized Queue underflow");
        int i = StdRandom.uniform(size);
        //swap random index with last item
        swap(q, i, size-1);
        Item item = q[size-1];
        q[size-1] = null;                           // to avoid loitering
        size--;
        // shrink size of array if necessary
        if (size > 0 && size == q.length/4) resize(q.length/2);
        return item;
    }

    // return but donot delete a random item
    public Item sample() {
        if (isEmpty()) 
            throw new java.util.NoSuchElementException("Randomized Queue underflow");
        int i = StdRandom.uniform(size);
        return q[i];
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private Item[] iter = null;

        public RandomizedQueueIterator() {
            i = 0;
            iter = (Item[]) new Object[size];
            for (int j = 0; j < size; j++) {
                iter[j] = q[j];
                int r = StdRandom.uniform(j+1);
                swap(iter, j, r);
            }
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = iter[i];
            iter[i] = null;
            i++;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        if (args.length != 1) throw new java.lang.IllegalArgumentException();
        int N = Integer.parseInt(args[0]);
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        for (int i = 0; i < N; i++) {
            d.enqueue(i);
        }
        StdOut.println("\nRandomized Queue:Iterate()");
        for (int s: d) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nRandomized Queue:sample()");
        for (int i = 0; i < N; i++) {
            StdOut.print(d.sample() + " ");
        }
        StdOut.println("\nRandomized Queue:Iterate()");
        for (int s: d) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nRandomized Queue:dequeue()");
        while (d.size() > 0) {
            StdOut.print(d.dequeue() + " ");
        }
        StdOut.print("\n");
    }
}
