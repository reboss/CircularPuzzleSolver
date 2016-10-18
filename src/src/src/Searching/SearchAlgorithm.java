/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Searching;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Alexander Martin
 */
public abstract class SearchAlgorithm<T> {
    
     public class Node<T> {
        public final T value;
        public final Node<T> parent;
        private double hCost;
        private double cCost;
        private double fCost; 
        
        public Node(T value, Node<T> parent, double hCost, double cCost) {
           this(value, parent, hCost, cCost, hCost + cCost);
        }
       
        public Node(T value, Node<T> parent, double hCost, double cCost, double fCost) {
            this.value = value;
            this.parent = parent;
            this.hCost = hCost;
            this.cCost = cCost;
            this.fCost = fCost;
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
            final Node<T> other = (Node<T>) obj;
            return Objects.equals(this.value, other.value);
        }
        
        @Override
        public String toString() {
            return this.value.toString() + " (Fcost: " + this.fCost + ")";
        }

        public double getHCost() {
            return hCost;
        }

        public void setHCost(double hCost) {
            this.hCost = hCost;
        }

        public double getCCost() {
            return cCost;
        }

        public void setCCost(double cCost) {
            this.cCost = cCost;
        }

        public double getFCost() {
            return fCost;
        }

        public void setFCost(double fCost) {
            this.fCost = fCost;
        }
        
    }
    
    public interface Functions<T> {
        public double cCost(T value);
        public double hCost(T value);
        public boolean goal(T value);
        public List<T> explore(T from);       
    }
    
    public final Functions<T> functions;
    
    public abstract List<T> findSolution(T start);
    
     protected List<T> buildSolution(Node<T> goal) {
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
    
    public SearchAlgorithm(Functions functions) {
        this.functions = functions;
    }
}
