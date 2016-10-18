/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Searching;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
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
    
    private final Map<T, Integer> closedSet;
    private final Queue<Node<T>> fringeSet;
    private final Queue<Node<T>> worstSet;
        
    private final int PQ_CAPACITY = 11; // Default for Java.
    
    private final Runtime runtime = Runtime.getRuntime();
    private WeakReference<Node<T>> nodeToPrune = new WeakReference(null);
    
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
    
        
    private class WorstNodeCompartor implements Comparator {
        
        // handle tie breakers
        @Override
        public int compare(Object o1, Object o2) {
            return (int) Math.signum(((Node) o2).getFCost() - ((Node) o1).getFCost());
        }
    } 
    
    
    public AStar(Functions functions) {
        super(functions);
        this.closedSet = new HashMap<>();
        this.fringeSet = new PriorityQueue<>(this.PQ_CAPACITY, new AStar.NodeCompartor());
        this.worstSet = new PriorityQueue<>(this.PQ_CAPACITY, new AStar.WorstNodeCompartor());
    }
    
    @Override
    public List<T> findSolution(T start) {
        this.closedSet.clear();
        this.fringeSet.clear();
        
        fringeSet.add(new Node(start, null, 0, 0));
        while (fringeSet.size() > 0) {
            Node<T> explore = fringeSet.poll();
            if (!closedSet.containsKey(explore.value)) {
                if (functions.goal(explore.value)) {
                    return buildSolution(explore);
                }      
                List<T> toExplore = functions.explore(explore.value);
                for (T value : toExplore) {
                    while (runtime.freeMemory() < 0.25 * runtime.totalMemory() && nodeToPrune.get() == null) {
                        Node<T> tempReference = worstSet.poll();
                        System.out.println("Proposing node with cost " + tempReference.getFCost() + " gets removed.  New max is: " + worstSet.peek().getFCost());
                        fringeSet.remove(tempReference);
                        nodeToPrune = new WeakReference(tempReference);
                        tempReference = null; // make sure no remaining strong references.
                    }                    

                    
                    
                    Node<T> cur = new Node<>(value, explore, functions.hCost(value),
                                              functions.cCost(value) + explore.getCCost());
                    
                    fringeSet.add(cur);
                    worstSet.add(cur);
                }                
                closedSet.put(explore.value, 1);
            }
        }
        
        // No solution.
        return null;
    }
}
