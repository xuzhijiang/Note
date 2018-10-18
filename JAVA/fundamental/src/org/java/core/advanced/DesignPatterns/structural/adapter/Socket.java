package org.java.core.advanced.DesignPatterns.structural.adapter;

public class Socket {
	
	public Volt getVolt() {
		return new Volt(120);
	}
}
