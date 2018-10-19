package org.java.core.advanced.DesignPatterns.structural.adapter;

/**
 *  Ç½±ÚÉÏµÄ²å×ù
 */
public class Socket {
	
	public Volt getVolt() {
		return new Volt(120);
	}
	
}
