package ajedrez2.logic;

import java.util.ArrayList;
import java.util.List;

public class Moves {
    
    //private List<Position> moves = new ArrayList();
    
    private static final int[][] cm = {{2,1}, {1,2}, {-1,2}, {-2,1}, {-2,-1}, {-1,-2}, {1,-2}, {2,-1}};
    
    
    
    public static List<Position> getMoves(Pieza pieza, List piezas) {
        
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
        moves.add(new Position(8,8));
        
        boolean pieceInFront = false;
        boolean PieceInFront2Sqr = false;
        
        // Verifica si hay una pieza adelante del peon a 1 o 2 casillas
        for (Pieza tPieza : piezas) {
            if (pieza.getColor()) {
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() - 1)
                    pieceInFront = true;
                
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() - 2)
                    PieceInFront2Sqr = true;
                
            } else {
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() + 1)
                    pieceInFront = true;
                
                
                if (tPieza.getX() == pieza.getX() && tPieza.getY() == pieza.getY() + 2)
                    PieceInFront2Sqr = true;
            }
        }
        
        
        if (!pieceInFront) {
            if (pieza.getMovements() == 0) {
                if (pieza.getColor()) {
                    moves.add(new Position(pieza.getX(),pieza.getY() - 1));
                    if (!PieceInFront2Sqr)
                        moves.add(new Position(pieza.getX(),pieza.getY() - 2));
                } else {
                    moves.add(new Position(pieza.getX(),pieza.getY() + 1));
                    if (!PieceInFront2Sqr)
                        moves.add(new Position(pieza.getX(),pieza.getY() + 2));
                }
            } else {
                if (pieza.getColor()) {
                    moves.add(new Position(pieza.getX(),pieza.getY() - 1));

                } else {
                    moves.add(new Position(pieza.getX(),pieza.getY() + 1));
                }
            }
        }
        
        // Agrega movimiento de ataque si hay una pieza en diagonal
        for (Pieza tPieza : piezas) {
            if (pieza.getColor()) {
                if (tPieza.getX() == pieza.getX() - 1 && tPieza.getY() == pieza.getY() - 1 
                        && tPieza.getColor() != pieza.getColor()) {
                    moves.add(new Position(pieza.getX() - 1,pieza.getY() - 1));
                }
                if (tPieza.getX() == pieza.getX() + 1 && tPieza.getY() == pieza.getY() - 1 
                        && tPieza.getColor() != pieza.getColor()) {
                    moves.add(new Position(pieza.getX() + 1,pieza.getY() - 1));
                }
            } else {
                if (tPieza.getX() == pieza.getX() - 1 && tPieza.getY() == pieza.getY() + 1 
                        && tPieza.getColor() != pieza.getColor()) {
                    moves.add(new Position(pieza.getX() - 1,pieza.getY() + 1));
                }
                if (tPieza.getX() == pieza.getX() + 1 && tPieza.getY() == pieza.getY() + 1 
                        && tPieza.getColor() != pieza.getColor()) {
                    moves.add(new Position(pieza.getX() + 1,pieza.getY() + 1));
                }
            }   
        }
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
            
            for (Pieza piezaC : piezas) {
                if (x == piezaC.getX() && y == piezaC.getY()) {
                    samePosition = true;
                    if (piezaC.getColor() != pieza.getColor()) {
                        difColor = true;
                    }
                }
            }
            
            if (!samePosition || (samePosition && difColor)) moves.add(new Position(x, y));
           
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
                            && tPieza.getColor() == pieza.getColor()) {
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
    
    public static Pieza findByposition(List<Pieza> piezas, int x, int y) {
        
        for (Pieza actualPieza : piezas) {
            if (actualPieza.getX() == x && actualPieza.getY() == y)
                return actualPieza;
        }
        return null;    
    }
    
    private static void addDiagonalMoves(Pieza pieza, List<Pieza> piezas,  List<Position> moves) {
        for (int i = 0; i < 4; i++) {
            boolean moveIsPosible = true;
            int c = 1;
            int x = 0;
            int y = 0;
            while(x >= 0 && x <=7 && y >= 0 && y <=7) {
                if (i == 0) {
                x = pieza.getX() - c;
                y = pieza.getY() - c;
                } else if (i == 1) {
                x = pieza.getX() + c;
                y = pieza.getY() - c;                
                } else if (i == 2) {
                x = pieza.getX() - c;
                y = pieza.getY() + c;                
                } else {
                x = pieza.getX() + c;
                y = pieza.getY() + c;                                
                }
                for (Pieza piezaC : piezas) {
                    if (moveIsPosible) {
                        if (x == piezaC.getX() && y == piezaC.getY()) {
                            if (piezaC.getColor() == pieza.getColor()) {
                                moveIsPosible = false;
                                c++;
                            }
                            if (piezaC.getColor() != pieza.getColor()) {
                                moves.add(new Position(x, y));
                                moveIsPosible = false;
                                c++;
                            }
                        }
                    }
                }                      
                if (moveIsPosible) {
                    moves.add(new Position(x, y));
                    c++;
                } else c++;
            }
        }        
    }
    
    private static void addRectMoves(Pieza pieza, List<Pieza> piezas,  List<Position> moves) {
        
        for (int i = 0; i < 4; i++) {

            boolean moveIsPosible = true;
            int c = 1;
            int x = 0;
            int y = 0;
            while(x >= 0 && x <=7 && y >= 0 && y <=7) {

                if (i == 0) {
                x = pieza.getX() - c;
                y = pieza.getY();
                } else if (i == 1) {
                x = pieza.getX() + c;
                y = pieza.getY();
                } else if (i == 2) {
                x = pieza.getX();
                y = pieza.getY() - c;
                } else {
                x = pieza.getX();
                y = pieza.getY() + c;
                }
                for (Pieza piezaC : piezas) {
                    if (moveIsPosible) {
                        if (x == piezaC.getX() && y == piezaC.getY()) {
                            if (piezaC.getColor() == pieza.getColor()) {
                                moveIsPosible = false;
                                c++;
                            }
                            if (piezaC.getColor() != pieza.getColor()) {
                                moves.add(new Position(x, y));
                                moveIsPosible = false;
                                c++;
                            }
                        }
                    }
                }                      
                if (moveIsPosible) {
                    moves.add(new Position(x, y));
                    c++;
                }else c++;
            }
        }
    }
    
}