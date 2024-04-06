/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrez2.logic;

/**
 *
 * @author francisco
 */
public class Position {
    int x,y;
    
    public Position(int x, int y) {
       this.x = x;
       this.y = y;
    }
    public void setX(int x) {
       this.x = x;
    }
   
    public void setY(int y) {
       this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void newPo(int x, int y){
        this.x = x;
        this.y = y;
    }
}
