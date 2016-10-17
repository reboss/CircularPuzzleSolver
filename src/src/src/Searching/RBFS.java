/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Searching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Alexander Martin
 * @param <T>
 */
public class RBFS<T> extends SearchAlgorithm<T> {
    
    private class SuccessorCompare implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            return (int) Math.signum(((Node) o1).getFCost() - ((Node) o2).getFCost());
        }
    }
    
    private class RBFSReturn {
        public final Node<T> node;
        public final double FCostLimit;  

        public RBFSReturn(Node<T> node, double FCostLimit) {
            this.node = node;
            this.FCostLimit = FCostLimit;
        }
    }

    public RBFS(Functions functions) {
        super(functions);
    }
       
    private RBFSReturn RecursiveBest(Node<T> node, double f_limit) {
        if (functions.goal(node.value))
            return new RBFSReturn(node, Double.POSITIVE_INFINITY);
        
        List<Node<T>> successors = new ArrayList<>();
        functions.explore(node.value).stream().forEach((move) -> {
            double hCost = functions.hCost(move);
            double cCost = node.getCCost() + functions.cCost(move);
            
            double fCost = Math.max(cCost + hCost, node.getFCost());
            successors.add(new Node<>(move, node, hCost, cCost, fCost));
        });
        
        if (successors.isEmpty())
            return new RBFSReturn(null, Double.POSITIVE_INFINITY);
        
        while (true) {
            Collections.sort(successors, new SuccessorCompare());
            Node<T> best = successors.get(0);
            if (best.getFCost() > f_limit)
                return new RBFSReturn(null, best.getFCost());
            Node<T> alternative = successors.get(1);
            RBFSReturn result = RecursiveBest(best, Math.min(f_limit, alternative.getFCost()));
            if (result.node != null) {
                return result;
            }
            best.setFCost(result.FCostLimit);
        }
    }
    

    @Override
    public List findSolution(T start) 
    {
        Node<T> starting_node = new Node<>(start, null, functions.hCost(start), functions.cCost(start));
        RBFSReturn soln = RecursiveBest(starting_node, Double.POSITIVE_INFINITY);
        if (soln.node != null) 
            return this.buildSolution(soln.node);
        return null;
    }
    
}
