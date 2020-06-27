import java.util.List;
public class Classbishop extends Classpiece {
    public Classbishop(int color, Classsquare initSq, String img_file) {
        super(color, initSq, img_file);
    }
    @Override
    public List<Classsquare> getLegalMoves(Classboard b) {
        Classsquare[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        return getDiagonalOccupations(board, x, y);
    }
}
