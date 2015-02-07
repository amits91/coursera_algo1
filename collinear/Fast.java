/*************************************************************************
 * Name : Amit Srivastava
 * Email: srivastava.amit@gmail.com
 *
 * Compilation:  javac Fast.java
 *
 * Description: Fast force algo to check for collinear points
 *
 *************************************************************************/
import java.util.Arrays;
public class Fast {
    /*
    private static class Collinear {
        private Point p0;
        private double slope;
    }
    private static Queue<Collinear> foundPointsQ = null;
    private static boolean isPointReportedQ(Point p1, double slope) {
        for (Collinear c : foundPointsQ) {
            if (slope == c.slope) {
                // point is collinear or same
                if ((c.p0.compareTo(p1) == 0) || (c.p0.slopeTo(p1) == c.slope)) {
                    return true;
                }
            }
        }
        return false;
    }
    */
    private static class SlopeList {
        private Queue<Double> slopes;
    }
    private static SlopeList [] foundPoints = null;
    private static boolean isPointReported(Point p1, double slope, Point [] arr) {
        if (slope == Double.NEGATIVE_INFINITY) return false;
        int k = getIdx(arr, p1);
        assert (k >= 0);
        SlopeList slopeP0 = foundPoints[k];
        if (slopeP0 != null) {
            for (double c : slopeP0.slopes) {
                if (c == slope) {
                    /*
                    if (isPointReportedQ(p1, slope) == false)
                        StdOut.println("\n WRONG\n");
                    */
                    return true;
                }
            }
        }
        /*
        if (isPointReportedQ(p1, slope) == true)
            StdOut.println("\n WRONG\n");
        */
        return false;
    }
    /*
    private static double [] foundPoints = null;
    private static boolean isPointReported(Point p1, double slope, Point [] arr) {
        if (slope == Double.NEGATIVE_INFINITY) return false;
        int k = getIdx(arr, p1);
        assert (k >= 0);
        double slopeP0 = foundPoints[k];
        if (slopeP0 == slope) {
            return true;
        }
        if (isPointReportedQ(p1, slope) == true)
            StdOut.println("\n WRONG\n");
        return false;
    }
    */
    // Binary search to get index for point
    private static int getIdx(Point[] arr, Point p) {
        int leftIdx = 0;
        int rightIdx = arr.length - 1;
        int i = leftIdx + (rightIdx - leftIdx)/2;
        while (leftIdx <= rightIdx) {
            int cmp = p.compareTo(arr[i]);
            if (cmp == 0) return i;
            else if (cmp < 0) rightIdx = i - 1;
            else leftIdx = i + 1;
            i = leftIdx + (rightIdx - leftIdx)/2;
        }
        return -1;
    }
    private static Point maxPoint(Point p0, Point p1) {
       if (p0.compareTo(p1) > 0) {
           return p0;
       } else {
           return p1;
       }
    }
    private static Point max4Point(Point p0, Point p1, Point p2, Point p3) {
        return maxPoint(maxPoint(p0, p1), maxPoint(p2, p3));
    }
    private static Point minPoint(Point p0, Point p1) {
       if (p0.compareTo(p1) > 0) {
           return p1;
       } else {
           return p0;
       }
    }
    private static Point min4Point(Point p0, Point p1, Point p2, Point p3) {
        return minPoint(minPoint(p0, p1), minPoint(p2, p3));
    }
    private static void reportPoints(Point[] slopePoints,
            Point p0, int start, int end, double currSlope, Point[] arr) {
        if ((end - start) >= 2) {
            /*
            StdOut.println("Origin: " + p0);
            for (int jt = 0; jt < slopePoints.length; jt++) {
                StdOut.println("["+jt+"]"+slopePoints[jt]
                        + "="
                        + p0.slopeTo(slopePoints[jt]));
            }
            StdOut.println("Report Points: P0: "
                    + p0 + ", start: "+start+
                    ", end: " + end +", slope: "+currSlope);
                    */
            Point[] rp = new Point[end - start + 2];
            rp[0] = p0;
            int idx = 1;
            for (int ri = start; ri <= end; ri++) {
                rp[idx++] = slopePoints[ri]; 
            }
            Arrays.sort(rp);
            for (int rj = 0; rj < rp.length; rj++) {
                StdOut.print(rp[rj]); 
                if (rj != (rp.length - 1)) {
                    StdOut.print(" -> ");
                } else {
                    StdOut.print("\n");
                }
                //foundPoints[getIdx(arr, rp[rj])] = currSlope;
                /*
                SlopeList q = foundPoints[getIdx(arr, rp[rj])];
                if (q == null) {
                    q = new SlopeList();
                    q.slopes = new Queue<Double>();
                }
                q.slopes.enqueue(currSlope);
                foundPoints[getIdx(arr, rp[rj])] = q;
                */
            }
            rp[0].drawTo(rp[rp.length - 1]);
            /*
            Collinear reportedPoints = new Collinear();
            reportedPoints.p0 = p0;
            reportedPoints.slope = currSlope;
            foundPointsQ.enqueue(reportedPoints);
            */
            SlopeList q = foundPoints[getIdx(arr, rp[0])];
            if (q == null) {
                q = new SlopeList();
                q.slopes = new Queue<Double>();
            }
            q.slopes.enqueue(currSlope);
            foundPoints[getIdx(arr, rp[0])] = q;
        }
    }
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        Point[] slopePoints = new Point[N];
        int a = 0;
        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            points[a] = new Point(p, q);
            slopePoints[a] = points[a];
            a++;
        }
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //StdDraw.setPenRadius(.01);
        StdDraw.show(0);

        Arrays.sort(points);
        //foundPoints = new double[points.length];
        foundPoints = new SlopeList[points.length];
        for (int i = 0; i < foundPoints.length; ++i) {
            //foundPoints[i] = Double.NEGATIVE_INFINITY;
            foundPoints[i] = null;
        }
        //foundPointsQ = new Queue<Collinear>();
        for (int k = 0; k < points.length; k++) {
            Point p0 = points[k];
            Point p1 = p0;
            double currSlope = Double.NEGATIVE_INFINITY;
            double reportedSlope = Double.NEGATIVE_INFINITY;
            p0.draw();
            Arrays.sort(slopePoints, p0.SLOPE_ORDER);
            double slope = Double.NEGATIVE_INFINITY;
            int start = 0;
            int end = start;
            for (int j = 1; j < slopePoints.length; j++) {
                p1 = slopePoints[j];
                currSlope = p0.slopeTo(p1);
                if (((reportedSlope != Double.NEGATIVE_INFINITY)
                            && (reportedSlope == currSlope))
                        || isPointReported(p1, currSlope, points)) {
                    start         = j;
                    end           = start;
                    slope         = currSlope;
                    reportedSlope = currSlope;
                    continue;
                }
                reportedSlope = Double.NEGATIVE_INFINITY;
                if (currSlope != slope) {
                    reportPoints(slopePoints, p0, start, end, slope, points);
                    start = j;
                    end   = start;
                    slope = currSlope;
                } else {
                    end++;
                }
            }
            reportPoints(slopePoints,
                    p0, start, slopePoints.length - 1, slope, points);
        }
        StdDraw.show(0);
    }
}
