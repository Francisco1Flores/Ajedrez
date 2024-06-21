package ajedrez2.logic;

import ajedrez2.Const;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;


public class Pieza {
    
    private PieceName name;
    private Position position;
    private List<Position> movesList;
    private boolean alive;
    private int x;
    private int y;
    private int xToPaint;
    private int yToPaint;
    private int previousX;
    private int previousY;
    private final boolean color;
    private int movements;
    private final Juego juego;
    
    private BufferedImage sprite;
    
    public Pieza(PieceName name, int x, int y, boolean color, Juego juego) {
        
        this.juego = juego;
        this.name = name;
        position = new Position(x,y);
        this.x = x;
        this.y = y;
        xToPaint = this.x * Const.SQR_SIDE;
        yToPaint = this.y * Const.SQR_SIDE;
        previousX = x;
        previousY = y;
        this.color = color;
        movements = 0;
        alive = true;
        
        findPosibleMoves();
        
        loadSprite();
    }
    
    public PieceName getName(){
        return name;
    }
    
    public void setX(int x) {
        this.x = x;
        position.setX(x);
    }
    
    public void setY(int y) {
        this.y = y;
        position.setY(y);
    }
    
    public void setXY(int x, int y, boolean t) {
        Pieza pieceInSquare = Juego.findByPosition(x, y);
        if (pieceInSquare != null)
            pieceInSquare.take();
              
        previousX = this.x;
        previousY = this.y;
        this.x = x;
        this.y = y;
        if ((this.y != previousY || this.x != previousX) && t) {
            movements++;
            juego.setTurn(!juego.getTurn());
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setPosition(int x, int y) {
        position.setX(x);
        position.setY(y);
    }
    
    public Position getPosition() {
        return position;
    }
    
    public int getPreviousX() {
        return previousX;
    }
    
    public int getPreviousY() {
        return previousY;
    }
    
    public int getMovements() {
        return movements;
    }
    
    public boolean isWhite() {
        return color;
    }
    
    public void paint(Graphics2D g) {
        g.drawImage(sprite, xToPaint, yToPaint, null);
    }

    public void move(int x, int y) {
        xToPaint = (x / Const.SQR_SIDE) * Const.SQR_SIDE;
        yToPaint = ((y - Const.SUP_BAR_HEIGHT)  / Const.SQR_SIDE) * Const.SQR_SIDE;
    }
    
    public void drag(int x, int y) {
        xToPaint = x - Const.H_SQR_SIDE;
        yToPaint = y - (Const.H_SQR_SIDE_Y + Const.SUP_BAR_HEIGHT);
    }
    
    void simulateMove(int x, int y, Pieza piece){
        if (piece != null) {
            piece.simulateDeath();
        }
        previousX = this.x;
        previousY = this.y;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    private void simulateDeath() {
        alive = false;
    }

    public void backMove(Pieza piece) {
        this.x = previousX;
        this.y = previousY;
        if (piece != null) {
            piece.cancelDeath();
        }
    }

    private void cancelDeath() {
        alive = true;
    }

    public void take() {
        Juego.getListaPiezas().remove(this);      
    }
    
    public List<Position> getPosibleMoves() {
        return movesList;
    }

    public void findPosibleMoves() {
        movesList = Moves.getMoves(this, Juego.getListaPiezas());
    }

    void setMovements(int i) {
        movements = i;
    }
    
    public void died() {
        Juego.getListaPiezas().remove(this);
    }
    
    private void loadSprite() {
        if (this.color) {
            try {
            sprite = ImageIO.read(new File("Sprites/" + name.toString() + "_W.png"));
            }
            catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        else {
            try {
            sprite = ImageIO.read(new File("Sprites/" + name.toString() + "_B.png"));
            }
            catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } 
    }
}
