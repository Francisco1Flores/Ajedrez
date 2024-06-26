package ajedrez2.logic;

import ajedrez2.Const;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;


public class Juego {

    private static List<Pieza> piezas = new ArrayList();
    
    private Pieza piezaSeleccionada = null;
    private static Pieza lastMovedPiece = null;
    
    List<Position> posibleMoves;
    
    private static boolean turn = true;
    
    private final boolean white = true;
    private final boolean black = false;
    
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
            //posibleMoves = piezaSeleccionada.getPosibleMoves();
        }
    }
    
    public Pieza getPiezaSeleccionada() {
        return piezaSeleccionada;
    }
    
    public static Pieza getLastMovedPiece() {
        return lastMovedPiece;
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
        
        setPiezaSeleccionada(getPieza(e.getX(), e.getY()));
                
        if (piezaSeleccionada != null) {
            //todo borrar print
            System.out.println("Position x: " + piezaSeleccionada.getX() + " y: " + piezaSeleccionada.getY());
            //todo borrar print en stream
            piezaSeleccionada.getPosibleMoves().forEach(m -> System.out.println(m.getX() + "    " + m.getY()));
            if (piezaSeleccionada.isWhite() != turn) 
                piezaSeleccionada = null;     
        }
        if (piezaSeleccionada != null) {
            verifyIfMovesCoverCheck(piezaSeleccionada);
        }
    }
    
    public void releasePiece(MouseEvent e) {
        if (piezaSeleccionada != null) {
            
            for (Position move : posibleMoves) {
                // todo borrar print
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
                    
                    if (piezaSeleccionada.getName().equals(PieceName.PAWN))
                        enPassant();

                    
                    lastMovedPiece = piezaSeleccionada;
                            
                    piezaSeleccionada.findPosibleMoves();
                    //todo borrar print
                    System.out.println(piezaSeleccionada.getMovements());

                    setPiezaSeleccionada(null);

                    if (isCheck()) {
                        if (isCheckMate()) {
                            finishGame();
                        } else {
                            System.out.println("No hay jaque mate");
                        }
                    } else {
                        if (isCheckMate()) {
                            System.out.println("TABLAS");
                        }
                    }

                // Verifica que la pieza se solto en una casilla a la que no podia moverse    
                } else {
                    // Si hay una pieza seleccionada y se suelta en una casilla a la que no
                    // puede moverse se vuelve la pieza a su casilla original
                    if (piezaSeleccionada != null) {
                        piezaSeleccionada.move(piezaSeleccionada.getX() * Const.SQR_SIDE,
                                piezaSeleccionada.getY() * Const.SQR_SIDE /*+ Const.SUP_BAR_HEIGHT*/);
                    }
                }
            }    
        }
    }

    //todo implementar finalización del juego
    private void finishGame() {
        System.out.println("------checkmate-------");
        String winner = (!turn) ? "BLANCAS" : "NEGRAS";
        System.out.println(winner +" GANAN");
    }

    //todo implementar jaque mate
    private boolean isCheckMate() {
        for(Pieza piece : piezas) {
            if(piece.isWhite() == turn) {
                piece.findPosibleMoves();
                verifyIfMovesCoverCheck(piece);
                if (piece.getPosibleMoves().size() > 1) {
                    return false;
                }
            }
        }
        return true;
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
    }
    
    public void castle() {
        if (piezaSeleccionada != null) {
                           
            // Verifica que el movimiento haya sido de enroque corto
            if (piezaSeleccionada.getX() == piezaSeleccionada.getPreviousX() + 2
                && piezaSeleccionada.getY() == piezaSeleccionada.getPreviousY()) {

                Pieza torre = Moves.findByposition(piezas, piezaSeleccionada.getX() + 1, piezaSeleccionada.getY());
                torre.move(5 * Const.SQR_SIDE, torre.getY() * Const.SQR_SIDE);
                torre.setXY(5, torre.getY(), false);
                torre = null;

            }
            // Verifica que el movimiento haya sido de enroque largo        
            if (piezaSeleccionada.getX() == piezaSeleccionada.getPreviousX() - 2
                && piezaSeleccionada.getY() == piezaSeleccionada.getPreviousY()) {

                Pieza torre = Moves.findByposition(piezas, piezaSeleccionada.getX() - 2, piezaSeleccionada.getY());
                torre.move(3 * Const.SQR_SIDE, torre.getY() * Const.SQR_SIDE);
                torre.setXY(3, torre.getY(), false);
                torre = null;                    
            }     
               
        }     
    }

    // busca si algun movimiento de alguna pieza coincide con la posicion del rey contrario
    public boolean isCheck() {
        Pieza king = turn ? whiteKing : blackKing;
        for (Pieza cPieza : piezas) {
            cPieza.findPosibleMoves();
            if (cPieza.isAlive()) {
                for (Position move : cPieza.getPosibleMoves()) {
                    if (move.getX() == king.getX() && move.getY() == king.getY() && cPieza.isWhite() != turn) {
                        System.out.println("encontrado jaque");
                        return true;
                    }
                }
            }
        }
        System.out.println("no hay jaque");
        return false;               
    }
    
    public void dragPiece(MouseEvent e) {
        if (piezaSeleccionada != null && turn == piezaSeleccionada.isWhite()) {
            piezaSeleccionada.drag(e.getX(), e.getY());
        }
    }

    private void verifyIfMovesCoverCheck(Pieza piece) {
        if (piece != null) {
            List<Position> coverMoves = new ArrayList<>();
            coverMoves.add(new Position(8,8));
            for (Position move : piece.getPosibleMoves()) {
                if (coverCheck(move, piece)) {
                    coverMoves.add(move);
                }
            }
            piece.setPosibleMoves(coverMoves);
            posibleMoves = coverMoves;
        }
    }

    private boolean coverCheck(Position move, Pieza piece) {
        piece.simulateMove(move.getX(), move.getY(), findByPosition(move.getX(), move.getY()));
        if (!isCheck()) {
            piece.backMove(findByPosition(move.getX(), move.getY()));
            return true;
        }
        piece.backMove(findByPosition(move.getX(), move.getY()));
        return false;
    }
    
    public static Pieza findByPosition(int x, int y) {
        for (Pieza actualPieza : piezas) {
            if (actualPieza.getX() == x && actualPieza.getY() == y)
                return actualPieza;
        }
        return null;
    }
    
    public void enPassant() {
        if (piezaSeleccionada != null) {
            Pieza backPawn;
            if (piezaSeleccionada.isWhite()) {
                if (piezaSeleccionada.getY() + 1 == piezaSeleccionada.getPreviousY() &&
                    (piezaSeleccionada.getX() + 1 == piezaSeleccionada.getPreviousX() ||
                     piezaSeleccionada.getX() - 1 == piezaSeleccionada.getPreviousX())) {
                    backPawn = findByPosition(piezaSeleccionada.getX(), piezaSeleccionada.getY() + 1);
                    if (backPawn != null) {
                        if (backPawn.getName().equals(PieceName.PAWN) 
                                && piezaSeleccionada.isWhite() != backPawn.isWhite()
                                && backPawn.getPreviousY() == backPawn.getY() - 2)
                            backPawn.died();
                    }
                }
            } else {
                if (piezaSeleccionada.getY() - 1 == piezaSeleccionada.getPreviousY() &&
                    (piezaSeleccionada.getX() + 1 == piezaSeleccionada.getPreviousX() ||
                     piezaSeleccionada.getX() - 1 == piezaSeleccionada.getPreviousX())) {
                    backPawn = findByPosition(piezaSeleccionada.getX(), piezaSeleccionada.getY() - 1);
                    if (backPawn != null)
                        if (backPawn.getName().equals(PieceName.PAWN) 
                                && piezaSeleccionada.isWhite() != backPawn.isWhite() 
                                && backPawn.getPreviousY() == backPawn.getY() + 2)
                            backPawn.died();
                }
            }          
        }
    }
    
    private boolean isInRange(int x, int y, int xInf, int yInf) {
        return (x >= (xInf * Const.SQR_SIDE)
                && x <= ((xInf * Const.SQR_SIDE) + Const.SQR_SIDE)
                && y >= (yInf * Const.SQR_SIDE)
                && y <= ((yInf * Const.SQR_SIDE) + Const.SQR_SIDE));
    }
}
