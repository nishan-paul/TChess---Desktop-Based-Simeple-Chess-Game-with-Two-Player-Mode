import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Classmenu implements Runnable {
    @Override
    public void run() {
        final JFrame startWindow = new JFrame("CHESS");
        startWindow.setLocation(300,100);
        startWindow.setResizable(true);
        startWindow.setSize(260, 240);
        Box components = Box.createVerticalBox();
        startWindow.add(components);
        final JPanel titlePanel = new JPanel();
        final JLabel titleLabel = new JLabel("! ! ! WELCOME ! ! !");
        titlePanel.add(titleLabel);
        components.add(titlePanel);
        final JPanel blackPanel = new JPanel();
        components.add(blackPanel, BorderLayout.EAST);
        final JLabel blackPiece = new JLabel();
        try{
            Image blackImg = ImageIO.read(getClass().getResource("bpawn.png"));
            blackPiece.setIcon(new ImageIcon(blackImg));
            blackPanel.add(blackPiece);
        }
        catch (IOException e) {
        }
        final JTextField blackInput = new JTextField("", 10);
        blackPanel.add(blackInput);
        
        final JPanel whitePanel = new JPanel();
        components.add(whitePanel);
        final JLabel whitePiece = new JLabel();
        try {
            Image whiteImg = ImageIO.read(getClass().getResource("wpawn.png"));
            whitePiece.setIcon(new ImageIcon(whiteImg));
            whitePanel.add(whitePiece);
            startWindow.setIconImage(whiteImg);
        }
        catch (IOException e) {
        }
        JTextField whiteInput = new JTextField("", 10);
        whitePanel.add(whiteInput);
        Box buttons = Box.createHorizontalBox();
        final JButton quit = new JButton("QUIT");
        quit.addActionListener((ActionEvent e) -> {
            startWindow.dispose();
        });
        final JButton start = new JButton("START");
        start.addActionListener((ActionEvent e) -> {
            String bn = blackInput.getText();
            String wn = whiteInput.getText();
            new Classwindow(bn, wn);
            startWindow.dispose();
        });
        buttons.add(start);
        buttons.add(quit);
        components.add(buttons);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setVisible(true);
    }
}
