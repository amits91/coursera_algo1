/*
 * ==============================================================
 *
 *       Filename:  Subset.java
 *    Description:  
 *        Created:  07/05/2014
 *         Author:  Amit Srivastava (srivastava.amit@gmail.com)
 *
 * ==============================================================
 */

public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) throw new java.lang.IllegalArgumentException();
        int N = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            q.enqueue(s);
        }
        assert ((N > 0) && (N < q.size()));
        for (int i = 0; i < N; i++) {
            StdOut.println(q.dequeue());
        }
        while (q.size() > 0) q.dequeue();
    }
}
