/**
 * Class to create Indirect-references, used to reassign from an outside class.
 *
 */
public class Container<T>{
	public T val;
	
	/**
	 * Constructor, takes an object to encapsulate
	 *
	 * @param t The object to encapsulate
	 */
	public Container(T t){
		val = t;
	}
}
