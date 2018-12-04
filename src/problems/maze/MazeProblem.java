package problems.maze;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static problems.maze.MazeAction.DOWN;
import static problems.maze.MazeAction.EAT;
import static problems.maze.MazeAction.LEFT;
import static problems.maze.MazeAction.RIGHT;
import static problems.maze.MazeAction.UP;

import search.State;
import search.Action;
import search.SearchProblem;
import utils.Position;

import visualization.ProblemView;
import visualization.ProblemVisualizable;

/** 
 * Extends SearchProblem and implements the functions which define
 * the maze problem. Always uses two cheeses.  
 */
public class MazeProblem implements SearchProblem, ProblemVisualizable{
	
	// Uses always three cheeses (to make it easier implementation).
	private static final int NUM_CHEESES = 3;
	// Penalty factor for fight with the cat. 
	private static final double PENALTY = 2;
	
	/* Maze */
	Maze maze;
	
    /** Builds a maze */
	public MazeProblem(){
		this.maze = new Maze(10);
	}
	
	public MazeProblem(int size, int seed, int cats){
		this.maze = new Maze(size, seed, cats, NUM_CHEESES);
	}

	@Override
	public void setParams(String[] args) {
		// The maze already exists.
		// It is generated with the new parameters. 
		int size=this.maze.size;
		int seed= this.maze.seed;
		int cats = this.maze.numCats;
		
		if (args.length==3)
			try{
			   size = Integer.parseInt(args[0]);
			   cats = Integer.parseInt(args[1]);
			   seed = Integer.parseInt(args[2]);
			} catch(Exception e){
                            System.out.println("Parameters for MazeProblem are incorrect.");
                            return;
			}	
		
		// Generates the new maze. 
		this.maze = new Maze(size, seed, cats, NUM_CHEESES);
	}
	
	// PROBLEM REPRESENTATION (CORRESPONDS TO THE FUNCTIONS IN THE INTERFACE SearchProblem)
	

	@Override
	public State initialState() {
            MazeState initial = new MazeState(new Position(maze.inputX,0));        
            return initial;
	}

	@Override
	public State applyAction(State state, Action action) {

                if(action==RIGHT){
                    MazeState newState = new MazeState(((MazeState)state).position.x+1,((MazeState)state).position.y);
                    newState.life=((MazeState)state).life;
                    newState.cheeses.addAll(((MazeState)state).cheeses);
                    if(maze.containsCat(newState.position.x,newState.position.y))newState.life++;
                        return newState;
                }
		
                else if(action==LEFT){
                    MazeState newState = new MazeState(((MazeState)state).position.x-1,((MazeState)state).position.y);
                    newState.life=((MazeState)state).life;
                    newState.cheeses.addAll(((MazeState)state).cheeses);
                     if(maze.containsCat(newState.position.x,newState.position.y))newState.life++;
                        return newState;
                }
		
                else if(action==UP){
                    MazeState newState = new MazeState(((MazeState)state).position.x,((MazeState)state).position.y-1);
                    newState.life=((MazeState)state).life;
                    newState.cheeses.addAll(((MazeState)state).cheeses);
                     if(maze.containsCat(newState.position.x,newState.position.y))newState.life++;
                         return newState;
                }
		
                else if(action==DOWN){
                    MazeState newState = new MazeState(((MazeState)state).position.x,((MazeState)state).position.y+1);
                    newState.life=((MazeState)state).life;
                    newState.cheeses.addAll(((MazeState)state).cheeses);
                     if(maze.containsCat(newState.position.x,newState.position.y))newState.life++;
                        return newState;
                }
                else if(action==EAT){
                    MazeState newState = new MazeState(((MazeState)state).position.x,((MazeState)state).position.y);
                    newState.life=((MazeState)state).life;
                    newState.cheeses.addAll(((MazeState)state).cheeses);
                    for(Position ch : maze.cheesePositions){
                        if((newState.position.x==ch.x && newState.position.y==ch.y) && !(newState.cheeses.contains(ch)))
                            newState.cheeses.add(ch);    
                    }
                     if(maze.containsCat(newState.position.x,newState.position.y))
                         newState.life++;
                         return newState;
                }
               return null;          
        }

