package net.dengzixu.java.exception;

public class UnknownProtocolVersionException extends RuntimeException {

	private static final long serialVersionUID = -5227899719124735360L;

	public UnknownProtocolVersionException() {
		super("Unknown Protocol Version");
	}
}
