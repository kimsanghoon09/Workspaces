package sh.java.exception;

public class NoGoodScoreException extends RuntimeException {
	public NoGoodScoreException() {}
	public NoGoodScoreException(String message) {
		super(message);
	}
}
