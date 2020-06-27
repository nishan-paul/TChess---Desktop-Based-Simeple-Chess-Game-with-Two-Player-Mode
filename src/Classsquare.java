import java.awt.*;
import javax.swing.*;
public class Classsquare extends JComponent {
    Classboard b;
    int color;
    Classpiece occupyingPiece;
    boolean dispPiece;
    int xNum;
    int yNum;
    Classsquare(Classboard b, int c, int xNum, int yNum) {
        this.b = b;
        this.color = c;
        this.dispPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    int getColor() {
        return this.color;
    }
    Classpiece getOccupyingPiece() {
        return occupyingPiece;
    }
    boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
    int getXNum() {
        return this.xNum;
    }
    int getYNum() {
        return this.yNum;
    }
    void setDisplay(boolean v) {
        this.dispPiece = v;
    }
    void put(Classpiece p) {
        this.occupyingPiece = p;
        p.setPosition(this);
    }
    Classpiece removePiece() {
        Classpiece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }
    void capture(Classpiece p) {
        Classpiece k = getOccupyingPiece();
        if (k.getColor() == 0) b.Bpieces.remove(k);
        if (k.getColor() == 1) b.Wpieces.remove(k);
        this.occupyingPiece = p;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.color == 1)
            g.setColor(new Color(118,37,169));
        else
            g.setColor(new Color(255,255,170));
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if(occupyingPiece != null && dispPiece)
            occupyingPiece.draw(g);
    }
}
