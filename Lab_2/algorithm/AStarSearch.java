package algorithm;

import graph.Graph;
import graph.Node;
import graph.NotFoundNodeException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class AStarSearch extends AbstractSearchAlgorithm {
	private int prevWeightSum = 0;
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
		ArrayList<Node> adjacentNodes = Graph.getAllNeighbors(curr);
		ArrayList<Node> nodes = adjacentNodes.stream().filter(x -> !visited.contains(x)).sorted((x, y) -> {
			try {
				return curr.getHeuristicBindingWeight(x) + curr.getBindingWeightWith(x) > curr.getHeuristicBindingWeight(y) + curr.getBindingWeightWith(y) ? 1 : -1;
			} catch (NotFoundNodeException ignored) {}
			return 0;
		}).collect(Collectors.toCollection(ArrayList::new));
		for (Node node : nodes) {
			if (ASearch(node))
			{
				prevWeightSum += node.getBindingWeightWith(curr);
				return true;
			}
		}
		path.pop();
		return false;
	}

	public int getPrevWeightSum() {
		return prevWeightSum;
	}

	@Override
	public Stack<Node> search() {
		visited.clear();
		path.clear();
		res = new Stack<>();
		try {
			ASearch(startNode);
		} catch (NotFoundNodeException ignored) {}
		return res;
	}
}
