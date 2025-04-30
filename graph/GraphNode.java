package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//simplest possible adjacency list implementation of the node

public class GraphNode {
	private String value;
	private HashMap<GraphNode, Integer> paths = new HashMap<>();
	
	public GraphNode(String item) {
		value = item;
	}
	
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String newValue) {
		value = newValue;
	}
	

	public List<GraphNode> getNeighbors() {
		return new ArrayList<GraphNode>(paths.keySet());
	}
	
	
	
	public Integer getDistanceToNeighbor(GraphNode neighbor) {
		for (GraphNode thisNode : paths.keySet()) {
			if (thisNode.getValue().equals(neighbor.getValue())) {
				return paths.get(thisNode);
			}
		}
		return null;
	}
	

	public Boolean removeNeighbor(GraphNode neighbor) {
		Boolean returnValue = false;
		for (GraphNode thisNode : paths.keySet()) {
			if (thisNode.getValue().equals(neighbor.getValue())) {
				paths.remove(thisNode);
				returnValue = true;
			}
		}
		return returnValue;
	}
	
	public Boolean addNeighbor(GraphNode neighbor, Integer weight) {
		if (getDistanceToNeighbor(neighbor) != null) return false;
		paths.put(neighbor, weight);
		return true;
	}

	public void printNeighbors() {
		System.out.println("All edges from <" + value + "> are:");
		for (GraphNode neighbor : paths.keySet()) {
			System.out.println("- Edge to <" + neighbor.getValue() + ">, with weight " + paths.get(neighbor) + ".");
		}
	}

}
