import java.util.LinkedList;
import java.util.List;
public class Classrook extends Classpiece {
    public Classrook(int color, Classsquare initSq, String img_file) {
        super(color, initSq, img_file);
    }
    @Override
    public List<Classsquare> getLegalMoves(Classboard b) {
        LinkedList<Classsquare> legalMoves = new LinkedList<>();
        Classsquare[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int[] occups = getLinearOccupations(board, x, y);
        for (int i=occups[0]; i<=occups[1]; i++) {
            if (i != y)
                legalMoves.add(board[i][x]);
        }
        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x)
                legalMoves.add(board[y][i]);
        }
        return legalMoves;
    }

}
