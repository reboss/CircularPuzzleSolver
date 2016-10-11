/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.List;

/**
 *
 * @author reboss
 */
public class Heuristic <Board> implements AStar.Functions{
    
    private int N;
    private int n;
    
    public Heuristic(int N, int n){
        this.N = N;
        this.n = n;
    }
    
    @Override
    public double cCost(Object value) {
        return 1;
    }

    @Override
    public double hCost(Object value) {
        int i = 0;
        int nCount;
        int outOfPlace = 0;
        
        while (i < N){
            nCount = 1;
            int currentValue = board.getSmallTiles()[i];

            while   ( tiles[(i + 1) % N] == currentValue ||
                    ( tiles[(i + 1) % N] == 0 ) ||
                    ( tiles[i % N] == 0 && tiles[(i+1) % N] == currentValue)){ 
                i++;
                nCount++;
            }
            if (nCount != n){
                //System.out.println(tiles[i%N]);
                // If current position is a zero AND current position - 1 is
                // equal to n, then 
                // the nth tiles are sorted followed by the open cell
                if (tiles[i%N] == 0 && tiles[(i+N-1) % N] == n){}
                else
                    outOfPlace++;
            }
            // account for incorrectly counting a wrapped yet sorted tile
            // when starting at index zero.  
            // The first time the array,
            // {1, 2, 2, 3, 3, 0, 1}, 
            // is iterated through, the 1 at index 0 will be counted 
            // as out of place because the algorithm doesn't wrap backwards to
            // check sorting.  
            // Once the iterator makes it to the end of the array, it will
            // wrap back around and see that the 1 is in fact sorted and 
            // will decrement the outOfPlace heuristic below. 
            if (i >= N && (nCount == n || nCount == n + 1))
                outOfPlace--; 
            i++;
        }
        return outOfPlace;
    }
    

    @Override
    public boolean goal(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List explore(Object from) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
