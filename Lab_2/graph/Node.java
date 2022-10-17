package graph;

import java.util.*;

public class Node {

	private String name;
	private final Set<Node> adjacentNodes;
	private final Map<Node, Integer> bindingWeights;
	private final Map<Node, Integer> heuristicBindingWeights;

	{
		adjacentNodes = new HashSet<>();
		bindingWeights = new HashMap<>();
		heuristicBindingWeights = new HashMap<>();
	}

	public Node() {}

	public Node(String name) {
		this.name = name;
	}

	public boolean addAdjacentNode(Node adjacentNode, int weight) {
		if (!adjacentNodes.add(adjacentNode))
			return false;
		bindingWeights.put(adjacentNode, weight);
		return true;
	}

	public void addAdjacentNode(Node adjacentNode, int bindingWeight, int heuristicBindWeight) {
		addAdjacentNode(adjacentNode, bindingWeight);
		setHeuristicBindingWeight(adjacentNode, heuristicBindWeight);
	}

	public boolean addAdjacentNode(Node adjacentNode) {
		return addAdjacentNode(adjacentNode, 0);
	}

	public boolean isBound(Node node) {
		return adjacentNodes.contains(node);
	}

	public void setBindingWeight(Node node, int weight){
		bindingWeights.put(node, weight);
	}

	public void setHeuristicBindingWeight(Node node, int weight){
		heuristicBindingWeights.put(node, weight);
	}

	public int getHeuristicBindingWeight(Node node){
		return heuristicBindingWeights.get(node);
	}

	public Node getHeuristicNearestNeighbor() {
		return heuristicBindingWeights.keySet().stream()
				.min((x, y) -> heuristicBindingWeights.get(x) > heuristicBindingWeights.get(y) ? 1 : -1).get();
	}

	public Map<Node, Integer> getAllHeuristicBindingWeights() {
		return new HashMap<>(heuristicBindingWeights);
	}

	public Node getNearestNeighbor() {
		return bindingWeights.keySet().stream()
				.min((x, y) -> bindingWeights.get(x) > bindingWeights.get(y) ? 1 : -1).get();
	}

	public String getName() {
		return name;
	}

	public Node setName(String name) {
		this.name = name;
		return this;
	}

	public boolean removeAdjacentNode(Node adjacentNode) {
		if (!adjacentNodes.remove(adjacentNode))
			return false;
		bindingWeights.remove(adjacentNode);
		return true;
	}

	public int getAdjacentNodesNumber(){
		return adjacentNodes.size();
	}

	public Set<Node> getNeighbors() {
		return adjacentNodes;
	}

	public boolean hasBind(Node node){
		return adjacentNodes.contains(node);
	}

	public int getBindingWeightWith(Node node) throws NotFoundNodeException {
		if (adjacentNodes.contains(node))
			return bindingWeights.get(node);
		throw new NotFoundNodeException("Given node isn't adjacent");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return Objects.equals(name, node.name) && Objects.equals(adjacentNodes, node.adjacentNodes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return
//				"Node{" +
				"name='" + name + '\'';
//						+ ", " todo
//				"bindings=" + bindWeight.keySet().stream()
//									.map(x-> {
//										String xName = x.getName() != null ? x.getName() : "@" + x.hashCode();
//										return "{node=" + xName + ", weight=" + bindWeight.get(x) + "}";
//									})
//									.collect(Collectors.joining(", ", "[", "]")) +
//				'}';
	}
}
