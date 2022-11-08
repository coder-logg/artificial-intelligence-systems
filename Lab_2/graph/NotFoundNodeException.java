package graph;

public class NotFoundNodeException extends RuntimeException{
	public NotFoundNodeException(String errorMessage) {
		super(errorMessage);
	}
}
