package ajedrez2.logic;

import java.util.ArrayList;
import java.util.List;

public class Moves {

    private static final int[][] cm = {{2,1}, {1,2}, {-1,2}, {-2,1}, {-2,-1}, {-1,-2}, {1,-2}, {2,-1}};

    public static List<Position> getMoves(Pieza pieza, List<Pieza> piezas) {
        if (pieza.getName().equals(PieceName.PAWN)) {
            return getPeonMoves(pieza, piezas);
        }
        if (pieza.getName().equals(PieceName.ROOK)) {
            return getTorreMoves(pieza, piezas);
        }
        if (pieza.getName().equals(PieceName.KNIGHT)) {
            return getCaballoMoves(pieza, piezas);    
        }
        if (pieza.getName().equals(PieceName.BISHOP)) {
            return getAlfilMoves(pieza, piezas);  
        }
        if (pieza.getName().equals(PieceName.QUEEN)) {
            return getDamaMoves(pieza, piezas);
        }
        return getReyMoves(pieza, piezas);
    }
    

    private static List<Position> getPeonMoves(Pieza pieza, List<Pieza> piezas) {
            
        List<Position> moves = new ArrayList();
        
        boolean pieceInFront = false;
        boolean pieceInFront2Sqr = false;
        
        // Verifica si hay una pieza adelante del peon a 1 o 2 casillas
        for (Pieza tPieza : piezas) {
            if (pieza.isWhite()) {
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() - 1)
                    pieceInFront = true;
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() - 2)
                    pieceInFront2Sqr = true;
            } else {
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() + 1)
                    pieceInFront = true;
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() + 2)
                    pieceInFront2Sqr = true;
            }
        }
        
        
        if (!pieceInFront) {
            if (pieza.getMovements() == 0) {
                if (pieza.isWhite()) {
                    moves.add(new Position(pieza.getX(),pieza.getY() - 1));
                    if (!pieceInFront2Sqr)
                        moves.add(new Position(pieza.getX(),pieza.getY() - 2));
                } else {
                    moves.add(new Position(pieza.getX(),pieza.getY() + 1));
                    if (!pieceInFront2Sqr)
                        moves.add(new Position(pieza.getX(),pieza.getY() + 2));
                }
            } else {
                if (pieza.isWhite()) {
                    moves.add(new Position(pieza.getX(),pieza.getY() - 1));
                } else {
                    moves.add(new Position(pieza.getX(),pieza.getY() + 1));
                }
            }
        }
        
        // Agrega movimiento de ataque si hay una pieza en diagonal
        for (Pieza tPieza : piezas) {
            if (pieza.isWhite()) {
                if (tPieza.getX() == pieza.getX() - 1 && tPieza.getY() == pieza.getY() - 1 
                        && tPieza.isWhite() != pieza.isWhite()) {
                    moves.add(new Position(pieza.getX() - 1,pieza.getY() - 1));
                }
                if (tPieza.getX() == pieza.getX() + 1 && tPieza.getY() == pieza.getY() - 1 
                        && tPieza.isWhite() != pieza.isWhite()) {
                    moves.add(new Position(pieza.getX() + 1,pieza.getY() - 1));
                }
            } else {
                if (tPieza.getX() == pieza.getX() - 1 && tPieza.getY() == pieza.getY() + 1 
                        && tPieza.isWhite() != pieza.isWhite()) {
                    moves.add(new Position(pieza.getX() - 1,pieza.getY() + 1));
                }
                if (tPieza.getX() == pieza.getX() + 1 && tPieza.getY() == pieza.getY() + 1 
                        && tPieza.isWhite() != pieza.isWhite()) {
                    moves.add(new Position(pieza.getX() + 1,pieza.getY() + 1));
                }
            }   
        }
        onPassant(pieza, piezas, moves);
        return moves;
    }

    private static List<Position> getTorreMoves(Pieza pieza, List<Pieza> piezas) {
        List<Position> moves = new ArrayList();
        addRectMoves(pieza, piezas, moves);
        return moves;          
    }

    private static List<Position> getCaballoMoves(Pieza pieza, List<Pieza> piezas) {
        List<Position> moves = new ArrayList();
        
        for(int i = 0; i < 8; i++ ) {
            boolean samePosition = false;
            boolean difColor = false;
            int x = pieza.getX() + cm[i][0];
            int y = pieza.getY() + cm[i][1];

            if (x >= 0 && x < 7 && y >= 0 && y < 7) {
                for (Pieza piezaC : piezas) {
                    if (x == piezaC.getX() && y == piezaC.getY()) {
                        samePosition = true;
                        if (piezaC.isWhite() != pieza.isWhite()) {
                            difColor = true;
                        }
                    }
                }
                if (!samePosition || difColor) moves.add(new Position(x, y));
            }
        }
        return moves;
    }
    

    private static List<Position> getAlfilMoves(Pieza pieza, List<Pieza> piezas) {
        List<Position> moves = new ArrayList();
        addDiagonalMoves(pieza, piezas, moves);    
        return moves;
    }
    

    private static List<Position> getDamaMoves(Pieza pieza, List<Pieza> piezas) {
        List<Position> moves = new ArrayList();
        
        addDiagonalMoves(pieza, piezas, moves);
        addRectMoves(pieza, piezas, moves);
     
        return moves;
    }
    

    private static List<Position> getReyMoves(Pieza pieza, List<Pieza> piezas) {
        List<Position> moves = new ArrayList();
        moves.add(new Position(8,8));
        
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                boolean samePositionAndColor = false;
                int x = pieza.getX() + j;
                int y = pieza.getY() + i;
                
                if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
                    for (Pieza tPieza : piezas) {
                        if (tPieza.getX() == x && tPieza.getY() == y
                            && tPieza.isWhite() == pieza.isWhite()) {
                            samePositionAndColor = true;
                        }
                    }
                    if (!samePositionAndColor)
                        moves.add(new Position(pieza.getX() + j, pieza.getY() + i)); 
                }
            }
        }
        
        // Verifica si el rey se puede enrocar y si es posible agrega el movimiento del enroque
        if (pieza.getMovements() == 0) {
            if (enroqueIsPosible(pieza, findByposition(piezas, pieza.getX() + 3, pieza.getY()), piezas)) {
                moves.add(new Position(pieza.getX() + 2, pieza.getY()));
            }
            if (enroqueIsPosible(pieza, findByposition(piezas, pieza.getX() - 4, pieza.getY()), piezas)) {
                moves.add(new Position(pieza.getX() - 2, pieza.getY()));
            }
        }
        return moves;
    }
    
    public static boolean enroqueIsPosible(Pieza rey, Pieza torre, List<Pieza> piezas) {
        
        if (torre == null)
            return false;
        if (torre.getMovements() != 0)
            return false;
        
        // Verifica si el enroque es corto o largo
        if (rey.getX() < torre.getX()) {
            for (Pieza actualPieza : piezas) {

                // Verifica si hay alguna pieza entre el rey y la torre en el enroque corto
                if ((actualPieza.getX() == 5 && actualPieza.getY() == torre.getY())
                    || (actualPieza.getX() == 6 && actualPieza.getY() == torre.getY())) {
                    return false;
                }
            }
        } else {
            for (Pieza actualPieza : piezas) {

                // Verifica si hay alguna pieza entre el rey y la torre en el enroque largo
                if ((actualPieza.getX() == 1 && actualPieza.getY() == torre.getY())
                    || (actualPieza.getX() == 2 && actualPieza.getY() == torre.getY())
                    || (actualPieza.getX() == 3 && actualPieza.getY() == torre.getY())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void addDiagonalMoves(Pieza pieza, List<Pieza> piezas,  List<Position> moves) {
        for (int i = 0; i < 4; i++) {
            boolean moveIsPosible = true;
            boolean samePosition = false;
            boolean difColor = false;
            int c = 1;
            int x = 0;
            int y = 0;
            while(x >= 0 && x < 7 && y >= 0 && y < 7 && moveIsPosible) {

                x = (i == 0 || i == 2) ? pieza.getX() - c : pieza.getX() + c;
                y = (i < 2) ? pieza.getY() - c : pieza.getY() + c;

                if(x >= 0 && x <= 7 && y >= 0 && y <= 7) {
                    for (Pieza piezaC : piezas) {
                        if (x == piezaC.getX() && y == piezaC.getY()) {
                            samePosition = true;
                            if (piezaC.isWhite() != pieza.isWhite()) {
                                difColor = true;
                            }
                        }
                    }
                    if (samePosition) {
                        if (difColor) {
                            moves.add(new Position(x, y));
                        }
                        moveIsPosible = false;
                    } else {
                        moves.add(new Position(x, y));
                    }
                }
                c++;
            }
        }        
    }
    
    private static void addRectMoves(Pieza pieza, List<Pieza> piezas,  List<Position> moves) {
        for (int i = 0; i < 4; i++) {
            boolean moveIsPosible = true;
            boolean samePosition = false;
            boolean difColor = false;
            int c = 1;
            int x = 0;
            int y = 0;
            while (x >= 0 && x <= 7 && y >= 0 && y <= 7 && moveIsPosible) {

                x = (i == 0) ? pieza.getX() - c :
                    (i == 1) ? pieza.getX() + c :
                    pieza.getX();
                y = (i == 2) ? pieza.getY() - c :
                    (i == 3) ? pieza.getY() + c :
                    pieza.getY();

                if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
                    for (Pieza piezaC : piezas) {
                        if (x == piezaC.getX() && y == piezaC.getY()) {
                            samePosition = true;
                            if (piezaC.isWhite() != pieza.isWhite()) {
                                difColor = true;
                            }
                        }
                    }
                    if (samePosition) {
                        if (difColor) {
                            moves.add(new Position(x, y));
                        }
                        moveIsPosible = false;
                    } else {
                        moves.add(new Position(x, y));
                    }
                }
                c++;
            }
        }
    }

    private static void onPassant(Pieza pieza, List<Pieza> piezas, List<Position> moves) {
        if (pieza.isWhite()) {
            if (pieza.getY() == 3) {
                for (int x = pieza.getX() - 1; x <= pieza.getX() +1; x += 2) {
                    Pieza sidePawn = Juego.findByPosition(x, pieza.getY());
                    if (sidePawn != null && Juego.getLastMovedPiece() != null) {
                        if (sidePawn.getName().equals(PieceName.PAWN) &&
                            sidePawn.getMovements() == 1 &&
                            Juego.getLastMovedPiece() == sidePawn) {
                            moves.add(new Position(x, pieza.getY() - 1));
                        }
                    }
                }
            }
        } else {
            if (pieza.getY() == 4) {
                for (int x = pieza.getX() - 1; x <= pieza.getX() +1; x += 2) {
                    Pieza sidePawn = Juego.findByPosition(x, pieza.getY());
                    if (sidePawn != null&& Juego.getLastMovedPiece() != null) {
                        if (sidePawn.getName().equals(PieceName.PAWN) &&
                            sidePawn.getMovements() == 1 &&
                            Juego.getLastMovedPiece() == sidePawn) {
                            moves.add(new Position(x, pieza.getY() + 1));
                        }
                    }
                }
            }
        }
    }

    public static Pieza findByposition(List<Pieza> piezas, int x, int y) {
        for (Pieza actualPieza : piezas) {
            if (actualPieza.getX() == x && actualPieza.getY() == y)
                return actualPieza;
        }
        return null;
    }
}

