package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
 

public class DiGraphImpl implements DiGraph{

	private List<GraphNode> nodeList = new ArrayList<>();
	private Map<String, List<GraphNode>> fewestHopsPaths = new HashMap<>();
	private Map<String, List<GraphNode>> shortestPaths = new HashMap<>();

	public Boolean addNode(GraphNode node){
		for (GraphNode existingNode : nodeList){
			if (existingNode.getValue().equals(node.getValue())){
				return false;
			}
		}
		nodeList.add(node);
		return true;
	}
    @Override
	public Boolean removeNode(GraphNode node){
		GraphNode nodeToRemove = getNode(node.getValue());
		if (nodeToRemove == null){
			return false;
		}

		for (GraphNode n : nodeList){
			n.removeNeighbor(nodeToRemove);
		}
		return nodeList.remove(nodeToRemove);
	}

	
	
	
}
