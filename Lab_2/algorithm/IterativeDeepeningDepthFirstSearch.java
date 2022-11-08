package algorithm;

import graph.Graph;
import graph.Node;
import graph.Path;

import java.util.Stack;

public class IterativeDeepeningDepthFirstSearch extends LimitedDepthFirstSearch{
	public IterativeDeepeningDepthFirstSearch(Graph graph, Node startNode, Node targetNode) {
		super(graph, startNode, targetNode);
	}

	@Override
	public Path search() {
		visited.clear();
		Path res;
		res = super.search();
		for (int i = 0; (res.getPath()).empty(); i++) {
			setDepthLimit(i);
			res = super.search();
		}
		return res;
	}
}
