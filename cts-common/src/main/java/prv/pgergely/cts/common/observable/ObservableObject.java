package prv.pgergely.cts.common.observable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import prv.pgergely.cts.common.observable.util.MissingObservedElement;

public class ObservableObject<T> {
	
	private T observedObj;
	private Map<String, Consumer<T>> observers = new LinkedHashMap<>();
	
	public void subscribe(String name, Consumer<T> funct) {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		System.out.println(Arrays.toString(stElements));
		if(!observers.containsKey(name)) {
			observers.put(name, funct);
		}
	}
	
	public void unsubscribe(String name) {
		observers.remove(name);
	}
	
	public void next(T observedObj) {
		this.observedObj = observedObj;
		for(Consumer<T> observer : observers.values()) {
			observer.accept(observedObj);
		}
	}
	
	public void fetch() throws MissingObservedElement {
		if(observedObj == null) {
			throw new MissingObservedElement("No element currently observe");
		}
		for(Consumer<T> observer : observers.values()) {
			observer.accept(observedObj);
		}
	}
}
