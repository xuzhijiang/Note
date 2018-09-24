package org.java.core.base.serialization.ProxyPattern;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Both Data and DataProxy class should implement Serializable interface.
 * Data和DataProxy都应该实现序列化接口<p><br>
 * DataProxy should be able to maintain the state of Data object.
 * DataProxy应该可以维护Data object的状态<br><p>
 * DataProxy is inner private static class, so that other classes can’t access it.
 * DataProxy是内部私有静态类，因此其他类不可以访问它<br><p>
 * DataProxy should have a single constructor that takes Data as argument.
 * DataProxy应该有一个单独构造器，接受Data作为参数<p><br>
 * Data class should provide writeReplace() method returning DataProxy instance. 
 * So when Data object is serialized, the returned stream is of DataProxy class. 
 * However DataProxy class is not visible outside, so it can’t be used directly.
 * Data类应该提供返回DataProxy实例的writeReplace（）方法。 因此，当序列化Data对象时，
 * 返回的流是DataProxy类。 但是DataProxy类在外部不可见，因此无法直接使用。<p><br>
 * DataProxy class should implement readResolve() method returning 
 * Data object. So when Data class is deserialized, internally DataProxy 
 * is deserialized and when it’s readResolve() method is called, we get Data object.
 * DataProxy类应该实现返回Data对象的readResolve（）方法。 因此，当反序列化Data类时，
 * 内部DataProxy被反序列化，当调用readResolve（）方法时，我们得到Data对象。
 * <p><br>
 * Finally implement readObject() method in Data class and 
 * throw InvalidObjectException to avoid hackers attack trying to 
 * fabricate Data object stream and parse it.
 * 最后在Data类中实现readObject（）方法并抛出InvalidObjectException
 * 以避免黑客攻击试图构造Data对象流并解析它。
 */
public class Data implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3853778866644741701L;

	private String data;
	
	public Data(String d){
		this.data=d;
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
	
	//serialization proxy class
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
			if(dataProxy.startsWith(PREFIX) && dataProxy.endsWith(SUFFIX))
				return new Data(dataProxy.substring(3, dataProxy.length() -4));
			else 
				throw new InvalidObjectException("data corrupted");
		}
	}
	
	//replacing serialized object to DataProxy object
	private Object writeReplace(){
		return new DataProxy(this);
	}
	
	private void readObject(ObjectInputStream ois) throws InvalidObjectException{
		throw new InvalidObjectException("Proxy is not used, something fishy");
	}
}
