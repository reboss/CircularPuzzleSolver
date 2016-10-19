/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import src.Searching.SearchAlgorithm;
import java.util.Arrays;
import java.util.List;
import src.Searching.SearchAlgorithm;

/**
 *
 * @author reboss
 */
public class Heuristic implements SearchAlgorithm.Functions<Board>{
    
    private int N;
    private int n;
    private int[] correctOrder;
    
    public Heuristic(int N, int n){
        this.N = N;
        this.n = n;
        this.correctOrder = new int[N];

        int num = 1;
        int pos = 0;
        
        while (pos<(N-1)){
            for (int j=0; j<n; j++){
                correctOrder[pos] = num;
                pos++;
            }
            num++;
        }
        correctOrder[N-1] = 0;
    }
    
    @Override
    public double cCost(Board value) {
        return 1;
    }

    @Override
    public double hCost(Board value) {
        int i = 0;
        int nCount;
        int outOfPlace = 0;
        int[] board = value.getSmallTiles();
        
        while (i < N){
            nCount = 1;
            int currentValue = board[i];

            while   ( board[(i + 1) % N] == currentValue ||
                    ( board[(i + 1) % N] == 0 ) ||
                    ( board[i % N] == 0 && board[(i+1) % N] == currentValue)){ 
                i++;
                nCount++;
            }
            if (nCount != n){
                // If current position is a zero AND current position - 1 is
                // equal to n, then 
                // the nth tiles are sorted followed by the open cell
                if (board[i%N] == 0 && board[(i+N-1) % N] == n){}
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
//            if (i >= N && (nCount == n || nCount == n + 1))
//                outOfPlace--; 
            i++;
        }
        return outOfPlace;
    }
    

    @Override
    public boolean goal(Board value) {
        if (hCost(value) == 0){
            return correctOrder(value);
        }
        return false;
    }

    @Override
    public List explore(Board from) {
        return Arrays.asList(from.getMoves());
    }
    
    private boolean correctOrder(Board value){
        int[] board = value.getSmallTiles();
        int boardPos = value.getOpenTile() + 1;
        
        for (int pos=0; pos<N; pos++){
            if (board[boardPos % N] != correctOrder[pos]){
                return false;
            }
            boardPos++;
        }
        
        return true;
    }
    
}
