/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Searching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 
 * @author Alexander Martin
 * @param <T>
 */
public class AStar<T> extends SearchAlgorithm<T> {
    
    private final Map<Node<T>, Integer> closedSet;
    private final Queue<Node<T>> fringeSet;
        
    private final int PQ_CAPACITY = 11; // Default for Java.
    
    private class NodeCompartor implements Comparator {
        
        // handle tie breakers
        @Override
        public int compare(Object o1, Object o2) {
            int diff = (int) Math.signum(((Node) o1).getFCost() - ((Node) o2).getFCost());
            if (diff == 0) {
                return (int) Math.signum(((Node) o1).getCCost() - ((Node) o2).getCCost());
            } else {
                return diff;
            }
        }
    } 
    
    
    public AStar(Functions functions) {
        super(functions);
        this.closedSet = new HashMap<>();
        this.fringeSet = new PriorityQueue<>(this.PQ_CAPACITY, new AStar.NodeCompartor());
    }
    
    @Override
    public List<T> findSolution(T start) {
        this.closedSet.clear();
        this.fringeSet.clear();
        
        fringeSet.add(new Node(start, null, 0, 0));
        while (fringeSet.size() > 0) {
            Node<T> explore = fringeSet.poll();
            if (!closedSet.containsKey(explore)) {
                if (functions.goal(explore.value)) {
                    return buildSolution(explore);
                }      
                List<T> toExplore = functions.explore(explore.value);
                for (T value : toExplore) {
                    Node<T> cur = new Node<>(value, explore, functions.hCost(value),
                                              functions.cCost(value) + explore.getCCost());
                    
                    fringeSet.add(cur);
                }                
                closedSet.put(explore, 1);
            }
        }
        
        // No solution.
        return null;
    }
}
