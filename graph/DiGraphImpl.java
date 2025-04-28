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


	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		GraphNode existingNode = getNode(node.getValue());
		if (existingNode == null) {
			return false;
		}
		existingNode.setValue(newNodeValue);
		return true;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		GraphNode existingNode = getNode(node.getValue());
		if (existingNode == null) {
			return null;
		}
		return existingNode.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return false;
		}
		return from.addNeighbor(to, weight);
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return false;
		}
		return from.removeNeighbor(to);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return false;
		}
		if (!from.getNeighbors().contains(to)) {
			return false;
		}
		from.removeNeighbor(to);
		from.addNeighbor(to, newWeight);
		return true;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return null;
		}
		return from.getDistanceToNeighbor(to);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		//TODO
		return List.of();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		//TODO
		return null;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		//TODO
		return null;
	}

	@Override
	public Boolean hasCycles() {
		//TODO
		return null;
	}

	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}

	@Override
	public GraphNode getNode(String nodeValue) {
		for (int i = 0; i < nodeList.size(); i++) {
			GraphNode node = nodeList.get(i);
			if (node.getValue().equals(nodeValue)) {
				return node;
			}
		}
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		//TODO
		return 0;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		//TODO
		return 0;
	}
}
