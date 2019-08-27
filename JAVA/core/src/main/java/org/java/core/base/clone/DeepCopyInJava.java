package org.java.core.base.clone;

public class DeepCopyInJava {

	public static void main(String[] args) {
		Player p1 = new Player(001, "james", new Roles("Point Guard", "Center", "Small forward"));
		Player p2 = null;
		try {
			// Creating a clone of p1 and assigning it to p2
			p2 = (Player) p1.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		// Printing the role1 of p1 and p2
		System.out.println("p1 role:" + p1.role.role1);
		System.out.println("p2 role:" + p2.role.role1);

		// Changing the role1 of p2
		p2.role.role1 = "Power forward";

		// This changeStr will not be reflected in original Player p1
		System.out.println("p1 role:" + p1.role.role1);
		System.out.println("p2 role:" + p2.role.role1);

		p2.id = 200;
		System.out.println("p1:" + p1.id);
		System.out.println("p2:" + p2.id);

	}

	private static class Player implements Cloneable {
		int id;
		String name;
		Roles role;

		public Player(int id, String name, Roles role) {
			this.id = id;
			this.name = name;
			this.role = role;
		}

		/**
		 * Overriding clone() method to create a deep copy of an object.
		 * 这就是我们想要的，克隆应该返回一个完全脱离原始对象的新对象。
		 * 原始类型/String类型的变量会自动拷贝,但是Object类型的,不会自动拷贝,需要我们修改.
		 */
		protected Object clone() throws CloneNotSupportedException {
			Player player = (Player) super.clone();
			player.role = (Roles) role.clone();
			return player;
		}
	}

	private static class Roles implements Cloneable {
		String role1;
		String role2;
		String role3;

		public Roles(String r1, String r2, String r3) {
			this.role1 = r1;
			this.role2 = r2;
			this.role3 = r3;
		}

		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

	}

}
