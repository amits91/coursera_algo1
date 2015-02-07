/*************************************************************************
 * Name : Amit Srivastava
 * Email: srivastava.amit@gmail.com
 *
 * Compilation:  javac Board.java
 * Execution:
 * Dependencies:
 *
 * Description:
 *
 *************************************************************************/


public class Board {
    private final int[][] tiles;
    private final int N;
    private final int zI;
    private final int zJ;
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = new int[N][N];
        int p = -1;
        int q = -1;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (blocks[i][j] == 0) {
                    p = i;
                    q = j;
                }
                tiles[i][j] = blocks[i][j];
            }
        }
        zI = p;
        zJ = q;
    }
    // board dimension N
    public int dimension() {
        return N;
    }
    // number of blocks out of place
    public int hamming() {
        int hamValue = 0;
        int goalTile = 0;

        //StdOut.println("Hamming:");

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                ++goalTile;
                if (i == N - 1 && j == N - 1) goalTile = 0;
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != goalTile) {
                    ++hamValue;
                    //StdOut.println((i*N+j+1) + ": 1");
                } else {
                    //StdOut.println((i*N+j+1) + ": 0");
                }
            }
        }
        return hamValue;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manVal = 0;
        int goalTile = 0;

        //StdOut.println("Manhattan:");

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                ++goalTile;
                if (i == N - 1 && j == N - 1) goalTile = 0;
                if (tiles[i][j] != goalTile) {
                    int v = tiles[i][j];
                    int vI = (v - 1) / N;
                    int vJ = (v - 1) % N;
                    if (v == 0) continue;
                    int m = Math.abs(vI - i) + Math.abs(vJ -j);
                    //StdOut.println((i*N+j+1)+": "+
                    //v + ": " + m + " vI: " + vI + 
                    //" vJ:" + vJ + " i:" + i + " j:" + j);
                    manVal += m;
                } else {
                    //StdOut.println((i*N+j+1) + ": 0");
                }
            }
        }
        //StdOut.print("\n");
        return manVal;
    }
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    private static void swap(int[][] b, int i, int j, int p, int q) {
        int x = b[i][j];
        b[i][j] = b[p][q];
        b[p][q] = x;
    }
    private int[][] getBlocks() {
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                blocks[i][j] = tiles[i][j];
            }
        }
        return blocks;
    }
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] blocks = this.getBlocks();
        int a = StdRandom.uniform(N);
        int b = StdRandom.uniform(N - 1);
        if (a == zI) a = (zI + 1) % N;
        swap(blocks, a, b, a, b + 1);
        return new Board(blocks);
    }
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.N != this.N) return false;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();
        if (zI - 1 >= 0) {
            int[][] blocks = this.getBlocks();
            swap(blocks, zI, zJ, zI - 1, zJ);
            q.enqueue(new Board(blocks));
        }
        if (zI + 1 < N) {
            int[][] blocks = this.getBlocks();
            swap(blocks, zI, zJ, zI + 1, zJ);
            q.enqueue(new Board(blocks));
        }
        if (zJ - 1 >= 0) {
            int[][] blocks = this.getBlocks();
            swap(blocks, zI, zJ, zI, zJ - 1);
            q.enqueue(new Board(blocks));
        }
        if (zJ + 1 < N) {
            int[][] blocks = this.getBlocks();
            swap(blocks, zI, zJ, zI, zJ + 1);
            q.enqueue(new Board(blocks));
        }
        return q;
    }
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    private void printAllProps() {
        StdOut.println(this);
        StdOut.println("Hamming: " + this.hamming());
        StdOut.println("Manhattan: " + this.manhattan());
        StdOut.println("Twin:");
        Board b = this.twin();
        StdOut.println(b);
        StdOut.println("Hamming: " + b.hamming());
        StdOut.println("Manhattan: " + b.manhattan());
        StdOut.println("Neighbors:");
        for (Board n : neighbors()) {
            StdOut.println(n);
            StdOut.println("Hamming: " + n.hamming());
            StdOut.println("Manhattan: " + n.manhattan());
        }
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        int goalTile = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                ++goalTile;
                if (i == N - 1 && j == N - 1) goalTile = 0;
                blocks[i][j] = goalTile;
            }
        }
        Board goal = new Board(blocks);
        StdOut.println("Goal Board");
        goal.printAllProps();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("Given Board");
        initial.printAllProps();


    }
}
