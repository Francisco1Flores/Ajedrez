package ajedrez2.gui;

import ajedrez2.Const;
import ajedrez2.logic.Juego;
import java.awt.Color;
import java.awt.Graphics2D;


public class Tablero {
    
    private Juego juego;
    
    //public int[][] tableroCoor = new int[8][8];
    
    // Squre colors
    private final Color BROWN = new Color(169,119,0);
    private final Color CREAM = new Color(255,255,153);
    
    
    public Tablero() {}
    
    public void paint(Graphics2D g) {
         
        for (int i = 0, y = 0; i < 8; i++, y += Const.SQR_SIDE) {
            for (int j = 0, x = 0; j < 8; j++, x += Const.SQR_SIDE) {
                    
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        g.setColor(CREAM);
                        g.fillRect(x, y, Const.SQR_SIDE, Const.SQR_SIDE);
                    }
                    else {
                        g.setColor(BROWN);
                        g.fillRect(x, y, Const.SQR_SIDE, Const.SQR_SIDE);
                    }
                }
                else {
                    if (j % 2 == 0) {
                        g.setColor(BROWN);
                        g.fillRect(x, y, Const.SQR_SIDE, Const.SQR_SIDE);
                    }
                    else {
                        g.setColor(CREAM);
                        g.fillRect(x, y, Const.SQR_SIDE, Const.SQR_SIDE);
                    }
                } 
            }
        }
    }  
}
