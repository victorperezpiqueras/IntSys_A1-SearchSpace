package problems.maze;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import problems.maze.MazeState;
import search.State;
import utils.Position;

/**
 *  Represents an state, which corresponds with a position (cell) of the maze.
 */
public class MazeState extends State implements Cloneable{
	
	/** An state is includes a position given by the coordinates (x,y) */
	public Position position;
        public int life;       
        public Set<Position> cheeses = new HashSet<Position>();

        public MazeState(Position x){
            this.position=x;
            this.life=0;
        }
        
        public MazeState(int x, int y){
            this.position = new Position(x,y);
            this.life=0;
        }
	@Override
	public boolean equals(Object anotherState) {
		if(!(anotherState instanceof MazeState))return false;
                if(this.life!=((MazeState)anotherState).life)return false;
                if (this.position.x!=((MazeState)anotherState).position.x)return false;
                if (this.position.y!=((MazeState)anotherState).position.y)return false;
                if (!this.cheeses.equals(((MazeState)anotherState).cheeses))return false;
                return true;     
	}

	@Override
	public int hashCode() {
            return Objects.hash(this.position.x,this.position.y,this.life,this.cheeses);      
	}

	@Override
	public String toString() {
            System.out.println("position: "+this.position+"\n life: "
             +this.life+ " cheeses " +this.cheeses.size());
            return null;
	}
}
