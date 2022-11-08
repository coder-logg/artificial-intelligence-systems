package algorithm;

import graph.Graph;
import graph.Node;
import graph.NotFoundNodeException;
import graph.Path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.util.stream.Collectors;

public class AStarSearch extends AbstractSearchAlgorithm {
	private Stack<Node> res;
	private Stack<Node> path = new Stack<>();

	public AStarSearch(Graph graph, Node startNode, Node targetNode) {
		this.graph = graph;
		this.startNode = startNode;
		this.finishNode = targetNode;
	}

	private boolean ASearch(Node curr) throws NotFoundNodeException {
		visited.add(curr);
		if (curr.equals(finishNode)){
			res.addAll(path);
			res.add(finishNode);
			return true;
		}
		path.push(curr);
		Comparator<Node> comparator = (x, y) -> {
			int xSum = curr.getHeuristicBindingWeight(x) + curr.getBindingWeightWith(x);
			int ySum = curr.getHeuristicBindingWeight(y) + curr.getBindingWeightWith(y);
			return  xSum > ySum ? 1 : -1;
		};
		ArrayList<Node> adjacentNodes = Graph.getAllNeighbors(curr);
		ArrayList<Node> nodes = adjacentNodes.stream().filter(x -> !visited.contains(x))
				.sorted(comparator)
				.collect(Collectors.toCollection(ArrayList::new));
		for (Node node : nodes) {
			if (ASearch(node)) {
				return true;
			}
		}
		path.pop();
		return false;
	}

	@Override
	public Path search() {
		visited.clear();
		path.clear();
		res = new Stack<>();
		ASearch(startNode);
		return new Path(res);
	}
}
