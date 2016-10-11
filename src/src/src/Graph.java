package src;

import java.util.Scanner;

//Class to create the graphs
public class Graph {
    private static int[] bigTiles;                      //Used to hold the big tiles
    private final Node root;                            //The root node
    private Node currentNode;                           //The current node
    private int boardSize;                              //The size of the board
    private int numberOfGroups;                         //The number of groups for the little tiles
    
    //Class to creates the nodes
    private class Node{
        private final Node parent;                      //Holds the parent node
        private final int h;                            //Holds the heuristic for the board
        private final int[] board;                      //Holds the little board
        
        /**
         * Used to create the nodes
         * @param parent = the parent of the node
         * @param h = heuristic
         * @param board = the little tiles
         */
        public Node(Node parent, int h, int[] board){
            this.parent = parent;
            this.h = h;
            this.board = board;
        }
    }
    
    //Used to initilize the graph
    public Graph(){
        int [] littleTiles = getInput();
        int h = Heuristic.evaluate(littleTiles,numberOfGroups,boardSize);   //Used to get the heuistic
        this.root = new Node(null, h, littleTiles);
        this.currentNode = root;
    }
    
    /**
     * Used to get the boards from standard input
     * @return littleTiles = the array for the little tiles
     */
    private int[] getInput(){
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
        boardSize = bigT.length;                        //Sets the size of the board
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
        
        for (int i=0; i<boardSize; i++){
            if (littleTiles[i] > numberOfGroups){
                numberOfGroups = littleTiles[i];
            }
        }
        return littleTiles;
    }
    
    /**
     * @param curNode = the current node to be the parent of the new nodes
     * @return tempNode = the new node
     */
    public Node newNode(Node curNode){
        Node tempNode;
        //SHOULD USE A GET MOVE FUNCTION OR SOMETHING SIMILAR--------------------------------------
        int [] littleTiles = null;
        //-----------------------------------------------------------------------------------------
        int h = Heuristic.evaluate(littleTiles,numberOfGroups,boardSize);   //Used to get the heuistic
        tempNode = new Node(curNode, h, littleTiles);
        return tempNode;
    }
}