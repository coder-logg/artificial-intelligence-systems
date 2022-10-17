package algorithm;

import graph.Graph;
import graph.Node;
import java.util.*;

public class DepthFirstSearch extends AbstractSearchAlgorithm {
	protected Stack<Node> path = new Stack<>();

	public DepthFirstSearch(Graph graph, Node startNode, Node targetNode) {
		this.graph = graph;
		this.startNode = startNode;
		this.finishNode = targetNode;
	}

	protected void dfs(Node curr, Stack<Node> res){
		visited.add(curr);
		if (curr.equals(finishNode)){
			res.addAll(path);
			res.add(finishNode);
			return;
		}
		path.push(curr);
		ArrayList<Node> adjacentNodes = Graph.getAllNeighbors(curr);
		for (int i = 0; i < adjacentNodes.size(); i++) {
			if (!visited.contains(adjacentNodes.get(i)))
				dfs(adjacentNodes.get(i), res);
		}
		path.pop();
	}

	@Override
	public Stack<Node> search() {
		visited.clear();
		Stack<Node> res = new Stack<>();
		dfs(startNode, res);
		return res;
	}

}
