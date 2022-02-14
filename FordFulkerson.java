import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> tmp_path = new ArrayList<Integer>();
		ArrayList<Boolean> nodes_visited = new ArrayList<Boolean>(graph.getNbNodes());
		for(int i = 0; i < graph.getNbNodes(); i++) {
			nodes_visited.add(i, false);
		}
		DFSRecursion(source,nodes_visited, tmp_path, graph);
		
		
		return tmp_path;
	}



	public static String fordfulkerson( WGraph graph){
		WGraph residual_graph = new WGraph(graph);
		ArrayList<Edge> edge_arraylist = graph.getEdges();
		int maximum_flow = 0;
		// Here, we create the flow graph to save and update the flow within the original graph
		WGraph flow_graph = new WGraph(graph);
		String final_answer="";
		// Here, we set the weight of the edges to 0 in the flow graph
		for(Edge e: edge_arraylist) {						
			flow_graph.setEdge(e.nodes[0], e.nodes[1], 0);
		}
		
		ArrayList<Integer> tmp_path = new ArrayList<Integer>();
		tmp_path = pathDFS(residual_graph.getSource(), residual_graph.getDestination(), residual_graph);
		
		
		while(tmp_path.size() != 0) {
			//We select the bottleneck to know what to add to the graph and how to modify the residual graph
			int bottleneck = findMinimum(residual_graph,tmp_path);
			//Here, we update the flow graph
			for(int i = 0; i < tmp_path.size() - 1; i++) {
				int first_node = tmp_path.get(i);
				int second_node = tmp_path.get(i+1);
				Edge e = graph.getEdge(first_node, second_node);
				if(e != null) {
					Edge f = flow_graph.getEdge(first_node, second_node);
					flow_graph.setEdge(first_node, second_node, f.weight + bottleneck);
				}
				else {
					Edge f = flow_graph.getEdge(second_node, first_node);
					if(f != null) {
						flow_graph.setEdge(second_node, first_node, f.weight - bottleneck);
					}
					else {
						Edge g = new Edge(second_node, first_node, bottleneck);
						flow_graph.addEdge(g);
					}
				}
				
			}
			//Here we update the residual graph
			UpdateResidualGraph(residual_graph, flow_graph, graph);
			
			maximum_flow += bottleneck;
			tmp_path = pathDFS(residual_graph.getSource(), residual_graph.getDestination(), residual_graph);			
		}
		
		final_answer += maximum_flow + "\n" + flow_graph.toString();	
		return final_answer;
	}
	public static void UpdateResidualGraph( WGraph res, WGraph flow, WGraph graph) {
		ArrayList<Edge> list_of_edges = graph.getEdges();
		for(Edge e: list_of_edges) {
			  Edge f = flow.getEdge(e.nodes[0], e.nodes[1]);
			  Edge c = graph.getEdge(e.nodes[0], e.nodes[1]);
			  if(f.weight > 0) {
				  Edge r = res.getEdge(e.nodes[1], e.nodes[0]);
				  if(r != null) {
					  res.setEdge(e.nodes[1], e.nodes[0], f.weight);
				  }
				  else {
					  Edge tmp = new Edge(e.nodes[1], e.nodes[0], f.weight);
					  res.addEdge(tmp);
				  }
			  }
			  if(f.weight < c.weight) {
				  res.setEdge(e.nodes[0], e.nodes[1], c.weight - f.weight);
			  }
			  if(f.weight >= c.weight && f.weight <= c.weight) {
				  res.setEdge(e.nodes[0], e.nodes[1], -1);
			  }
			  
			  if(f.weight == 0) {
				  Edge r = res.getEdge(e.nodes[1], e.nodes[0]);
				  if(r != null) {
					  res.setEdge(e.nodes[1], e.nodes[0], -1);
				  }
			  
		
		
	}
		}
	}
			  
	public static void DFSRecursion(int cur, ArrayList<Boolean> nodes_visited, ArrayList<Integer> path, WGraph graph) {
		ArrayList<Integer> possible = new ArrayList<Integer>();
		nodes_visited.set(cur, true);
		path.add(cur);
		int max = graph.getNbNodes();
		for(int i = 0; i < max; i++) { 
			if(graph.getEdge(cur,i) != null && graph.getEdge(cur, i).weight >= 0 && cur != graph.getDestination()) { 
				possible.add(i); 
			} 
		}
	
		int tmp = 0;
		while(tmp < possible.size()) {
			int newCur = possible.get(tmp);
			if(!nodes_visited.get(newCur) && !path.contains(graph.getDestination())) {
				DFSRecursion(newCur, nodes_visited, path, graph);
			}
			tmp++;
		}
		
		if(!path.contains(graph.getDestination())) {
			path.remove(path.size()-1);
		}
	}
	
	public static Integer findMinimum(WGraph graph, ArrayList<Integer> path) {
		int minPath = Integer.MAX_VALUE;
		for(int i = 0; i < path.size()-1 ; i++) {
			int node1 = path.get(i);
			int node2 = path.get(i+1);
			Edge e = graph.getEdge(node1, node2);
			if(e.weight < minPath && e.weight >= 0) {
				minPath = e.weight;
			}
		}
		return minPath;
	}
	

	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
	         System.out.println(fordfulkerson(g));
	 }
}

