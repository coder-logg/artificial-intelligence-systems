package algorithm;

import graph.Graph;
import graph.Node;
import graph.Path;

import java.util.Stack;

public class LimitedDepthFirstSearch extends DepthFirstSearch {
	protected int depthLimit = -1;

	public LimitedDepthFirstSearch(Graph graph, Node startNode, Node targetNode) {
		super(graph, startNode, targetNode);
	}

	public LimitedDepthFirstSearch(Graph graph, Node startNode, Node targetNode, int depthLimit) {
		super(graph, startNode, targetNode);
		setDepthLimit(depthLimit);
	}

	public int getDepthLimit() {
		return depthLimit;
	}

	public DepthFirstSearch setDepthLimit(int depthLimit) {
		this.depthLimit = depthLimit;
		return this;
	}

	@Override
	protected void dfs(Node curr, Stack<Node> res){
		if (path.size() + 1 > depthLimit)
			return;
		super.dfs(curr, res);
	}

	@Override
	public Path search() {
		visited.clear();
		Stack<Node> res = new Stack<>();
		dfs(startNode, res);
		return new Path(res);
	}
}
