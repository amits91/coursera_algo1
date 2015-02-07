/*************************************************************************
 * Name : Amit Srivastava
 * Email: srivastava.amit@gmail.com
 *
 * Compilation:  javac Brute.java
 *
 * Description: Brute force algo to check for collinear points
 *
 *************************************************************************/
import java.util.Arrays;
public class Brute {
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        int a = 0;
        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            points[a++] = new Point(p, q);
        }
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //StdDraw.setPenRadius(0.01);
        StdDraw.show(0);
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point p0 = points[i];
            p0.draw();
            boolean matchFound = false;
            for (int j = i; j < points.length; j++) {
                Point p1 = points[j];
                double s1 = p0.slopeTo(p1);
                if (s1 == Double.NEGATIVE_INFINITY)
                    continue;
                for (int k = j; k < points.length; k++) {
                    Point p2 = points[k];
                    double s2 = p0.slopeTo(p2);
                    if (s2 == Double.NEGATIVE_INFINITY)
                        continue;
                    if (p1.compareTo(p2) == 0) 
                        continue;
                    if (s1 == s2) {
                        for (int l = k; l < points.length; l++) {
                            Point p3 = points[l];
                            double s3 = p0.slopeTo(p3);
                            if (s3 == Double.NEGATIVE_INFINITY)
                                continue;
                            if (p1.compareTo(p3) == 0) 
                                continue;
                            if (p2.compareTo(p3) == 0) 
                                continue;
                            if (s2 == s3) {
                                StdOut.println(p0 + " -> " 
                                        + p1 +" -> " 
                                        + p2 + " -> "
                                        + p3);
                                p0.drawTo(p3);
                                //p1.drawTo(p2);
                                //p2.drawTo(p3);
                                matchFound = true;
                                //break;
                            }
                        }
                        /*
                           if (matchFound == true) {
                           break;
                           }
                           */
                    }
                    /*
                       if (matchFound == true) {
                       break;
                       }
                       */
                }
                /*
                   if (matchFound == true) {
                   break;
                   }
                   */
            }
        }
        StdDraw.show(0);
    }
}
