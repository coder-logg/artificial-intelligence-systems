package algorithm;

import graph.Graph;
import graph.Node;

import java.util.Stack;

public class IterativeDeepeningDepthFirstSearch extends LimitedDepthFirstSearch{
	public IterativeDeepeningDepthFirstSearch(Graph graph, Node startNode, Node targetNode) {
		super(graph, startNode, targetNode);
	}

	@Override
	public Stack<Node> search() {
		visited.clear();
		Stack<Node> res = new Stack<>();
		for (int i = 0; (res = super.search()).empty(); i++)
			setDepthLimit(i);
		return res;
	}
}