	@Override
	public ArrayList<Action> getPossibleActions(State state) {
		ArrayList<Action> posActs=new ArrayList<Action>();
                MazeState mazeState = (MazeState) state;
                if(mazeState.life>1)return posActs;//if dead--> return no actions                
                Set<Position> alcanzables = maze.cellGraph.get(mazeState.position); 
                
//BAD SELECTION
                if(maze.containsCheese(mazeState.position)&&!mazeState.cheeses.contains(mazeState.position))posActs.add(EAT);
                if(alcanzables.contains(new Position(mazeState.position.x, mazeState.position.y+1))){
		    posActs.add(DOWN);
                }
                 if(alcanzables.contains(new Position(mazeState.position.x-1, mazeState.position.y))){
		    posActs.add(LEFT);
                }
                if(alcanzables.contains(new Position(mazeState.position.x+1, mazeState.position.y))){
                  posActs.add(RIGHT);
                 }
                if(alcanzables.contains(new Position(mazeState.position.x, mazeState.position.y-1))){
                    posActs.add(UP);
                  }

 // GOOD SELECTION
//                if(alcanzables.contains(new Position(mazeState.position.x, mazeState.position.y-1))){
//		    posActs.add(UP);
//                }
//                 if(alcanzables.contains(new Position(mazeState.position.x-1, mazeState.position.y))){
//		    posActs.add(LEFT);
//                }
//                if(alcanzables.contains(new Position(mazeState.position.x+1, mazeState.position.y))){
//		    posActs.add(RIGHT);
//                }
//                if(alcanzables.contains(new Position(mazeState.position.x, mazeState.position.y+1))){
//		    posActs.add(DOWN);
//                }
//                if(maze.containsCheese(mazeState.position)&&!mazeState.cheeses.contains(mazeState.position))posActs.add(EAT);
        return posActs;
	}

	@Override
	public double cost(State state, Action action) {
		if(((MazeState)state).life==0)return 1;
                else return PENALTY;
	}

	@Override
	public boolean testGoal(State chosen) {
                if(((MazeState)chosen).position.x!=maze.outputX)return false;
                if(((MazeState)chosen).position.y!=maze.size-1)return false;
                if(((MazeState)chosen).cheeses.size()!=NUM_CHEESES)return false;
                return true;                                     
	}

	@Override
	public double heuristic(State state) {

//HEURISTIC 1: EUCLIDEAN DISTANCE
            double total=abs(maze.outputX-((MazeState)state).position.x)+abs(maze.size-1-((MazeState)state).position.y);
            //System.out.println("heuristic:" +total);
            return total;
            
//HEURISTIC 2: DISTANCE FROM POSITION TO FURTHER CHEESE FROM EXIT       
//            long maxsegment=Integer.MIN_VALUE;
//            double total=0;
//            Set<Position> cheeseRemaining=new HashSet<Position>();
//            Position pos=new Position(((MazeState)state).position.x,((MazeState)state).position.y); 
//            
//            cheeseRemaining.addAll(maze.cheesePositions);//add all the problem cheeses
//            cheeseRemaining.removeAll( ((MazeState)state).cheeses );//remove cheeses that are already eaten 
//            
//            if(cheeseRemaining.isEmpty())return abs(maze.outputX-pos.x)+abs(maze.size-1-pos.y);
//            else{
//                Position cheese=null;
//                for(Position ch : cheeseRemaining){
//                    if(maxsegment< ( abs(maze.outputX-ch.x)+abs(maze.size-1-ch.y) ) ){//distance from further cheese from exit
//                        maxsegment=(abs(maze.outputX-ch.x)+abs(maze.size-1-ch.y));
//                        cheese=new Position(ch.x,ch.y);
//                    }
//                }
//                total+=maxsegment;
//                total+=abs(pos.x-cheese.x)+abs(pos.y-cheese.y);//distancia(ESTADO,cheese)  
//                return total;
//            }
////////////////////

	}
	
    
	// VISUALIZATION
	/** Returns a panel with the view of the problem. */
	@Override
	public ProblemView getView(int sizePx) {
		return new MazeView(this, sizePx);
	}	
}
