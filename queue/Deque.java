/*
 * ==============================================================
 *
 *       Filename:  Deque.java
 *    Description:  
 *        Created:  07/05/2014
 *         Author:  Amit Srivastava (srivastava.amit@gmail.com)
 *
 * ==============================================================
 */
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldFirst = first;
        first      = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        size++;
        if (oldFirst == null) last = first;
        else oldFirst.prev = first;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldLast = last;
        last      = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        size++;
        if (oldLast == null) first = last;
        else oldLast.next = last;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) 
            throw new java.util.NoSuchElementException("Deque underflow");
        Item item = first.item;
        Node oldFirst = first;
        first = first.next;
        if (first == null) last = null;
        else first.prev = null;
        //oldFirst.next = null;
        //oldFirst.item = null;
        size--;
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) 
            throw new java.util.NoSuchElementException("Deque underflow");
        Item item = last.item;
        Node oldLast = last;
        last = last.prev;
        if (last == null) first = null;
        else last.next = null;
        oldLast.item = null;
        oldLast.prev = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            // unit testing
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        if (args.length != 1) throw new java.lang.IllegalArgumentException();
        int N = Integer.parseInt(args[0]);
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < N; i++) {
            d.addFirst(i);
        }
        StdOut.println("\nDeque Iterator");
        for (int s: d) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nFILO");
        while (d.size() > 0) {
            StdOut.print(d.removeLast() + " ");
        }
        for (int i = 0; i < N; i++) {
            d.addLast(i);
        }
        StdOut.println("\nDeque Iterator");
        for (int s: d) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nLIFO");
        while (d.size() > 0) {
            StdOut.print(d.removeFirst() + " ");
        }
        for (int i = 0; i < N; i++) {
            d.addLast(i);
        }
        StdOut.println("\nDeque Iterator");
        for (int s: d) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nLILO");
        while (d.size() > 0) {
            StdOut.print(d.removeLast() + " ");
        }
        for (int i = 0; i < N; i++) {
            d.addFirst(i);
        }
        StdOut.println("\nDeque Iterator");
        for (int s: d) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nFIFO");
        while (d.size() > 0) {
            StdOut.print(d.removeFirst() + " ");
        }
        StdOut.println("\n");

    }
}
