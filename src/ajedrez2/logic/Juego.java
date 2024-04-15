package ajedrez2.logic;


import ajedrez2.Const;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class Juego {
    
    
    private static List<Pieza> piezas = new ArrayList();
    
    Pieza piezaSeleccionada = null;
    
    List<Position> posibleMoves;
    
    private static boolean turn = true;
    
    private final boolean white = true;
    private final boolean black = false;
    
    private boolean check = false;
    
    Pieza whiteKing;
    Pieza blackKing;
    
    public Juego() {
  
        piezas.add(new Pieza(PieceName.PAWN, 0, 6,white, this));
        piezas.add(new Pieza(PieceName.PAWN, 1, 6,white, this));
        piezas.add(new Pieza(PieceName.PAWN, 2, 6,white,this));
        piezas.add(new Pieza(PieceName.PAWN, 3, 6,white,this));
        piezas.add(new Pieza(PieceName.PAWN, 4, 6,white,this));
        piezas.add(new Pieza(PieceName.PAWN, 5, 6,white,this));
        piezas.add(new Pieza(PieceName.PAWN, 6, 6,white,this));
        piezas.add(new Pieza(PieceName.PAWN, 7, 6,white,this));
        piezas.add(new Pieza(PieceName.ROOK, 0, 7,white,this));
        piezas.add(new Pieza(PieceName.ROOK, 7, 7,white,this));
        piezas.add(new Pieza(PieceName.KNIGHT, 1, 7,white,this));
        piezas.add(new Pieza(PieceName.KNIGHT, 6, 7,white,this));
        piezas.add(new Pieza(PieceName.BISHOP, 2, 7,white,this));
        piezas.add(new Pieza(PieceName.BISHOP, 5, 7,white,this));
        piezas.add(new Pieza(PieceName.QUEEN, 3, 7,white,this));
        piezas.add(new Pieza(PieceName.KING, 4, 7,white,this));
        
        piezas.add(new Pieza(PieceName.PAWN, 0, 1,black,this));
        piezas.add(new Pieza(PieceName.PAWN, 1, 1,black,this));
        piezas.add(new Pieza(PieceName.PAWN, 2, 1,black,this));
        piezas.add(new Pieza(PieceName.PAWN, 3, 1,black,this));
        piezas.add(new Pieza(PieceName.PAWN, 4, 1,black,this));
        piezas.add(new Pieza(PieceName.PAWN, 5, 1,black,this));
        piezas.add(new Pieza(PieceName.PAWN, 6, 1,black,this));
        piezas.add(new Pieza(PieceName.PAWN, 7, 1,black,this));
        piezas.add(new Pieza(PieceName.ROOK, 0, 0,black,this));
        piezas.add(new Pieza(PieceName.ROOK, 7, 0,black,this));
        piezas.add(new Pieza(PieceName.KNIGHT, 1, 0,black,this));
        piezas.add(new Pieza(PieceName.KNIGHT, 6, 0,black,this));
        piezas.add(new Pieza(PieceName.BISHOP, 2, 0,black,this));
        piezas.add(new Pieza(PieceName.BISHOP, 5, 0,black,this));
        piezas.add(new Pieza(PieceName.QUEEN, 3, 0,black,this));
        piezas.add(new Pieza(PieceName.KING, 4, 0,black,this));
        
        whiteKing = piezas.get(15);
        blackKing = piezas.get(31);
        
    }
    
    public void setPiezaSeleccionada(Pieza pieza) {
        piezaSeleccionada = pieza;
        if (piezaSeleccionada != null) {
            piezaSeleccionada.findPosibleMoves();
            posibleMoves = piezaSeleccionada.getPosibleMoves();
        }
    }
    
    public Pieza getPiezaSeleccionada() {
        return piezaSeleccionada;
    }
    
    public static List<Pieza> getListaPiezas() {
        return piezas;
    }
    
    public boolean getTurn() {
        return turn;
    }
    
    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    
    public void selectPiece(MouseEvent e) {
        
        setPiezaSeleccionada(getPieza((int) e.getX(), (int) e.getY()));
                
        if (piezaSeleccionada != null) {
            System.out.println("Position x: " + piezaSeleccionada.getX() + " y: " + piezaSeleccionada.getY());
            piezaSeleccionada.getPosibleMoves().forEach(m -> System.out.println(m.getX() + "    " + m.getY()));
            if (piezaSeleccionada.getColor() != turn) 
                piezaSeleccionada = null;     
        }
        
        if (isCheck()){
            verifyIfMovesCoverCheck();
        }
            
    }
    
    public void releasePiece(MouseEvent e) {
     
        if (piezaSeleccionada != null) {
            
            for (Position move : posibleMoves) {

                System.out.println(move.getX() + "  " + move.getY());

                // Verfifica si al momento de soltar el boton del raton el cursor se
                // encuentre sobre una casilla a la que la pieza pueda moverse
                if (isInRange(e.getX(),e.getY(), move.getX(), move.getY())) {

                    piezaSeleccionada.move(e.getX(), e.getY());
                    

                    // Cambia las coordenadas de la pieza a las de la nueva casilla
                    
                    piezaSeleccionada.setXY(move.getX(), move.getY(), true);
                   

                    // Verifica si el movimiento fue un enroque y lo realiza 
                    if ((piezaSeleccionada.getName().equals(PieceName.KING)))
                        castle(); 
                    
                    piezaSeleccionada.findPosibleMoves();

                    System.out.println(piezaSeleccionada.getMovements());

                    setPiezaSeleccionada(null);

                // Verifica que la pieza se solto en una casilla a la que no podia moverse    
                } else {
                    // Si hay una pieza seleccionada y se suelta en una casilla a la que no
                    // puede moverse se vuelve la pieza a su casilla original
                    if (piezaSeleccionada != null) {
                        piezaSeleccionada.move(piezaSeleccionada.getX() * Const.SQR_SIDE,
                                piezaSeleccionada.getY() * Const.SQR_SIDE + Const.SUP_BAR_HEIGHT);
                    }
                }
            }    
        }
    }
    

    public Pieza getPieza(int x, int y) {
        for (Pieza pieza : piezas) {
            if (isInRange(x,y,pieza.getX(),pieza.getY()))
                return pieza;
        }
        return null;
    }
    
    public List<Position> getPosibleMoves() {
        
        return posibleMoves;
        //return pieza.getPosibleMoves(piezas);
    }
    
    
    public void castle() {
        if (piezaSeleccionada != null) {
                           
            // Verifica que el movimiento haya sido de enroque corto
            if (piezaSeleccionada.getX() == piezaSeleccionada.getPreviousX() + 2
                && piezaSeleccionada.getY() == piezaSeleccionada.getPreviousY()) {

                Pieza torre = Moves.findByposition(piezas, piezaSeleccionada.getX() + 1, piezaSeleccionada.getY());
                torre.move(5 * Const.SQR_SIDE, (torre.getY() * Const.SQR_SIDE) + Const.SUP_BAR_HEIGHT);
                torre.setXY(5, torre.getY(), false);
                torre = null;

            }
            // Verifica que el movimiento haya sido de enroque largo        
            if (piezaSeleccionada.getX() == piezaSeleccionada.getPreviousX() - 2
                && piezaSeleccionada.getY() == piezaSeleccionada.getPreviousY()) {

                Pieza torre = Moves.findByposition(piezas, piezaSeleccionada.getX() - 2, piezaSeleccionada.getY());
                torre.move(3 * Const.SQR_SIDE, (torre.getY() * Const.SQR_SIDE) + Const.SUP_BAR_HEIGHT);
                torre.setXY(3, torre.getY(), false);
                torre = null;                    
            }     
               
        }     
    }
    
    public boolean isCheckWhitSimulatedMoves() {
        Pieza king = turn ? whiteKing : blackKing;
        return false;
    }
    
    public boolean isCheck() {
        
        Pieza king = turn ? whiteKing : blackKing;
        
        for (Pieza cPieza : piezas) {
            cPieza.findPosibleMoves();
            for (Position move : cPieza.getPosibleMoves()) {
                if (move.getX() == king.getX() && move.getY() == king.getY() && cPieza.getColor() != turn) {
                    System.out.println("encontrado jaque");
                    check = true;
                    return true;
                }
            }
        }
        System.out.println("no hay jaque");
        check = false;
        return false;               
    }
    
    public void dragPiece(MouseEvent e) {
        
        if (piezaSeleccionada != null && turn == piezaSeleccionada.getColor()) {
            piezaSeleccionada.drag(e.getX(), e.getY());
        }
    }

    private void verifyIfMovesCoverCheck() {
        if (piezaSeleccionada != null) {
            
            List<Position> coverMoves = new ArrayList<>();
            coverMoves.add(new Position(8,8));
            
            // TODO verificar si al simular los movimientos el jaque se cubre
            // verificar si es jaque con pas posiciones simuladas de las piezas
            // y con el estado simulado (si el estado simulado es false sus movimientos no se tienen
            // en cuenta
            for (Position move : posibleMoves) {
                if (coverCheck(move)) {
                    coverMoves.add(move);
                }
            }
            
            posibleMoves = coverMoves;
            
        }
    }

    private boolean coverCheck(Position move) {
        //int realX = piezaSeleccionada.getX();
        //int realY = piezaSeleccionada.getY();
 
        piezaSeleccionada.simulateMove(move.getX(), move.getY(), findByPosition(move.getX(), move.getY()));
        if (!isCheck()) {
            piezaSeleccionada.backMove();
            return true;
        }
        piezaSeleccionada.backMove();
        return false;
    }
    
    public static Pieza findByPosition(int x, int y) {
        
        for (Pieza actualPieza : piezas) {
            if (actualPieza.getX() == x && actualPieza.getY() == y)
                return actualPieza;
        }
        return null;
    }
    
    /*public void onPassant() {
        if (piezaSeleccionada != null) {
            if (piezaSeleccionada.getName() == PieceName.PAWN &&
                    piezaSeleccionada.getY() == )
        }
    }*/
    
    private boolean isInRange(int x, int y, int xInf, int yInf) {
        return (x >= (xInf * Const.SQR_SIDE) 
                    && x <= ((xInf * Const.SQR_SIDE) + Const.SQR_SIDE)
                    && y >= (yInf * Const.SQR_SIDE) + Const.SUP_BAR_HEIGHT
                    && y <= ((yInf * Const.SQR_SIDE) + Const.SQR_SIDE) + Const.SUP_BAR_HEIGHT);
    }


        
    
    
}
