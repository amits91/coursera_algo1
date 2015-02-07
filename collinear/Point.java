/*************************************************************************
 * Name : Amit Srivastava
 * Email: srivastava.amit@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        double num = that.y - this.y;
        double den = that.x - this.x;
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY; 
        if (num == 0) return 0.0;
        if (den == 0) return Double.POSITIVE_INFINITY;
        return num/den;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slopeP1 = Point.this.slopeTo(p1);
            double slopeP2 = Point.this.slopeTo(p2);
            if ((slopeP1 == Double.POSITIVE_INFINITY)
                && (slopeP2 == Double.POSITIVE_INFINITY)) return 0;
            if ((slopeP1 == Double.NEGATIVE_INFINITY)
                && (slopeP2 == Double.NEGATIVE_INFINITY)) return 0;
            //if ((slopeP1 == Double.POSITIVE_INFINITY)
                //&& (slopeP2 == Double.NEGATIVE_INFINITY)) return 0;
            //if ((slopeP1 == Double.NEGATIVE_INFINITY)
                //&& (slopeP2 == Double.POSITIVE_INFINITY)) return 0;
            //if (slopeP1 == Double.NEGATIVE_INFINITY) return -1;
            //if (slopeP1 == Double.POSITIVE_INFINITY) return +1;
            //if (slopeP2 == Double.NEGATIVE_INFINITY) return +1;
            //if (slopeP2 == Double.POSITIVE_INFINITY) return -1;
            if (slopeP1 < slopeP2) return -1;
            if (slopeP1 > slopeP2) return +1;
            /*
            double slopeP1 = p1.slopeTo(p2);
            if (slopeP1 == Double.NEGATIVE_INFINITY) return -1;
            if (slopeP1 == Double.POSITIVE_INFINITY) return +1;
            if (slopeP1 < 0.0) return -1;
            if (slopeP1 > 0.0) return +1;
            */
            return 0;
        }
    }


    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Point[] points = new Point[N];
        int i = 0;
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            points[i++] = new Point(p, q);
        }
        for (int j = 0; j < points.length; j++) {
            StdOut.println(points[j]);
        }
        StdOut.println("Sorted:  ");
        Arrays.sort(points);
        for (int j = 0; j < points.length; j++) {
            StdOut.println(points[j]);
        }
        for (int k = 0; k < points.length; k++) {
            Point p0 = points[k];
            StdOut.println("Sorted: Slope order " + p0);
            Arrays.sort(points, p0.SLOPE_ORDER);
            for (int j = 0; j < points.length; j++) {
                StdOut.println(points[j] + "=" + p0.slopeTo(points[j]));
            }
        }
    }
}
