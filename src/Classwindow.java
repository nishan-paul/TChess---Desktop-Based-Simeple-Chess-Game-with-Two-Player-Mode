import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Classwindow {
    JFrame game;
    Classboard board;
    Classwindow(String blackName, String whiteName) {
        game = new JFrame("CHESS");
        try {
            Image whiteImg = ImageIO.read(getClass().getResource("bpawn.png"));
            game.setIconImage(whiteImg);
        }
        catch (IOException e) {
        }
        game.setLocation(100, 100);
        game.setLayout(new BorderLayout(20,20));
        JPanel gameData = gameDataPanel(blackName+" is playing with BLACK", whiteName+" is playing with WHITE");
        gameData.setSize(gameData.getPreferredSize());
        game.add(gameData, BorderLayout.NORTH);
        this.board = new Classboard(this);
        game.add(board, BorderLayout.CENTER);
        game.add(buttons(), BorderLayout.SOUTH);
        game.setMinimumSize(game.getPreferredSize());
        game.setSize(game.getPreferredSize());
        game.setResizable(true);
        game.setVisible(true);
        game.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    JPanel gameDataPanel(final String bn, final String wn) {
        JPanel gameData = new JPanel();
        gameData.setLayout(new GridLayout(3,2,0,0));
        JLabel w = new JLabel(wn);
        JLabel b = new JLabel(bn);
        w.setHorizontalAlignment(JLabel.CENTER);
        w.setVerticalAlignment(JLabel.CENTER);
        b.setHorizontalAlignment(JLabel.CENTER);
        b.setVerticalAlignment(JLabel.CENTER);
        w.setSize(w.getMinimumSize());
        b.setSize(b.getMinimumSize());
        gameData.add(w);
        gameData.add(b);
        return gameData;
    }
    JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 10, 0));
        final JButton quit = new JButton("QUIT");
        quit.addActionListener((ActionEvent e) -> {
            game.dispose();
        });
        final JButton nGame = new JButton("NEW GAME");
        nGame.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(new Classmenu());
            game.dispose();
        });
        buttons.add(nGame);
        buttons.add(quit);
        buttons.setPreferredSize(buttons.getMinimumSize());
        return buttons;
    }
    public void checkmateOccurred (int c) {
        if (c == 0) {
            int n = JOptionPane.showConfirmDialog(game, "WHITE IS GOING TO WIN BY CHECKMATE. WANT TO START A NEW GAME?\n", "WHITE WINS", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new Classmenu());
                game.dispose();
            }
        }
        else {
            int n = JOptionPane.showConfirmDialog(game, "BLACK IS GOING TO WIN BY CHECKMATE. WANT TO START A NEW GAME?\n", "BLACK WINS", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new Classmenu());
                game.dispose();
            }
        }
    }
}
