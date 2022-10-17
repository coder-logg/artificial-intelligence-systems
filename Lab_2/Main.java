import algorithm.*;
import graph.Graph;
import graph.Node;
import graph.NotFoundNodeException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	static void printResult(Stack<Node> nodes){
		for (Node a : nodes)
			System.out.print(a.getName() + " ");
		System.out.println();
	}

	public static Graph readGraphFromFile(String fileName) throws FileNotFoundException, NotFoundNodeException {
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		Graph graph = new Graph();
		for (int i = 0; scanner.hasNext(); i++) {
			Node from = readNodeToGraph(scanner, graph);
			Node to = readNodeToGraph(scanner, graph);
			graph.bindNotOriented(from, to, scanner.nextInt(), scanner.nextInt());
		}
		return graph;
	}

	private static Node readNodeToGraph(Scanner scanner, Graph graph) throws NotFoundNodeException {
		String tmp = scanner.next();
		Node node;
		if (!graph.containsNode(tmp)) {
			node = new Node(tmp);
			graph.addNode(node);
			return node;
		} else node = graph.getNodeByName(tmp);
		return node;
	}

	public static void runSearch(SearchAlgorithm algo){
		printResult(algo.search());
	}

	public static void main(String[] args) throws NotFoundNodeException, FileNotFoundException {
		Graph graph = readGraphFromFile("graph.txt");
//		graph.printAdjacentNodes();
//		System.out.println(graph.getNodeByName("Казань").getHeuristicNearestNeighbor());
		System.out.println("Поиск в глубину");
		runSearch(new DepthFirstSearch(graph, graph.getNodeByName("Рига"), graph.getNodeByName("Одесса")));
		System.out.println("\nПоиск в ширину");
		runSearch(new BreadthFirstSearch(graph, graph.getNodeByName("Рига"), graph.getNodeByName("Одесса")));
		System.out.println("\nПоиск с ограничением глубины");
		runSearch(new LimitedDepthFirstSearch(graph, graph.getNodeByName("Рига"), graph.getNodeByName("Одесса"), 5));
		System.out.println("\nПоиск с итеративным углублением");
		runSearch(new IterativeDeepeningDepthFirstSearch(graph, graph.getNodeByName("Рига"), graph.getNodeByName("Одесса")));
		System.out.println("\nДвунаправленный поиск");
		runSearch(new BidirectionalSearch(graph, graph.getNodeByName("Рига"), graph.getNodeByName("Одесса")));
		System.out.println("\nЖадный поиск по первому наилучшему соответствию");
		runSearch(new BestFirstSearch(graph, graph.getNodeByName("Рига"), graph.getNodeByName("Одесса")));
		System.out.println("\nА*");
		runSearch(new AStarSearch(graph, graph.getNodeByName("Рига"), graph.getNodeByName("Одесса")));
	}
}
