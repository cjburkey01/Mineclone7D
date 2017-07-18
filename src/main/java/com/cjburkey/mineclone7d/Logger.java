package com.cjburkey.mineclone7d;

import java.io.PrintStream;

public class Logger {
	
	private static final String NULL = "null";
	
	public static void log(PrintStream stream, Object msg) {
		String out = (msg == null) ? NULL : msg.toString();
		stream.println(out.trim());
	}
	
	public static void info(Object msg) {
		log(System.out, msg);
	}
	
	public static void error(Object msg) {
		log(System.err, msg);
	}
	
}