import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
public class Classboard extends JPanel implements MouseListener, MouseMotionListener {
    String wbishop = "wbishop.png";
    String bbishop = "bbishop.png";
    String wknight = "wknight.png";
    String bknight = "bknight.png";
    String wrook = "wrook.png";
    String brook = "brook.png";
    String wking = "wking.png";
    String bking = "bking.png";
    String bqueen = "bqueen.png";
    String wqueen = "wqueen.png";
    String wpawn = "wpawn.png";
    String bpawn = "bpawn.png";
    Classsquare[][] board;
    Classwindow g;
    LinkedList<Classpiece> Bpieces;
    LinkedList<Classpiece> Wpieces;
    List<Classsquare> movable;
    boolean whiteTurn;
    Classpiece currPiece;
    int currX;
    int currY;
    Classcheckmate cmd;
    Classboard(Classwindow g) {
        this.g = g;
        board = new Classsquare[8][8];
        Bpieces = new LinkedList<>();
        Wpieces = new LinkedList<>();
        setLayout(new GridLayout(8, 8, 0, 0));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int x=0; x<8; x++) {
            for(int y=0; y<8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;
                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Classsquare(this, 1, y, x);
                    this.add(board[x][y]);
                }
                else {
                    board[x][y] = new Classsquare(this, 0, y, x);
                    this.add(board[x][y]);
                }
            }
        }
        initializePieces();
        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));
        whiteTurn = true;
    }
    void initializePieces() {
        for (int x=0; x<8; x++) {
            board[1][x].put(new Classpawn(0, board[1][x], bpawn));
            board[6][x].put(new Classpawn(1, board[6][x], wpawn));
        }
        board[7][3].put(new Classqueen(1, board[7][3], wqueen));
        board[0][3].put(new Classqueen(0, board[0][3], bqueen));
        Classking bk = new Classking(0, board[0][4], bking);
        Classking wk = new Classking(1, board[7][4], wking);
        board[0][4].put(bk);
        board[7][4].put(wk);
        board[0][0].put(new Classrook(0, board[0][0], brook));
        board[0][7].put(new Classrook(0, board[0][7], brook));
        board[7][0].put(new Classrook(1, board[7][0], wrook));
        board[7][7].put(new Classrook(1, board[7][7], wrook));
        board[0][1].put(new Classknight(0, board[0][1], bknight));
        board[0][6].put(new Classknight(0, board[0][6], bknight));
        board[7][1].put(new Classknight(1, board[7][1], wknight));
        board[7][6].put(new Classknight(1, board[7][6], wknight));
        board[0][2].put(new Classbishop(0, board[0][2], bbishop));
        board[0][5].put(new Classbishop(0, board[0][5], bbishop));
        board[7][2].put(new Classbishop(1, board[7][2], wbishop));
        board[7][5].put(new Classbishop(1, board[7][5], wbishop));
        for(int y=0; y<2; y++) {
            for (int x=0; x<8; x++) {
                Bpieces.add(board[y][x].getOccupyingPiece());
                Wpieces.add(board[7-y][x].getOccupyingPiece());
            }
        }
        cmd = new Classcheckmate(this, Wpieces, Bpieces, wk, bk);
    }
    Classsquare[][] getSquareArray() {
        return this.board;
    }
    boolean getTurn() {
        return whiteTurn;
    }
    void setCurrPiece(Classpiece p) {
        this.currPiece = p;
    }
    Classpiece getCurrPiece() {
        return this.currPiece;
    }
    @Override
    public void paintComponent(Graphics g) {
        for (int x=0; x<8; x++) {
            for (int y=0; y<8; y++) {
                Classsquare sq = board[y][x];
                sq.paintComponent(g);
            }
        }
        if (currPiece != null) {
            if ((currPiece.getColor() == 1 && whiteTurn) || (currPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currPiece.getImage();
                g.drawImage(i, currX, currY, null);
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();
        Classsquare sq = (Classsquare) this.getComponentAt(new Point(e.getX(), e.getY()));
        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        Classsquare sq = (Classsquare) this.getComponentAt(new Point(e.getX(), e.getY()));
        if (currPiece != null) {
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            List<Classsquare> legalMoves = currPiece.getLegalMoves(this);
            movable = cmd.getAllowableSquares(whiteTurn);
            if (legalMoves.contains(sq) && movable.contains(sq) && cmd.testMove(currPiece, sq)) {
                sq.setDisplay(true);
                currPiece.move(sq);
                cmd.update();
                if (cmd.blackCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(0);
                }
                else if (cmd.whiteCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(1);
                }
                else {
                    currPiece = null;
                    whiteTurn = !whiteTurn;
                    movable = cmd.getAllowableSquares(whiteTurn);
                }

            }
            else {
                currPiece.getPosition().setDisplay(true);
                currPiece = null;
            }
        }
        repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}