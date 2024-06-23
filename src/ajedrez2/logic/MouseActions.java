package ajedrez2.logic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class MouseActions {

    public static void mouseActions(Juego juego, JPanel board) {

        board.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {                
                
                if (juego.getPieza((int) e.getX(), (int) e.getY()) != null) {
                    System.out.println(juego.getPieza((int) e.getX(), (int) e.getY()).getName()); 
                } else {
                    System.out.println("gil");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
                //if (juego.isCheck()) System.out.println("Jaque");
                // Selecciona la pieza de acuerdo a la posicion del raton
                juego.selectPiece(e);    
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                juego.releasePiece(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });

        board.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                juego.dragPiece(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
               
            }
        });
    }
}
