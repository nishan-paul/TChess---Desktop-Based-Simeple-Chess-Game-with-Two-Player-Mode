import javax.swing.*;
public class Classmain implements Runnable {
    @Override
    public void run() {
        SwingUtilities.invokeLater(new Classmenu());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Classmain());
    }
}
