package graph;

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
		GraphNode existingNode = getNode(node.getValue());
		if (existingNode == null) {
			return new ArrayList<>();
		}
		return existingNode.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		GraphNode from = getNode(fromNode.getValue());
		GraphNode to = getNode(toNode.getValue());
		if (from == null || to == null) {
			return false;
		}
		for (GraphNode neighbor : from.getNeighbors()) {
			if (neighbor.getValue().equals(to.getValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		if (fewestHops(fromNode, toNode) != -1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean hasCycles() {
		Set<GraphNode> visited = new HashSet<>();
		Set<GraphNode> recursionStack = new HashSet<>();

		for (GraphNode node : nodeList) {
			if (detectCycle(node, visited, recursionStack)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectCycle(GraphNode node, Set<GraphNode> visited, Set<GraphNode> recursionStack) {
		if (recursionStack.contains(node)) {
			return true;
		}
		if (visited.contains(node)) {
			return false;
		}

		visited.add(node);
		recursionStack.add(node);

		for (GraphNode neighbor : node.getNeighbors()) {
			if (detectCycle(neighbor, visited, recursionStack)) {
				return true;
			}
		}

		recursionStack.remove(node);
		return false;
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
	public int fewestHops(GraphNode fromNode, GraphNode toNode) { //took this from hw assignment, tweaked it  
		GraphNode targetfromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());

		Queue<GraphNode> queue = new LinkedList<>();
		Set<GraphNode> visitedNodes = new HashSet<>();

		queue.add(targetfromNode);
		int hops = 0;

		while(queue.peek() != null){
			GraphNode thisNode = queue.poll();
			for (GraphNode thisNeighbor : thisNode.getNeighbors()) {
				if(visitedNodes.add(thisNeighbor)){
					queue.add(thisNeighbor);
					hops++;
				}
				if (visitedNodes.contains(targetToNode)){
					return hops;
				}
			}
		}
		return -1;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		GraphNode source = getNode(fromNode.getValue());
		GraphNode target = getNode(toNode.getValue());
		
		if (source == null || target == null) {
			return -1;
		}
		
		if (source.equals(target)) {
			return 0;
		}
		
		Map<GraphNode, Integer> distances = new HashMap<>();
		Set<GraphNode> visited = new HashSet<>();
		
		//get distances
		for (GraphNode node : nodeList) {
			distances.put(node, Integer.MAX_VALUE); //Integer library instad of using really high value 
		}
		distances.put(source, 0);
		
		while (visited.size() < nodeList.size()) {
			GraphNode current = null;
			int minDistance = Integer.MAX_VALUE;
			
			for (GraphNode node : nodeList) {
				if (!visited.contains(node) && distances.get(node) < minDistance) {
					minDistance = distances.get(node);
					current = node;
				}
			}
			
			if (current == null || minDistance == Integer.MAX_VALUE) {
				break;
			}
			
			if (current.equals(target)) {
				return distances.get(target);
			}
			
			visited.add(current);
			
			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					int newDistance = distances.get(current) + current.getDistanceToNeighbor(neighbor);
					if (newDistance < distances.get(neighbor)) {
						distances.put(neighbor, newDistance);
					}
				}
			}
		}
		if (distances.get(target) == Integer.MAX_VALUE){
			return -1;
		} else {
			return distances.get(target);
		}

	}

	@Override
	public List<GraphNode> getShortestPath(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());

		if (start == null || end == null) {
			return new ArrayList<>();
		}

		Map<GraphNode, Integer> distances = new HashMap<>();
		Map<GraphNode, GraphNode> parentMap = new HashMap<>();
		Set<GraphNode> visited = new HashSet<>();

		for (GraphNode node : nodeList) {
			distances.put(node, Integer.MAX_VALUE);
		}
		distances.put(start, 0);
		parentMap.put(start, null);

		while (visited.size() < nodeList.size()) {
			GraphNode current = null;
			int minDistance = Integer.MAX_VALUE;

			for (GraphNode node : nodeList) {
				if (!visited.contains(node) && distances.get(node) < minDistance) {
					minDistance = distances.get(node);
					current = node;
				}
			}

			if (current == null) {
				break;
			}

			visited.add(current);

			for (GraphNode neighbor : current.getNeighbors()) {
				int edgeWeight = current.getDistanceToNeighbor(neighbor);
				int newDistance = distances.get(current) + edgeWeight;

				if (newDistance < distances.get(neighbor)) {
					distances.put(neighbor, newDistance);
					parentMap.put(neighbor, current);
				}
			}
		}

		if (distances.get(end) == Integer.MAX_VALUE) {
			return new ArrayList<>();
		}

		List<GraphNode> path = new ArrayList<>();
		for (GraphNode node = end; node != null; node = parentMap.get(node)) {
			path.add(0, node);
		}
		return path;
	}


	@Override
	public List<GraphNode> getFewestHopsPath(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());

		if (start == null || end == null) {
			return new ArrayList<>();
		}

		Map<GraphNode, GraphNode> parentMap = new HashMap<>();
		Set<GraphNode> visited = new HashSet<>();
		Queue<GraphNode> queue = new LinkedList<>();

		queue.add(start);
		visited.add(start);
		parentMap.put(start, null);

		while (!queue.isEmpty()) {
			GraphNode current = queue.poll();

			if (current.equals(end)) {
				break;
			}

			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, current);
					queue.add(neighbor);
				}
			}
		}

		if (!parentMap.containsKey(end)) {
			return new ArrayList<>();
		}

		List<GraphNode> path = new ArrayList<>();
		for (GraphNode node = end; node != null; node = parentMap.get(node)) {
			path.add(0, node);
		}
		return path;
	}

	@Override
	public void addEdgeStr(String a, String b, int weight) {
		GraphNode from = getNode(a);
		GraphNode to = getNode(b);
		if (from != null && to != null) {
			addEdge(from, to, weight);
		}
	}

}

