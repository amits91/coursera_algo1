/*
 * ==============================================================
 *
 *       Filename:  Percolation.java
 *    Description:  
 *        Created:  06/22/2014 04:26:37 AM
 *         Author:  Amit Srivastava (srivastava.amit@gmail.com)
 *
 * ==============================================================
 */

public class Percolation {

    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridFull;
    private int   length;
    private boolean[] isOpen;
    private int down;
    /**
     * Constructor to Create N-by-N grid with all sites blocked
     */
    public Percolation(int N) {
        if (N <= 0) throw  new IllegalArgumentException();
        length = N;
        down = N * N + 1;
        // Create grid with 2 additional sites for virtual top and bottom.
        // id = (i - 1)*length + j - 1 + 1
        grid = new WeightedQuickUnionUF(down + 1);
        gridFull = new WeightedQuickUnionUF(down + 1);
        isOpen = new boolean[down + 1];
    }

    private int getId(int i, int j) {
        if (i < 1 || i > length) 
            throw new IndexOutOfBoundsException(
                    "row index " + i + " out of bounds: " + length);
        if (j < 1 || j > length) 
            throw new IndexOutOfBoundsException(
                    "column index " + j + " out of bounds: " + length);
        return (i - 1) * length + j;
    }

    private void checkUnion(int id, int k, int l) {
        int otherId = getId(k, l);
        if (isOpen[ otherId ]) {
            //StdOut.println(" other: " + k +"," + l);
            grid.union(id, otherId);
            gridFull.union(id, otherId);
        }
    }

    /**
     * open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {
        int id = getId(i, j);
        if (isOpen[ id ]) return;
        isOpen[ id ] = true;
        if (i > 1) {
            checkUnion(id, i - 1, j);
        }
        if (i < length) {
            checkUnion(id, i + 1, j);
        }
        if (j > 1) {
            checkUnion(id, i, j - 1);
        } 
        if (j < length) {
            checkUnion(id, i, j + 1);
        }
        if (i == 1 || i == length) {
            // Now create path from top
            if (i == 1) {
                //StdOut.println(" top: " + i +"," + j);
                grid.union(0, id);
                gridFull.union(0, id);
            }
            // Now create path from down
            if (i == length) {
                //StdOut.println(" down: " + i +"," + j);
                grid.union(down, id);
            }
        }
    }

    /**
     * is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        return isOpen[ getId(i, j) ];
    }

    /**
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        //return grid.connected(0, getId(i, j));
        return gridFull.connected(0, getId(i, j));
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        /*
        for (int i = 1; i <= length; i++) {
            if (isFull(length, i)) {
                return true;
            }
        }
        return false;
        */
        return grid.connected(0, down);
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        int cnt = 1;
        Percolation perc = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            perc.open(p, q);
            StdOut.println(p + " " + q);
            cnt++;
            if (perc.percolates()) {
                StdOut.println(" System Percolates after " + cnt + "tries");
                cnt--;
            }
        }
    }
}
