/*************************************************************************
 * Name : Amit Srivastava
 * Email: srivastava.amit@gmail.com
 *
 * Compilation:  javac KdTree.java
 * Execution:
 * Dependencies:
 *
 * Description:
 *
 *************************************************************************/

public class KdTree {
    private Node root;
    private int N;
    private static class Node {
        private Point2D p;      // the point
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;  
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }
    // construct an empty set of points
    public KdTree() {
    }
    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return N;
    }

    // add the point p to the set (if it is not already in the set)
    private int compare(double q, double p) {
        if (p < q) return -1;
        if (p > q) return +1;
        return 0;
    }
    private Node put(Node x, Point2D key, boolean useX, Node par, boolean left) {
        if (x == null) {
            Node n = new Node();
            n.p  = key;
            n.lb = null;
            n.rt = null;
            N++;
            if (par == null) {
                n.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            } else {
                if (left) {
                    if (useX) {
                        n.rect = new RectHV(
                                par.rect.xmin(),
                                par.rect.ymin(),
                                par.rect.xmax(),
                                par.p.y()
                                );
                        //StdOut.println("left.vertical");
                    } else {
                        n.rect = new RectHV(
                                par.rect.xmin(),
                                par.rect.ymin(),
                                par.p.x(),
                                par.rect.ymax()
                                );
                        //StdOut.println("left.horizontal");
                    }
                } else {
                    if (useX) {
                        n.rect = new RectHV(
                                par.rect.xmin(),
                                par.p.y(),
                                par.rect.xmax(),
                                par.rect.ymax()
                                );
                        //StdOut.println("right.vertical");
                    } else {
                        n.rect = new RectHV(
                                par.p.x(),
                                par.rect.ymin(),
                                par.rect.xmax(),
                                par.rect.ymax()
                                );
                        //StdOut.println("right.horizontal");
                    }
                }
                //StdOut.println("==> Par: " + par.p + " x : " + key + " N: " + N );
            }
            return n;
        }
        int cmp;
        if (useX) {
            cmp = compare(x.p.x(), key.x());
        } else {
            cmp = compare(x.p.y(), key.y());
        }
        //StdOut.println( "Node:" +
        //x.p + " Key: " + key + " cmp: " + cmp + " UseX: " + useX);
        if      (cmp < 0) x.lb = put(x.lb, key, !useX, x, true);
        else if (cmp > 0) x.rt = put(x.rt, key, !useX, x, false);
        else {
            if (x.p.compareTo(key) == 0) {
                return x;
            } else {
                x.rt = put(x.rt, key, !useX, x, false);
            }
        }
        return x;
    }
    public void insert(Point2D p) {
        root = put(root, p, true, null, false);
    }
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return get(root, p, true) != null;
    }

    private Point2D get(Node x, Point2D key, boolean useX) {
        if (x == null) return null;
        int cmp;
        if (useX) {
            cmp = compare(x.p.x(), key.x());
        } else {
            cmp = compare(x.p.y(), key.y());
        }
        if      (cmp < 0) return get(x.lb, key, !useX);
        else if (cmp > 0) return get(x.rt, key, !useX);
        else {
            if (x.p.compareTo(key) == 0) {
                return x.p;
            } else {
                return get(x.rt, key, !useX);
            }
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        root.rect.draw();
        drawPointRecurse(root, true);
    }
    private void drawPoint(Node x, boolean virt)
    {
        double xStart, yStart;
        double xEnd, yEnd;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        x.p.draw();
        StdDraw.setPenRadius();
        //x.rect.draw();
        if (virt) {
            StdDraw.setPenColor(StdDraw.RED);
            yStart = x.rect.ymin();
            yEnd   = x.rect.ymax();
            xStart = x.p.x();
            xEnd   = x.p.x();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            xStart = x.rect.xmin();
            xEnd   = x.rect.xmax();
            yStart = x.p.y();
            yEnd   = x.p.y();
        }
        StdDraw.line(xStart, yStart, xEnd, yEnd);
    }

    private void drawPointRecurse(Node x, boolean virt)
    {
        if (x == null) return;
        drawPoint(x, virt);
        drawPointRecurse(x.lb, !virt);
        drawPointRecurse(x.rt, !virt);
    }

    private void traverseAndCollectPoints(Node x, RectHV rect, Queue<Point2D> q)
    {
        if (x == null) return;
        if (rect.contains(x.p)) {
            q.enqueue(x.p);
        }
        if (x.lb != null) {
            if (x.lb.rect.intersects(rect)) {
                traverseAndCollectPoints(x.lb, rect, q);
            }
        }
        if (x.rt != null) {
            if (x.rt.rect.intersects(rect)) {
                traverseAndCollectPoints(x.rt, rect, q);
            }
        }
    }
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        traverseAndCollectPoints(root, rect, q);
        return q;
    }
    private class Val {
        private double minDistSquared;
        private Point2D minPoint;
    }
    private void traverseAndSearch(Node x, Point2D p, Val v)
    {
        if (x == null) return;
        double distSq = x.p.distanceSquaredTo(p);
        if (distSq < v.minDistSquared) {
            v.minDistSquared = distSq;
            v.minPoint = x.p;
        }
        if (x.lb != null) {
            if (v.minDistSquared > x.lb.rect.distanceSquaredTo(p)) {
                traverseAndSearch(x.lb, p, v);
            }
        }
        if (x.rt != null) {
            if (v.minDistSquared > x.rt.rect.distanceSquaredTo(p)) {
                traverseAndSearch(x.rt, p, v);
            }
        }

    }
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        Val v = new Val();
        v.minDistSquared = Double.POSITIVE_INFINITY;
        v.minPoint = null;
        traverseAndSearch(root, p, v);
        return v.minPoint;
    }
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);


        StdDraw.show(0);

        KdTree kdtree = new KdTree();
        //Point2D r = new Point2D(0.0, 0.0);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //StdOut.println(" Contains: " + kdtree.contains(r));
            StdDraw.clear();
            kdtree.draw();
        }
        //StdOut.println(" N: " + kdtree.size());
        StdDraw.show(50);
    }
}

