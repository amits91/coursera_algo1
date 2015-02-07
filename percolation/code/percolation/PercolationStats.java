/*
 * =============================================================
 *
 *       Filename:  PercolationStats.java
 *
 *    Description:  
 *        Created:  06/22/2014 10:39:23 AM
 *         Author:  Amit Srivastava (srivastava.amit@gmail.com)
 *
 * =============================================================
 */

public class PercolationStats {
    private double [] xT;
    private int t;
    /**
     * perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();
        t = T;
        xT = new double[T];
        for (int i = 0; i < T; i++) {
            xT[i] = getPercThreshold(N);
        }
    }

    private double getPercThreshold(int N) {
        Percolation perc;
        int cnt = 0;
        double threshold = 0.0;
        perc = new Percolation(N);
        while (!perc.percolates()) {
            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;
            if (perc.isOpen(i, j)) continue;
            perc.open(i, j);
            cnt++;
        }
        threshold = (double) cnt/(N*N);
        return threshold;
    }
    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(xT);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(xT);
    }

    /**
     * returns lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        double mean = this.mean();
        double stddev = this.stddev();
        double rootT = Math.sqrt(t);
        return (mean - ((1.96 * stddev) / rootT));
    }

    /**
     * returns upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        double mean = this.mean();
        double stddev = this.stddev();
        double rootT = Math.sqrt(t);
        return (mean + ((1.96 * stddev) / rootT));
    }

    /**
     * test client, described below
     */
    public static void main(String[] args) {
        if (args.length != 2) throw new java.lang.IllegalArgumentException();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = " 
                + percStats.confidenceLo() + ", " + percStats.confidenceHi());
    }
}
