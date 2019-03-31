package org.java.core.advanced.DesignPatterns.creational.factory.demo02;

public class PC extends Computer{
	
	private String ram;
	private String hdd;
	private String cpu;
	
	public PC(String ram, String hdd, String cpu) {
		this.ram = ram;
		this.hdd = hdd;
		this.cpu = cpu;
	}

	@Override
	public String getRAM() {
		return null;
	}

	@Override
	public String getHDD() {
		return null;
	}

	@Override
	public String getCPU() {
		return null;
	}

}
