package prv.pgergely.cts.common.observable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import prv.pgergely.cts.common.observable.util.MissingObservedElement;

public class ObservableObject<T> {
	
	private T observedObj;
	private Map<String, Consumer<T>> observers = new LinkedHashMap<>();
	
	public void next(T observedObj) {
		this.observedObj = observedObj;
		observers.values().forEach(observerFunct -> observerFunct.accept(observedObj));
	}
	
	public void fetch() throws MissingObservedElement {
		if(observedObj == null) {
			throw new MissingObservedElement("No element currently observed");
		}
		observers.values().forEach(observerFunct -> observerFunct.accept(observedObj));
	}
	
	public void subscribe(Consumer<T> funct) {
		// First stack the threadStack call, second stack is the method itself, third stack is the real caller method which contains the declaring class what we need
		StackTraceElement type = Thread.currentThread().getStackTrace()[2];
		String typeName = type.getClassName();
		if(!observers.containsKey(typeName)) {
			observers.put(typeName, funct);
		}
	}
	
	public void subscribe(String name, Consumer<T> funct) {
		if(!observers.containsKey(name)) {
			observers.put(name, funct);
		}
	}
	
	public void unsubscribe() {
		StackTraceElement type = Thread.currentThread().getStackTrace()[2];
		String typeName = type.getClassName();
		observers.remove(typeName);
	}
	
	public void unsubscribe(String name) {
		observers.remove(name);
	}
	
}
