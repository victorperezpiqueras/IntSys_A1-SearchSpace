/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import search.Node;
import search.SearchAlgorithm;
import search.State;

/**
 *
 * @author viktitors
 */
public class A_star extends SearchAlgorithm  {
     
    @Override
    public void doSearch() {
        
    
        State initialstate = problem.initialState();
        Node inicial = new Node(initialstate);                                                      
        PriorityQueue<Node> open=new PriorityQueue<Node>(Node.BY_EVALUATION);

        HashSet<State> explored=new HashSet<State>();
        
        ArrayList<Node> successors=new ArrayList<Node>();
        open.add(inicial);
        
        while(!open.isEmpty()&& !solutionFound){
            Node selected=open.poll();//pop
            this.exploredNodes++;
            if(!explored.contains(selected.getState())  ){
                if(problem.testGoal(selected.getState())){
                    actionSequence=recoverPath(selected, inicial);
                    solutionFound=true;
                    totalCost=selected.getCost();
                }
                successors=getSuccessors(selected);
                for(Node successor : successors){
                    open.add(successor);
                }
                if(open.size()>openMaxSize)openMaxSize=open.size();
                explored.add(selected.getState());
                if(explored.size()>exploredMaxSize)exploredMaxSize=open.size();

            }         
        }
       
    } 

    @Override
    public void setParams(String[] params) {
    }

    
}
