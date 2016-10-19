/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author reboss
 */
public class Board {

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Arrays.hashCode(this.bigTiles);
        hash = 37 * hash + Arrays.hashCode(this.smallTiles);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Board other = (Board) obj;
        if (!Arrays.equals(this.bigTiles, other.bigTiles)) {
            return false;
        }
        if (!Arrays.equals(this.smallTiles, other.smallTiles)) {
            return false;
        }
        return true;
    }
    
    private int[] bigTiles;
    private int[] smallTiles;
    private int openTile;
    private int N;
    private int n;
    
    public Board(int N, int n){
        bigTiles = new int[N];
        smallTiles = new int[N];
        this.n = n;
        this.N = N;
        // randomly create board
    }
    
    public Board(int[] bigTiles, int[] smallTiles, int openTile, int N, int n){
        this.bigTiles = bigTiles;
        this.smallTiles = smallTiles;
        this.openTile = openTile;
        this.N = N;
        this.n = n;
    }
    
    @Override
    public String toString(){
        String ret = "[";
        for (int i= 0; i<(smallTiles.length)-1; i++){
            ret += (smallTiles[i] + " ");
        }
        ret += (smallTiles[smallTiles.length - 1] + "]\n");
        return ret;
    }
    
    public int[] getSmallTiles(){
        return smallTiles.clone();
    }
    
    public Board[] getMoves(){
        Board[] moves;
        int[] board;
        if (bigTiles[openTile] == 1){
            moves = new Board[2];
        }

        else{ 
            moves = new Board[4];

            board = smallTiles.clone();
            board[openTile] = board[(openTile + bigTiles[openTile]) % N];
            board[(openTile + bigTiles[openTile]) % N] = 0;
            moves[2] = new Board(bigTiles, board, (openTile + bigTiles[openTile]) % N, N, n);

            board = smallTiles.clone();
            board[openTile] = board[(openTile - bigTiles[openTile] + N) % N];
            board[(openTile - bigTiles[openTile] + N) % N] = 0;
            moves[3] = new Board(bigTiles, board, (openTile - bigTiles[openTile] + N) % N, N, n);
            
        }
        board = smallTiles.clone();
        board[openTile] = board[(openTile + 1) % N];
        board[(openTile + 1) % N] = 0;
        moves[0] = new Board(bigTiles, board, (openTile + 1) % N, N, n);

        board = smallTiles.clone();
        board[openTile] = board[(openTile - 1 + N) % N];
        board[(openTile - 1 + N) % N] = 0;
        moves[1] = new Board(bigTiles, board, (openTile - 1 + N) % N, N, n);

        Collections.shuffle(Arrays.asList(moves));

        return moves;
    }
    
    public boolean isSorted(){
        int current = 1;
        int count = 0;
        int position = (openTile + 1) % N;
        for (int i = 0; i < n; i++){
            count = 0;
            while (smallTiles[position] == current){
                position = (position + 1) % N;
                count++;
            }
            if (count < n){
                return false;
            }
            current++;
        }
        return true;
    }
    
    public int getOpenTile(){
        return openTile;
    }
    
}
