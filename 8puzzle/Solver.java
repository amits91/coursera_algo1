/*************************************************************************
 * Name : Amit Srivastava
 * Email: srivastava.amit@gmail.com
 *
 * Compilation:  javac Solver.java
 * Execution:
 * Dependencies:
 *
 * Description:
 *
 *************************************************************************/


public class Solver {
    private SearchNode soln;
    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode previous;
        private Board search;
        private int priority;
        private int moves;
        private int manhattan;
        public SearchNode(SearchNode prev, Board curr) {
            previous  = prev;
            search    = curr;
            manhattan = search.manhattan();
            if (prev == null) moves = 0;
            else              moves = prev.moves + 1;
            priority  = moves + manhattan;
        }
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        soln = null;
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        SearchNode curr     = new SearchNode(null, initial);
        SearchNode currTwin = new SearchNode(null, initial.twin());
        boolean twinReached = false;
        pq.insert(curr);
        pqTwin.insert(currTwin);
        while (!(pq.isEmpty() || pqTwin.isEmpty())) {
            curr     = pq.delMin();
            currTwin = pqTwin.delMin();
            //StdOut.println("C:");
            //StdOut.println(curr.search);
            //StdOut.println("T:");
            //StdOut.println(currTwin.search);
            if (soln == null) {
                if (curr.search.isGoal()) {
                    soln = curr;
                    //break;
                } else {
                    for (Board n : curr.search.neighbors()) {
                        if ((curr.previous != null)
                                && (n.equals(curr.previous.search))) continue;
                        SearchNode nS = new SearchNode(curr, n);
                        pq.insert(nS);
                    }
                }
            }
            if (!twinReached) {
                if (currTwin.search.isGoal()) {
                    twinReached = true;
                } else {
                    for (Board n : currTwin.search.neighbors()) {
                        if ((currTwin.previous != null) 
                                && (n.equals(currTwin.previous.search))) continue;
                        SearchNode nS = new SearchNode(currTwin, n);
                        pqTwin.insert(nS);
                    }
                }
            }
        }
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return (soln != null);
    }
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (soln != null) return soln.moves;
        else return -1;
    }
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (soln == null) {
            return null;
        }
        Stack<Board> iter = new Stack<Board>();
        SearchNode c = soln;
        while (c != null) {
            iter.push(c.search);
            c = c.previous;
        }
        return iter;
    }
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
