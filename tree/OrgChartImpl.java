package tree;

import java.util.*;

public class OrgChartImpl implements OrgChart {

	private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();
	private GenericTreeNode<Employee> root;
	
	@Override
	public void addRoot(Employee e) {
		root = new GenericTreeNode<>(e);
		root.children = new ArrayList<>();
		nodes.add(root);
	}

	@Override
	public void clear() {
		root = null;
		nodes.clear();
	}

	@Override
	public void addDirectReport(Employee manager, Employee newPerson){
		GenericTreeNode<Employee> managerNode = findNode(manager);
		if (managerNode != null) {
			GenericTreeNode<Employee> newNode = new GenericTreeNode<>(newPerson);
			newNode.children = new ArrayList<>();
			managerNode.addChild(newNode);
			nodes.add(newNode);
		}
	}

	@Override
	public void removeEmployee(Employee firedPerson) {
		GenericTreeNode<Employee> firedNode = findNode(firedPerson);
		if (firedNode == null || firedNode == root) {
			return;
		}
		
		//find the parent of the fired person
		GenericTreeNode<Employee> parentNode = findParent(firedNode);
		if (parentNode != null){
			parentNode.children.remove(firedNode);
			
			if (firedNode.children != null) {
				for (GenericTreeNode<Employee> child : firedNode.children) {
					parentNode.addChild(child);
				}
			}
			
			//remove the fired node 
			nodes.remove(firedNode);
		}
	}

	@Override
	public void showOrgChartDepthFirst() {
		System.out.println("Depth-First Organization Chart:");
		if (root != null) {
			depthFirstTraversal(root, 0);
		}
		System.out.println();
	}

	@Override
	public void showOrgChartBreadthFirst() {
		System.out.println("Breadth-First Organization Chart:");
		if (root != null) {
			breadthFirstTraversal();
		}
		System.out.println();
	}
	
	//helper methods
	private GenericTreeNode<Employee> findNode(Employee employee) {
		for (GenericTreeNode<Employee> node : nodes) {
			if (node.data.equals(employee)) {
				return node;
			}
		}
		return null;
	}
	
	private GenericTreeNode<Employee> findParent(GenericTreeNode<Employee> childNode) {
		for (GenericTreeNode<Employee> node : nodes) {
			if (node.children != null && node.children.contains(childNode)) {
				return node;
			}
		}
		return null;
	}
	
	private void depthFirstTraversal(GenericTreeNode<Employee> node, int level) {
		StringBuilder indent = new StringBuilder();
		for (int i = 0; i < level; i++) {
			indent.append("  ");
		}
		System.out.println(indent + node.data.toString());
		
		if (node.children != null) {
			for (GenericTreeNode<Employee> child : node.children) {
				depthFirstTraversal(child, level + 1);
			}
		}
	}
	
	private void breadthFirstTraversal() {
		Queue<GenericTreeNode<Employee>> queue = new LinkedList<>();
		Queue<Integer> levels = new LinkedList<>();
		
		queue.add(root);
		levels.add(0);
		
		while (!queue.isEmpty()) {
			GenericTreeNode<Employee> current = queue.poll();
			int level = levels.poll();
			
			StringBuilder indent = new StringBuilder();
			for (int i = 0; i < level; i++) {
				indent.append("  ");
			}
			System.out.println(indent + current.data.toString());
			
			if (current.children != null) {
				for (GenericTreeNode<Employee> child : current.children) {
					queue.add(child);
					levels.add(level + 1);
				}
			}
		}
	}
}