/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Tests;

import java.util.ArrayList;
import java.util.List;
import src.Searching.AStar;

/**
 *
 * @author Alexander Martin
 */
public class AStarTester {
    
    public static class Vector2 {
        public int x;
        public int y;
        
        public Vector2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + this.x;
            hash = 53 * hash + this.y;
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
            final Vector2 other = (Vector2) obj;
            if (this.x != other.x) {
                return false;
            }
            if (this.y != other.y) {
                return false;
            }
            return true;
        }
        
        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
        
        
    }
    
    public static class Functions implements AStar.Functions<AStarTester.Vector2> {
        
        private final Vector2 goal = new Vector2(3,3);
        private final int BOUND_UP = 5;
        private final int BOUND_RIGHT = 5;
        private final int BOUND_DOWN = 0;
        private final int BOUND_LEFT = 0;
        
        @Override
        public double cCost(AStarTester.Vector2 value) {
            return 1;
        }

        @Override
        public double hCost(AStarTester.Vector2 value) {
            return Math.sqrt(Math.pow(value.x - goal.x, 2) + Math.pow(value.y - goal.y, 2));
        }

        @Override
        public boolean goal(AStarTester.Vector2 value) {
            return goal.equals(value);
        }

        @Override
        public List<AStarTester.Vector2> explore(AStarTester.Vector2 from) {
            List<AStarTester.Vector2> list = new ArrayList<>();
            if (from.x < BOUND_RIGHT)
                list.add(new Vector2(from.x + 1, from.y));
            if (from.x > BOUND_LEFT)
                list.add(new Vector2(from.x - 1, from.y));
            if (from.y < BOUND_UP)
                list.add(new Vector2(from.x, from.y + 1));
            if (from.y > BOUND_DOWN)
                list.add(new Vector2(from.x, from.y - 1));
            return list;
        }
        
    }
    
    public static void main(String[] args)
    {
        AStar<AStarTester.Vector2> solver = new AStar<>(new Functions());
        List<AStarTester.Vector2> soln = solver.findSolution(new Vector2(1,1));
        for (AStarTester.Vector2 x : soln) {
            System.out.println(x);
        }
    }
    
    
}
