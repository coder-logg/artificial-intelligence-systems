package graph;

import java.util.Stack;

public class Path {
	private Stack<Node> path;
	private int length;

	public Path(Stack<Node> path) {
		this.path = path;
		this.length = calcPathLength();
	}

	public Path(Stack<Node> path, int length) {
		this.path = path;
		this.length = length;
	}

	public int calcPathLength() throws NotFoundNodeException {
		int sum = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			sum += path.get(i).getBindingWeightWith(path.get(i + 1));
		}
		return sum;
	}

	public Stack<Node> getPath() {
		return path;
	}

	public int getLength() {
		return length;
	}
}