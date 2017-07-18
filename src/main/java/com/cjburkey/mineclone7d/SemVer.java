package com.cjburkey.mineclone7d;

import java.util.regex.Pattern;
import org.joml.Vector3i;

public class SemVer {
	
	private final Vector3i val = new Vector3i();
	
	public SemVer() {
		this(0, 0, 0);
	}
	
	public SemVer(int major, int minor, int patch) {
		val.set(major, minor, patch);
	}
	
	public int getMajor() {
		return val.x;
	}
	
	public int getMinor() {
		return val.y;
	}
	
	public int getPatch() {
		return val.z;
	}
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(getMajor());
		out.append('.');
		out.append(getMinor());
		out.append('.');
		out.append(getPatch());
		return out.toString();
	}
	
	public static SemVer fromString(String version) {
		String[] separate = version.split(Pattern.quote("."));
		if(separate.length == 3) {
			try {
				int major = Integer.parseInt(separate[0]);
				int minor = Integer.parseInt(separate[1]);
				int patch = Integer.parseInt(separate[2]);
				return new SemVer(major, minor, patch);
			} catch(Exception e) {  }
		}
		return new SemVer();
	}
	
}