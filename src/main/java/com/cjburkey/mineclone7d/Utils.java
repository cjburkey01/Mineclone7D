package com.cjburkey.mineclone7d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Utils {
	
	public static String[] readStream(InputStream stream) throws IOException {
		List<String> out = new ArrayList<>();
		if(stream != null) {
			BufferedReader reader = null;
			try {
				long start = System.nanoTime();
				reader = new BufferedReader(new InputStreamReader(stream));
				String line;
				while((line = reader.readLine()) != null) {
					out.add(line);
				}
				long end = System.nanoTime();
				Logger.info("Read file in " + (end - start) + "ns.");
			} catch(IOException e) {
				throw e;
			} finally {
				if(reader != null) {
					reader.close();
				}
			}
		}
		return out.toArray(new String[out.size()]);
	}
	
	public static String readStreamAsString(InputStream stream) throws IOException {
		String[] lines = readStream(stream);
		StringBuilder out = new StringBuilder();
		for(int i = 0; i < lines.length; i ++) {
			out.append(lines[i]);
			out.append('\n');
		}
		return out.toString();
	}
	
	public static String[] readFile(String path) throws IOException {
		Logger.info("Reading " + path);
		return readStream(Utils.class.getResourceAsStream(path));
	}
	
	public static String readFileAsString(String path) throws IOException {
		Logger.info("Reading " + path);
		return readStreamAsString(Utils.class.getResourceAsStream(path));
	}
	
}