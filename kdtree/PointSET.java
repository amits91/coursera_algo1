/*************************************************************************
 * Name : Amit Srivastava
 * Email: srivastava.amit@gmail.com
 *
 * Compilation:  javac PointSET.java
 * Execution:
 * Dependencies:
 *
 * Description:
 *
 *************************************************************************/

public class PointSET {
    private SET<Point2D> points;
    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    // number of points in the set
    public int size() {
        return points.size();
    }
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!points.contains(p)) {
            points.add(p);
        }
    }
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }
    // draw all of the points to standard draw
    public void draw() {
        //StdDraw.setPenColor(StdDraw.BLACK);
        //StdDraw.setPenRadius(.01);
        for (Point2D p : points) {
            p.draw();
        }
        //StdDraw.show();
    }
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                q.enqueue(point);
            }
        }
        return q;
    }
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        double minDistSquared = Double.POSITIVE_INFINITY;
        Point2D minPoint = null;
        for (Point2D point : points) {
            double distSq = point.distanceSquaredTo(p);
            if (distSq < minDistSquared) {
                minDistSquared = distSq;
                minPoint = point;
            }
        }
        return minPoint;
    }
}
