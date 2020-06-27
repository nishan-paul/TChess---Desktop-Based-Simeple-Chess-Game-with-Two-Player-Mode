import java.util.*;
public class Classpawn extends Classpiece {
    private boolean wasMoved;
    public Classpawn(int color, Classsquare initSq, String img_file) {
        super(color, initSq, img_file);
    }
    @Override
    public boolean move(Classsquare fin) {
        boolean b = super.move(fin);
        wasMoved = true;
        return b;
    }
    @Override
    public List<Classsquare> getLegalMoves(Classboard b) {
        LinkedList<Classsquare> legalMoves = new LinkedList<>();
        Classsquare[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int c = this.getColor();
        if (c == 0) {
            if (!wasMoved) {
                if (!board[y+2][x].isOccupied()) {
                    legalMoves.add(board[y+2][x]);
                }
            }
            if (y+1 < 8) {
                if (!board[y+1][x].isOccupied()) {
                    legalMoves.add(board[y+1][x]);
                }
            }
            if (x+1 < 8 && y+1 < 8) {
                if (board[y+1][x+1].isOccupied()) {
                    legalMoves.add(board[y+1][x+1]);
                }
            }
            if (x-1 >= 0 && y+1 < 8) {
                if (board[y+1][x-1].isOccupied()) {
                    legalMoves.add(board[y+1][x-1]);
                }
            }
        }
        if (c == 1) {
            if (!wasMoved) {
                if (!board[y-2][x].isOccupied()) {
                    legalMoves.add(board[y-2][x]);
                }
            }
            if (y-1 >= 0) {
                if (!board[y-1][x].isOccupied()) {
                    legalMoves.add(board[y-1][x]);
                }
            }
            if (x+1 < 8 && y-1 >= 0) {
                if (board[y-1][x+1].isOccupied()) {
                    legalMoves.add(board[y-1][x+1]);
                }
            }
            if (x-1 >= 0 && y-1 >= 0) {
                if (board[y-1][x-1].isOccupied()) {
                    legalMoves.add(board[y-1][x-1]);
                }
            }
        }
        return legalMoves;
    }
}
