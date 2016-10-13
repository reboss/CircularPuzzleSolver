/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 
 * @author Alexander Martin
 * @param <T>
 */
public class AStar<T> {
    
    private class Node<T> {
        public final T value;
        public final Node<T> parent;
        public final double hCost;
        public final double cCost;
       
        public Node(T value, Node<T> parent, double hCost, double cCost) {
            this.value = value;
            this.parent = parent;
            this.hCost = hCost;
            this.cCost = cCost;
        }
        
        public double fCost() {
            return this.hCost + this.cCost;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.value);
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
            final Node<?> other = (Node<?>) obj;
            return Objects.equals(this.value, other.value);
        }
        
        @Override
        public String toString() {
            return this.value.toString() + " (Fcost: " + this.fCost() + ")";
        }
    }
    
    private class NodeCompartor implements Comparator {
        
        // handle tie breakers
        @Override
        public int compare(Object o1, Object o2) {
            int diff = (int) Math.signum(((Node) o1).fCost() - ((Node) o2).fCost());
            if (diff == 0) {
                return (int) Math.signum(((Node) o1).cCost - ((Node) o2).cCost);
            } else {
                return diff;
            }
        }
    } 
    
    public interface Functions<T> {
        public double cCost(T value);
        public double hCost(T value);
        public boolean goal(T value);
        public List<T> explore(T from);       
    }
    

    private final List<Node<T>> closedSet;
    private final Queue<Node<T>> fringeSet;
    private final Functions<T> functions;
    
    private final int PQ_CAPACITY = 11; // Default for Java.
    
    public AStar(Functions functions) {
        this.closedSet = new ArrayList<>();
        this.fringeSet = new PriorityQueue<>(this.PQ_CAPACITY, new AStar.NodeCompartor());
        this.functions = functions;
    }
    
    private List<T> buildSolution(Node<T> goal) {
        // Using LinkedList because we always are inserting
        // at the beggining of the list.
        List<T> solution = new LinkedList<>();
        Node<T> cur = goal;
        while (cur != null) {
            solution.add(0, cur.value);
            cur = cur.parent;
        }
        return solution;
    }
    
    public List<T> findSolution(T start) {
        this.closedSet.clear();
        this.fringeSet.clear();
        
        fringeSet.add(new Node(start, null, 0, 0));
        while (fringeSet.size() > 0) {
            Node<T> explore = fringeSet.poll();
            if (!closedSet.contains(explore)) {
                if (functions.goal(explore.value)) {
                    return buildSolution(explore);
                }      
                List<T> toExplore = functions.explore(explore.value);
                for (T value : toExplore) {
                    Node<T> cur = new Node<>(value, explore, functions.hCost(value),
                                              functions.cCost(value) + explore.cCost);
                    
                    fringeSet.add(cur);
                }
                closedSet.add(explore);
            }
        }
        
        // No solution.
        return null;
    }
}
