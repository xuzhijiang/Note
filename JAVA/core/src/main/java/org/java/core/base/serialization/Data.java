package org.java.core.base.serialization;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Data和DataProxy都应该实现序列化接口
 * DataProxy应该可以维护Data object的状态
 *
 * DataProxy是内部私有静态类，因此其他类不可以访问它
 * DataProxy应该有一个单独构造器，接受Data作为参数
 *
 * Data类应该提供返回DataProxy实例的writeReplace（）方法。 因此，当序列化Data对象时，
 * 返回的流是DataProxy类。 但是DataProxy类在外部不可见，因此无法直接使用
 *
 * DataProxy类应该实现返回Data对象的readResolve（）方法。 因此，当反序列化Data类时，
 * 内部DataProxy被反序列化，当调用readResolve（）方法时，我们得到Data对象。
 *
 * 最后在Data类中实现readObject（）方法并抛出InvalidObjectException
 * 以避免黑客攻击试图构造Data对象流并解析它。
 *
 * it’s always better not to rely on default implementation
 * 序列化最好不要依赖默认的实现
 */
class Data implements Serializable{

	private static final long serialVersionUID = -3853778866644741701L;

	private String data;
	
	public Data(String data){
		this.data=data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString(){
		return "Data{data=" + data + "}";
	}
	
	// 用DataProxy对象代替Data来进行序列化.
	private Object writeReplace(){
		System.out.println("call writeReplace");
		return new DataProxy(this);
	}

	private void readObject(ObjectInputStream ois) throws InvalidObjectException{
		throw new InvalidObjectException("Proxy is not used, something fishy");
	}

	// 序列化代理类
	private static class DataProxy implements Serializable{

		private static final long serialVersionUID = 8333905273185436744L;

		private String dataProxy;
		private static final String PREFIX = "ABC";
		private static final String SUFFIX = "DEFG";

		public DataProxy(Data d){
			//obscuring data for security
			this.dataProxy = PREFIX + d.data + SUFFIX;
		}

		private Object readResolve() throws InvalidObjectException {
			System.out.println("call readResolve");
			if(dataProxy.startsWith(PREFIX) && dataProxy.endsWith(SUFFIX))
				return new Data(dataProxy.substring(3, dataProxy.length() -4));
			else
				throw new InvalidObjectException("data corrupted");
		}
	}
}
