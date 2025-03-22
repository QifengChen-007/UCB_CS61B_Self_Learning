package game2048;

import java.util.Formatter;
import java.util.Observable;
import java.util.ArrayList;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;
    private static final int COLMERGE = 1;
    private static final int COLMOVE =2;
    private boolean checkChanged = false;
    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        board.setViewingPerspective(side);
        changed = isChanged(changed);
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    private  boolean isChanged(boolean changed){
        ArrayList<Integer>[] valid = new ArrayList[4];

        for (int c=0; c < board.size();c++){
            valid[c] = new ArrayList<>();
            for (int r=board.size()-1; r>=0; r--){
                if (board.tile(c,r) != null){
                    valid[c].add(r);
                }
            }
        }

        for (int c = 0; c <= 3; c++){
            // if nothing in this column
            if (valid[c].isEmpty()){
                continue;
            }

            // if there is one tile in this column
            if (valid[c].size() == 1){
                Tile s1 = board.tile(c,valid[c].get(0));
                if (s1.row()!=3) changed=true;
                board.move(c,3,s1);

            } else if (valid[c].size() == 2){
                Tile t1 = board.tile(c,valid[c].get(0));
                Tile t2 = board.tile(c,valid[c].get(1));
                if (t1.value()==t2.value()){
                    changed = true;
                    board.move(c,3,t2);
                    board.move(c,3,t1);
                    score += board.tile(c,3).value();
                } else {
                    changed = true;
                    board.move(c,3,t1);
                    board.move(c,2,t2);
                }
            }


            else if (valid[c].size() == 3){
                Tile t1 = board.tile(c,valid[c].get(0));
                Tile t2 = board.tile(c,valid[c].get(1));
                Tile t3 = board.tile(c,valid[c].get(2));
                if (t1.value()==t2.value()){
                    changed = true;
                    board.move(c,3,t1);
                    board.move(c,3,t2);
                    board.move(c,2,t3);
                    score += board.tile(c,3).value();
                } else if (t2.value()==t3.value()){
                    changed = true;
                    board.move(c,3,t1);
                    board.move(c,2,t2);
                    board.move(c,2,t3);
                    score += board.tile(c,2).value();
                } else {
                    changed = true;
                    board.move(c,3,t1);
                    board.move(c,2,t2);
                    board.move(c,1,t3);
                }
            }

            // if there are 4 tile in this column
            else {
                Tile t1 = board.tile(c,valid[c].get(0));
                Tile t2 = board.tile(c,valid[c].get(1));
                Tile t3 = board.tile(c,valid[c].get(2));
                Tile t4 = board.tile(c,valid[c].get(3));

                if (t1.value()==t2.value()){
                    if(t3.value()== t4.value()){
                        changed = true;
                        board.move(c,3,t2);
                        score += board.tile(c, 3).value();
                        board.move(c, 2,t3);
                        board.move(c, 2,t4);
                        score += board.tile(c,2).value();

                    } else {
                        changed = true;
                        score += board.tile(c, 3).value();
                        board.move(c,3,t2);
                        score += board.tile(c, 1).value();
                        board.move(c, 2,t3);
                        board.move(c, 1,t4);
                    }
                } else {
                    if (t2.value() != t3.value()){
                        if (t3.value()==t4.value()){
                            changed = true;
                            score += board.tile(c,1).value();
                            board.move(c,1,t4);
                        }
                    } else {
                        changed = true;
                        score += board.tile(c,2).value();
                        board.move(c, 2,t3);
                        board.move(c, 1,t4);

                    }
                }
            }
        }
        return changed;
    }





    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int i=0; i < b.size(); i ++){
            for (int j=0; j < b.size(); j ++){
                if (b.tile(i,j) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int i=0; i < b.size(); i ++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i,j) == null){
                    continue;
                }
                if (b.tile(i,j).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        for (int i=0; i < b.size()-1; i++){
            for (int j=0; j<b.size()-1; j++){
                if (b.tile(i,j)==null || b.tile(i+1,j)==null || b.tile(i,j+1)==null){
                    return true;
                } if (b.tile(i,j).value()==b.tile(i+1,j).value() || b.tile(i,j).value()==b.tile(i,j+1).value()){
                    return true;
                }
            }
        }
        if (b.tile(3,3)==null){
            return true;
        } else if (b.tile(3,2).value() == b.tile(3,3).value() || b.tile(3,3).value() == b.tile(2,3).value()) {
            return true;
        }

        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
