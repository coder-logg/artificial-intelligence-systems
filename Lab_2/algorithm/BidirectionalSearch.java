package algorithm;

import graph.Graph;
import graph.Node;
import graph.Path;

import java.util.*;
import java.util.stream.Collectors;

public class BidirectionalSearch extends BreadthFirstSearch {
	private ArrayList<Node> visitedFromStart = new ArrayList<>();
	private Queue<Node> nextToVisitFromStart = new LinkedList<>();
	private Map<Node, Integer> wavesNumbersFromStart = new HashMap<>();

	private ArrayList<Node> visitedFromEnd = new ArrayList<>();
	private Queue<Node> nextToVisitFromEnd = new LinkedList<>();
	private Map<Node, Integer> wavesNumbersFromEnd = new HashMap<>();

	public BidirectionalSearch(Graph graph, Node startNode, Node targetNode) {
		super(graph, startNode, targetNode);
	}

	private Stack<Node> joinPaths(Node intermediateNode){
		Stack<Node> res = restorePath(wavesNumbersFromStart, startNode, intermediateNode);
		Stack<Node> tmp = restorePath(wavesNumbersFromEnd, finishNode, intermediateNode);
		Collections.reverse(tmp);
		res.addAll(tmp);
		res = res.stream().distinct().collect(Collectors.toCollection(Stack::new));
		return res;
	}

	private Stack<Node> iterate() {
		init(nextToVisitFromEnd, wavesNumbersFromEnd, finishNode);
		init(nextToVisitFromStart, wavesNumbersFromStart, startNode);
		while (!nextToVisitFromEnd.isEmpty() && !nextToVisitFromStart.isEmpty()){
			Node currFromStart = nextToVisitFromStart.poll();
			Node currFromEnd = nextToVisitFromEnd.poll();
			if (visitedFromStart.contains(currFromEnd) || currFromEnd.equals(currFromStart))
				return joinPaths(currFromEnd);
			else if (visitedFromEnd.contains(currFromStart))
				return joinPaths(currFromStart);
			else {
				visitedFromStart.add(currFromStart);
				visitedFromEnd.add(currFromEnd);

				currFromStart.getNeighbors().stream().filter(x -> !visitedFromStart.contains(x))
						.forEach(x-> wavesNumbersFromStart.put(x, wavesNumbersFromStart.get(currFromStart) + 1));
				currFromEnd.getNeighbors().stream().filter(x -> !visitedFromEnd.contains(x))
						.forEach(x-> wavesNumbersFromEnd.put(x, wavesNumbersFromEnd.get(currFromEnd) + 1));

				nextToVisitFromStart.addAll(currFromStart.getNeighbors());
				nextToVisitFromStart.removeAll(visitedFromStart);

				nextToVisitFromEnd.addAll(currFromEnd.getNeighbors());
				nextToVisitFromEnd.removeAll(visitedFromEnd);
			}
		}
		return new Stack<>();
	}

	private void init(Queue<Node> nextToVisit, Map<Node, Integer> wavesNumbers, Node startNode) {
		nextToVisit.offer(startNode);
		wavesNumbers.put(startNode, 0);
	}

	@Override
	public Path search() {
		visitedFromStart.clear();
		nextToVisitFromStart.clear();
		wavesNumbersFromStart.clear();

		visitedFromEnd.clear();
		nextToVisitFromEnd.clear();
		wavesNumbersFromEnd.clear();
		return new Path(iterate());
	}
}
