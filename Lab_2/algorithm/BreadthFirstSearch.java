package algorithm;

import graph.Graph;
import graph.Node;

import java.util.*;

public class BreadthFirstSearch extends AbstractSearchAlgorithm {
	private Queue<Node> nextToVisit = new LinkedList<>();
	private Map<Node, Integer> wavesNumbers = new HashMap<>();

	public BreadthFirstSearch(Graph graph, Node startNode, Node targetNode) {
		this.graph = graph;
		this.startNode = startNode;
		this.finishNode = targetNode;
	}

	private Stack<Node> bfs() {
		nextToVisit.offer(startNode);
		wavesNumbers.put(startNode, 0);
		while (!nextToVisit.isEmpty()){
			Node curr = nextToVisit.poll();
			if (curr.equals(finishNode)) {
				return restorePath(wavesNumbers, startNode, finishNode);
			} else {
				visited.add(curr);
				curr.getNeighbors().stream().filter(x -> !visited.contains(x)).forEach(x-> wavesNumbers.put(x, wavesNumbers.get(curr) + 1));
				nextToVisit.addAll(curr.getNeighbors());
				nextToVisit.removeAll(visited);
			}
		}
		return new Stack<>();
	}

	protected Stack<Node> restorePath(Map<Node, Integer> wavesNumbers, Node startNode, Node finishNode) {
		Stack<Node> path = new Stack<>();
		ArrayList<Node> nodes = new ArrayList<>(wavesNumbers.keySet());
		path.add(finishNode);
		while (true) {
			Node curr = nodes.stream().filter(x -> wavesNumbers.get(x) == wavesNumbers.get(path.get(path.size() - 1)) - 1).findFirst().get();
			if (curr.equals(startNode)) {
				path.add(curr);
				break;
			}
			if (curr.hasBind(path.get(path.size() - 1))){
				path.add(curr);
				nodes.remove(curr);
				nodes.removeIf(x -> wavesNumbers.get(curr) <= wavesNumbers.get(x));
			} else nodes.remove(curr);
		}
		Collections.reverse(path);
		return path;
	}

	@Override
	public Stack<Node> search() {
		return bfs();
	}
}
