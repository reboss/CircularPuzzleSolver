/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.Scanner;
import java.util.List;
import src.Searching.AStar;
import src.Searching.SearchAlgorithm;

/**
 *
 * @author reboss
 */
public class Main {
    private static int[] bigTiles;                                              //Used to hold the big tiles
    private static int n;                                                       //Used to hold the number of groups for the little tiles
    private static int N;                                                       //Used to hold the board size

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] smallTiles = getInput();
        int openTile = 0;
        List<Board> solution;

        while (smallTiles[openTile] != 0){                                      //Used to find the position of the open tile
            openTile++;
        }

        Board board = new Board(bigTiles, smallTiles, openTile, N, n);          //Creates a new board
        Heuristic heuristic = new Heuristic(N, n);
        SearchAlgorithm<Board> search = new AStar<Board>(heuristic);
        
        //The solution only checks if the numbers are grouped and not in order
        solution = search.findSolution(board);
        printSolution(solution);
    }
    
    /**
     * Used to get the boards from standard input
     * @return littleTiles = the array for the little tiles
     */
    private static int[] getInput(){
        String[] input = new String[2];
        int[] littleTiles;
        int pos;
        
        Scanner stdin = new Scanner(System.in);
        for (pos=0; pos<2; pos++){                      //Used to get the boards from standard inout
            input[pos] = stdin.nextLine();
        }
        
        //Used to get the big tiles
        pos=0;

        String[] bigT = input[0].split(" ");
        N = bigT.length;                        //Sets the size of the board
        bigTiles = new int[bigT.length];                //Creates a new array with the correct length
        
        for (String bigT1 : bigT) {
            bigTiles[pos] = Integer.parseInt(bigT1);
            pos++;
        }
        
        //Used to get the beginning line of the little tiles
        pos=0;

        String[] littleT = input[1].split(" ");
        littleTiles = new int[littleT.length];          //Creates a new array with the correct length

        for (String littleT1 : littleT) {
            littleTiles[pos] = Integer.parseInt(littleT1);
            pos++;
        }
        
        for (int i=0; i<N; i++){
            if (littleTiles[i] > n){
                n = littleTiles[i];
            }
        }
        return littleTiles;
    }
    
    private static void printSolution(List solution){
        if (solution == null){
            System.out.println("There is no solution");
        }
        else{
            for (int i = 0; i<solution.size(); i++){
                System.out.print(solution.get(i));
            }
        }
    }
}
