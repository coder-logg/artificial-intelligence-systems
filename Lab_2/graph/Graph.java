package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
	private final Set<Node> nodes;

	private boolean isNodeNamesUnique = true;

	{
		nodes = new HashSet<>();
	}

	public boolean addNode(Node node){
		return nodes.add(node);
	}

	public boolean addAllNodes(List<Node> nodeList){
		return nodes.addAll(nodeList);
	}

	public void bindNotOriented(Node firstNode, Node secondNode, int bindingWeight) throws NotFoundNodeException {
		if (validNode(firstNode) && validNode(secondNode)) {
			firstNode.addAdjacentNode(secondNode, bindingWeight);
			secondNode.addAdjacentNode(firstNode, bindingWeight);
		}
	}

	public void bindNotOriented(Node firstNode, Node secondNode, int bindingWeight, int heuristicBindWeight) throws NotFoundNodeException {
		if (validNode(firstNode) && validNode(secondNode)) {
			firstNode.addAdjacentNode(secondNode, bindingWeight, heuristicBindWeight);
			secondNode.addAdjacentNode(firstNode, bindingWeight, heuristicBindWeight);
		}
	}

	public void addNodesAndBind(Node firstNode, Node secondNode, int bindingWeight){
		addNode(firstNode);
		addNode(secondNode);
		try {
			bindNotOriented(firstNode, secondNode, bindingWeight);
		} catch (NotFoundNodeException ignored) {

		}
	}

	public void bindNotOriented(Node firstNode, Node secondNode) throws NotFoundNodeException {
		bindNotOriented(firstNode, secondNode, 0);
	}

	public void bindOriented(Node from, Node to, int bindingWeight) throws NotFoundNodeException {
		if (validNode(from) && validNode(to))
			from.addAdjacentNode(to, bindingWeight);
	}

	public void bindOriented(Node from, Node to) throws NotFoundNodeException {
		bindOriented(from, to, 0);
	}

	public static boolean containsBinding(Node first, Node second) {
		return first.isBound(second) || second.isBound(first);
	}

	public boolean containsNode(Node node){
		return nodes.contains(node);
	}

	public boolean containsNode(String nodeName){
		return nodes.stream().map(Node::getName).anyMatch(a -> a.equals(nodeName));
	}

	public void bindNotOrientedByNodeNames(String first, String second, int bindingWeight) throws NotFoundNodeException {
		bindNotOriented(getNodeByName(first), getNodeByName(second), bindingWeight);
	}

	public void bindOrientedByNodeNames(String from, String to, int bindingWeight) throws NotFoundNodeException {
		bindOriented(getNodeByName(from), getNodeByName(to), bindingWeight);
	}

	private boolean validNode(Node node) throws NotFoundNodeException {
		if (nodes.contains(node))
			return true;
		else  throw new NotFoundNodeException("Graph doesn't contain given node");
	}

	public int getNodesNumber(){
		return nodes.size();
	}
	public ArrayList<String> getNodesNames(){
		return nodes.stream().map(Node::getName).collect(Collectors.toCollection(ArrayList::new));
	}

	public Node getNodeByName(String name) throws NotFoundNodeException {
		return nodes.stream().filter(a -> a.getName().equals(name)).findFirst().
				orElseThrow(() -> new NotFoundNodeException("Graph doesn't contain node with given name :" + name));
	}

	public void setBindingWeight(Node first, Node second, int weight){
		first.setBindingWeight(second, weight);
		second.setBindingWeight(first, weight);
	}

	public ArrayList<Node> getNodes(){
		return new ArrayList<>(nodes);
	}

	public static ArrayList<Node> getAllNeighbors(Node node) {
		return new ArrayList<>(node.getNeighbors());
	}

	public boolean isNodeNamesUnique() {
		return isNodeNamesUnique;
	}

	public Graph setNodeNamesUnique(boolean nodeNamesUnique) {
		isNodeNamesUnique = nodeNamesUnique;
		return this;
	}

	public void printAdjacencyMatrix() throws NotFoundNodeException {
		ArrayList<Node> listNodes = getNodes();
		ArrayList<String> nodesNames = listNodes.stream().map(Node::getName).collect(Collectors.toCollection(ArrayList::new));
		int maxWordLength = nodesNames.stream().map(String::length).max(Integer::compare).get();
		System.out.printf("%" + (maxWordLength) + "s|", " ");
		nodesNames.forEach((a)-> System.out.printf("%" + (a.length() + 2) + "s |", a));
		System.out.println();
		for (int i = 0; i < listNodes.size(); i++) {
			System.out.printf("%" + (maxWordLength) + "s|", nodesNames.get(i));
			Node curr = listNodes.get(i);
			for (int j = 0; j < listNodes.size(); j++) {
				if (curr.isBound(listNodes.get(j)))
					System.out.printf("%" + (nodesNames.get(j).length()+ 2) + "d |", curr.getBindingWeightWith(listNodes.get(j)));
				else System.out.printf("%" + (nodesNames.get(j).length() + 2) + "s |", " ");
			}
			System.out.println();
		}
	}

	public void printAdjacentNodes() throws NotFoundNodeException {
		ArrayList<Node> listNodes = getNodes();
		ArrayList<String> nodesNames = listNodes.stream().map(Node::getName).collect(Collectors.toCollection(ArrayList::new));
		int maxWordLength = nodesNames.stream().map(String::length).max(Integer::compare).get();
		ArrayList<Node> printed = new ArrayList<>();
		int counter = 1;
		for (int i = 0; i < listNodes.size(); i++) {
			Node node = listNodes.get(i);
			ArrayList<Node> adjacent = getAllNeighbors(node);
			for (int j = 0; j < adjacent.size(); j++) {
				if ((!printed.contains(adjacent.get(j)) && adjacent.get(j).isBound(node)) || !adjacent.get(j).isBound(node))
				{
					System.out.printf("%3d|", counter++);
					System.out.printf("%" + (maxWordLength) + "s|", node.getName());
					System.out.printf("%" + (maxWordLength) + "s|", adjacent.get(j).getName());
					System.out.printf("%" + (5) + "d|", node.getBindingWeightWith(adjacent.get(j)));
					System.out.printf("%" + (5) + "d|", node.getHeuristicBindingWeight(adjacent.get(j)));
					System.out.println();
				}
			}
			printed.add(node);
		}
	}

}
