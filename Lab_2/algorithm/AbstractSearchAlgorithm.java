package algorithm;

import graph.Graph;
import graph.Node;

import java.util.ArrayList;

public abstract class AbstractSearchAlgorithm implements SearchAlgorithm{
	protected Graph graph;
	protected Node startNode;
	protected Node finishNode;
	protected ArrayList<Node> visited;

	{
		visited = new ArrayList<>();
	}

	public AbstractSearchAlgorithm setGraph(Graph graph) {
		this.graph = graph;
		return this;
	}

	public AbstractSearchAlgorithm setStartNode(Node startNode) {
		this.startNode = startNode;
		return this;
	}

	public AbstractSearchAlgorithm setFinishNode(Node finishNode) {
		this.finishNode = finishNode;
		return this;
	}

	public Graph getGraph() {
		return graph;
	}

	public Node getStartNode() {
		return startNode;
	}

	public Node getFinishNode() {
		return finishNode;
	}
}
