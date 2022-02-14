import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception{
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException{
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException{
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    BellmanFord(WGraph g, int source) throws NegativeWeightException{
    	this.source = source;
    	int[] dist = new int[g.getNbNodes()];
    	dist[0] = source;
    	int[] pred = new int[g.getNbNodes()];
    	pred[0] = -1;
    	for(int i = 1; i < dist.length; i++) {
    		dist[i] = Integer.MAX_VALUE;
    	}
    	this.distances= dist;
    	this.predecessors = pred;
    	for(int j = 0; j < g.getNbNodes()-1; j++) {
    		for(Edge e : g.getEdges()) {
    			Relax_method(e);
    		}
    	}
    	for(Edge f : g.getEdges()) {
    		if(distances[f.nodes[1]] > distances[f.nodes[0]] + f.weight) {
    			throw new NegativeWeightException(null);
    		}
    	}
    	

    }
    public void Relax_method(Edge e){
		if(distances[e.nodes[1]] > distances[e.nodes[0]] + e.weight) {
			distances[e.nodes[1]] = distances[e.nodes[0]] + e.weight;
			predecessors[e.nodes[1]] = e.nodes[0];
			
		}
	}
    public int[] shortestPath(int destination) throws PathDoesNotExistException{
    	int counter = 0;
    	int tmp = 0;
    	int[] tmp_path = new int[distances.length];
    	if(distances[destination] == Integer.MAX_VALUE) {
    		throw new PathDoesNotExistException(null);
    	}
    	else {
    		
    		while(this.predecessors[destination] != -1) {
    			tmp_path[counter] = destination;
    			destination = predecessors[destination];
    			counter++;
    			tmp++;
    			
    		}
    		int[] shortest_path = new int[tmp+1];
    		for(int k = 0; k < tmp_path.length; k++) {
    			if(tmp_path[k] != 0) {
    				shortest_path[tmp] = tmp_path[k];
    				tmp--;
    			}
    		}
    		shortest_path[0] = source;
    		
    		return shortest_path;
    		
    	}
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}

