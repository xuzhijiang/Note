package org.java.core.base.Enum;

import java.io.Closeable;
import java.io.IOException;

public enum ThreadStatesEnum implements Closeable {

	START(1) {
		@Override
		public String toString() {
			return "START implementation. Priority=" + getPriority();
		}

		// getDetail是抽象方法，所以必须要实现
		@Override
		public String getDetail() {
			return "START";
		}
	},RUNNING(2) {
		@Override
		public String getDetail() {
			return "RUNNING";
		}
	},WAITING(3) {
		@Override
		public String getDetail() {
			return "WAITING";
		}
	},DEAD(4) {
		@Override
		public String getDetail() {
			return "DEAD";
		}
	};

	private int priority;

	public abstract String getDetail();

	// Enum的构造器总是private的，所以不用显示的添加private
	ThreadStatesEnum(int i) {
		priority = i;
	}

	// Enum可以有方法
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int p) {
		this.priority = p;
	}

	// Enum可以覆写函数
	@Override
	public String toString() {
		return "Default ThreadStatesConstructors implementation. Priority=" + getPriority();
	}

	@Override
	public void close() throws IOException {
		System.out.println("Close of Enum");
	}
}
