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
    private final List<Node<T>> nodeHolder;
    private final Queue<Integer> fringeSet;
    private final Queue<Integer> worstSet;
        
    private final int PQ_CAPACITY = 11; // Default for Java.
    private final int PRUNE_COUNT = 1;

    
    private final Runtime runtime = Runtime.getRuntime();
   
    private class NodeComparator implements Comparator {
        
        private final List<Node<T>> nodeHolder;
        private final boolean invert;
        
        public NodeComparator(List<Node<T>> nodeHolder, boolean invert) {
            this.nodeHolder = nodeHolder;
            this.invert = invert;
        }
        
        // handle tie breakers
        @Override
        public int compare(Object o1, Object o2) {
            int multiplier = invert ? -1 : 1;
            Node<T> n1 = this.nodeHolder.get(((Integer) o1));
            Node<T> n2 = this.nodeHolder.get(((Integer) o2));
            
            if (n1 == null)
                return -1;
            if (n2 == null)
                return 1;
            
            int diff = (int) Math.signum(n1.getFCost() - n2.getFCost()) * multiplier;
            if (diff == 0) {
                return (int) Math.signum(n2.getCCost() - n1.getCCost()) * multiplier;
            } else {
                return diff;
            }
        }
    } 
    
    public AStar(Functions functions) {
        super(functions);
        this.closedSet = new HashMap<>();
        this.nodeHolder = new ArrayList<>();
        this.fringeSet = new PriorityQueue<>(this.PQ_CAPACITY, new AStar.NodeComparator(this.nodeHolder, false));
        this.worstSet = new PriorityQueue<>(this.PQ_CAPACITY, new AStar.NodeComparator(this.nodeHolder, true));
    }
    
    @Override
    public List<T> findSolution(T start) {
        this.closedSet.clear();
        this.fringeSet.clear();
        
        nodeHolder.add(new Node(start, null, 0, 0));
        fringeSet.add(nodeHolder.size() - 1);
        while (fringeSet.size() > 0) {
            int explore_pos = fringeSet.poll();
            Node<T> explore = nodeHolder.get(explore_pos);
                
            if (explore != null && !closedSet.containsKey(explore.value)) {
                if (functions.goal(explore.value)) {
                    return buildSolution(explore);
                }      
                List<T> toExplore = functions.explore(explore.value);
                for (T value : toExplore) {
                    if (runtime.freeMemory() < 0.10 * runtime.totalMemory()) {
                        for (int i=0; i<PRUNE_COUNT; i++) {
                            int remove_pos = worstSet.poll();
                            
                            nodeHolder.set(remove_pos, null);
                        }                        
                    }                                   
                    
                    Node<T> cur = new Node<>(value, explore, functions.hCost(value),
                                              functions.cCost(value) + explore.getCCost());
                    
                    nodeHolder.add(cur);
                    int new_pos = nodeHolder.size() - 1;
                    
                    fringeSet.add(new_pos);
                    worstSet.add(new_pos);
                }                
                closedSet.put(explore.value, 1);
            }
        }
        
        // No solution.
        return null;
    }
}
