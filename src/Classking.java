import java.util.*;
public class Classking extends Classpiece {
    public Classking(int color, Classsquare initSq, String img_file) {
        super(color, initSq, img_file);
    }
    @Override
    public List<Classsquare> getLegalMoves(Classboard b) {
        LinkedList<Classsquare> legalMoves = new LinkedList<>();
        Classsquare[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        for (int i=1; i>-2; i--) {
            for (int k=1; k>-2; k--) {
                if ( !(i==0 && k==0) ) {
                    try {
                        if(!board[y + k][x + i].isOccupied() || board[y + k][x + i].getOccupyingPiece().getColor() != this.getColor()) {
                            legalMoves.add(board[y + k][x + i]);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        return legalMoves;
    }
}
