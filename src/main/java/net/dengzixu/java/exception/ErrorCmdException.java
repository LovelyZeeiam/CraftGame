package net.dengzixu.java.exception;

public class ErrorCmdException extends RuntimeException {

	private static final long serialVersionUID = 7162029723165334381L;

	public ErrorCmdException() {
		super("Unknown Cmd Type");
	}
}
