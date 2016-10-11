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
    
    private int[] bigTiles;
    private int[] smallTiles;
    private int openTile;
    int N;
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
            moves[2] = new Board(bigTiles, board, (openTile - bigTiles[openTile] + N) % N, N, n);
            
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
    
    
}