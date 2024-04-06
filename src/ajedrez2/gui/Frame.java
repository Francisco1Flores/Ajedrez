
package ajedrez2.gui;

import ajedrez2.Const;
import ajedrez2.logic.Juego;
import ajedrez2.logic.MouseActions;
import javax.swing.JFrame;


public class Frame {
    public static void start() {
        
        JFrame frame = new JFrame("Ajedrez");
        Juego juego = new Juego();
        Board board = new Board(juego);
        frame.add(board);
        frame.setSize(480, 480 + Const.SUP_BAR_HEIGHT);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MouseActions.mouseActions(frame, juego);
        
        while (true) {
            try {
                board.repaint();
                Thread.sleep(10);
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
