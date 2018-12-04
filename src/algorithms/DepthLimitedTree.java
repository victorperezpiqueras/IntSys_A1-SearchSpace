/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import search.Node;
import search.SearchAlgorithm;
import search.State;

/**
 *
 * @author viktitors
 */
public class DepthLimitedTree extends SearchAlgorithm  {
     private int maxdepth;
    @Override //
    public void doSearch() {
        
    //We removed the set of explored nodes in order for the algorithm to be able
    //to check nodes with lower depth, that could've been already checked in a
    //deeper depth.
        State initialstate = problem.initialState();
        Node inicial = new Node(initialstate);
        Deque<Node> open=new ArrayDeque<Node>();
        ArrayList<Node> successors=new ArrayList<Node>();
        open.addFirst(inicial);
        while(!open.isEmpty()&& !solutionFound){
            Node selected=open.pop();//pop
            State estado=selected.getState();
            this.exploredNodes++;      
            //if(!explored.contains(selected.getState())  ){
                if(problem.testGoal(selected.getState())){
                    actionSequence=recoverPath(selected, inicial);
                    solutionFound=true;
                    totalCost=selected.getCost();
                }
                if(selected.getDepth()<maxdepth){
                    successors=getSuccessors(selected);
                    for(Node successor : successors){
                        open.addFirst(successor);
                    }
                    if(open.size()>openMaxSize)openMaxSize=open.size();
                    //explored.add(selected.getState());
                    //if(explored.size()>exploredMaxSize)exploredMaxSize=open.size();
                }
            //}
           
        }
        
    } 

    @Override
    public void setParams(String[] params) {
        this.maxdepth=Integer.parseInt(params[0]);
    }
    
}
