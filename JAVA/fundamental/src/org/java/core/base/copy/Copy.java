package org.java.core.base.copy;

/**
 * «≥øΩ±¥”Î…ÓøΩ±¥Ω‚Œˆ
 */
class Team implements Cloneable{
	
	public String name;
	
	public void setName(String name) { this.name = name;}
	
	public String getName() { return name; }
	
	/**
	 * «≥øΩ±¥
	 * @throws CloneNotSupportedException 
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}

class Player implements Cloneable{
	private String name;
	
	public void setName(String name) { this.name = name; }
	
	public String getName() { return name; }
	
	/**
	 * …ÓøΩ±¥
	 */
	@Override
	public Object clone() {
		Object obj = null;
		
		return obj;
	}
}

public class Copy {
	
	public static void main(String[] args) {
		Team team = new Team();
		team.setName("lakers");
		
	}
}
