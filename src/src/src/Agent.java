/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author reboss
 */
public class Agent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int[] board = {1, 2, 3, 3, 4, 2, 2, 1, 3, 1};
        int[] tiles = {1, 2, 2, 2, 1, 1, 1, 3, 3, 0}; // h = 5
        
        System.out.println(getHeuristic(tiles, 3, 10));
        for (int i : getMoves(board, 9, 10)){
            System.out.print(i+ " ");
        }
    }
    
    public static int getHeuristic(int[] tiles, int n, int N){
        
        int h = 0;
        int tilesInOrder = 1;
        
        for (int i = 0; i < tiles.length - 1 ; i++){
            if (tiles[i] == tiles[i+1] || tiles[i+1] == 0 || tiles[i] == 0)
                tilesInOrder++;
            else if (tilesInOrder < n){
                tilesInOrder = 1;
                h++;
            }
        }
        if (tiles[0] == tiles[tiles.length-1] || tiles[0] == 0 || tiles[tiles.length-1] == 0)
            return h;
        return h+1;
    }
    
    public static int[] getMoves(int[] board, int index, int n){
        int[] ret = new int[4];
        ret[0] = (index + 1) % n;
        ret[1] = (index - 1 + n) % n;
        ret[2] = (index + board[index]) % n;
        ret[3] = (index - board[index] + n) % n;
        return ret;
    }
}
