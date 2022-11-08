package algorithm;

import graph.Graph;
import graph.Node;
import graph.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class BestFirstSearch extends AbstractSearchAlgorithm{
	private Stack<Node> path = new Stack<>();
	private Map<Node, Integer> selectedWays = new HashMap<>();

	public BestFirstSearch(Graph graph, Node startNode, Node targetNode) {
		this.graph = graph;
		this.startNode = startNode;
		this.finishNode = targetNode;
	}

	protected boolean bestfs(Node curr, Stack<Node> res){
		visited.add(curr);
		if (curr.equals(finishNode)){
			res.addAll(path);
			res.add(finishNode);
			return true;
		}
		path.push(curr);
		Map<Node, Integer> heuristics = curr.getAllHeuristicBindingWeights();
		ArrayList<Node> sortedNeighbors = heuristics.keySet()
				.stream().sorted((x, y) -> heuristics.get(x) > heuristics.get(y) ? 1 : -1)
				.collect(Collectors.toCollection(ArrayList::new));
		for (Node node : sortedNeighbors){
			if (!visited.contains(node) && bestfs(node, res))
				return true;
		}
		path.pop();
		return false;
	}

	@Override
	public Path search() {
		visited.clear();
		Stack<Node> res = new Stack<>();
		bestfs(startNode, res);
		return new Path(res);
	}
}
