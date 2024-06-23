package ajedrez2.gui;

import ajedrez2.Const;
import ajedrez2.logic.Juego;
import ajedrez2.logic.Pieza;
import ajedrez2.logic.Position;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel {
        
    private Juego juego;
    Tablero tablero;
    
    public Board(Juego juego) {
        this.juego = juego;
        tablero = new Tablero();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        tablero.paint(g2d);
        paintPieces(g2d, juego.getListaPiezas());
        
         if (juego.getPiezaSeleccionada() != null)
            paintMoves(g2d);
        
    }
    
    public void paintPieces(Graphics2D g2d, List<Pieza> pieces) {
        for (Pieza piece : pieces) {
            piece.paint(g2d);
        }
    }
    
    public void paintMoves(Graphics2D g2d) {
        for (Position move : juego.getPosibleMoves()) {
            for (Pieza pieza : juego.getListaPiezas()) {
                if (move.getX() == pieza.getX() && move.getY() == pieza.getY()) { 
                    paintThreat(g2d, move);    
                } 
            }
            paintMove(g2d, move);
        }
    }
    
    private void paintMove(Graphics2D  g2d, Position move) {
        g2d.setColor(Color.GRAY);
        g2d.fillOval((move.getX() * Const.SQR_SIDE) + 20, (move.getY() * Const.SQR_SIDE) + 20, 20, 20);
     }

    private void paintThreat(Graphics2D  g2d, Position move) {
        g2d.setColor(Color.RED);
        g2d.fillRect(move.getX() * Const.SQR_SIDE, move.getY() * Const.SQR_SIDE, Const.SQR_SIDE, 4);
        g2d.fillRect(move.getX() * Const.SQR_SIDE, move.getY() * Const.SQR_SIDE, 4, Const.SQR_SIDE);                    
        g2d.fillRect((move.getX() * Const.SQR_SIDE) + (Const.SQR_SIDE - 4), move.getY() * Const.SQR_SIDE, 4, Const.SQR_SIDE);
        g2d.fillRect(move.getX() * Const.SQR_SIDE, (move.getY() * Const.SQR_SIDE) + (Const.SQR_SIDE - 4), Const.SQR_SIDE, 4);
    }
}
