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
            i++;
        }
        return outOfPlace;
    }
    

    @Override
    public boolean goal(Board value) {
        return value.isSorted();
    }

    @Override
    public List explore(Board from) {
        return Arrays.asList(from.getMoves());
    }
}
 
